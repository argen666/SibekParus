package ru.sibek.parus.sync;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

import ru.sibek.parus.mappers.ContentValuesUtils;
import ru.sibek.parus.mappers.complectations.ComplectationSpec;
import ru.sibek.parus.mappers.complectations.Complectations;
import ru.sibek.parus.rest.ParusService;
import ru.sibek.parus.sqlite.complectations.ComplectationProvider;
import ru.sibek.parus.sqlite.complectations.ComplectationSpecProvider;


/**
 * Created by Developer on 23.04.2015.
 */
public class ComplectationSync {
    public static void syncComplectation(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {
        Log.d("Complectations>", "syncComplectations");
        try {
            final Cursor transindepts = provider.query(
                    ComplectationProvider.URI, new String[]{
                            ComplectationProvider.Columns._ID,
                            ComplectationProvider.Columns.NRN
                    }, where, whereArgs, null
            );


            try {
                if (transindepts.moveToFirst()) {

                    if (where != null) {

                        do {
                            getComplectations(transindepts.getString(0), transindepts.getString(1), null, provider, syncResult);
                        } while (transindepts.moveToNext());
                    } else {
                        //дернул шторку

                        final Cursor maxTms = provider.query(
                                ComplectationProvider.URI, new String[]{
                                        "MAX(" + ComplectationProvider.Columns.HASH + ") as" + ComplectationProvider.Columns.HASH,
                                }, null, null, null
                        );
                        maxTms.moveToFirst();
                        String tms = String.valueOf(maxTms.getLong(0));
                        Log.d("MAXTMS>>>", tms);
                        getComplectations(null, null, tms, provider, syncResult);
                    }
                } else {
                    getComplectations(null, null, "0", provider, syncResult);
                }
            } finally {
                transindepts.close();
            }
        } catch (RemoteException e) {
            Log.e(SyncAdapter.class.getName(), e.getMessage(), e);
            ++syncResult.stats.numIoExceptions;
        }

    }

    public static void getComplectations(String transindeptId, String NRN, String tms, ContentProviderClient provider, SyncResult syncResult) {

        // NetworkTask n = new NetworkTask(provider, syncResult);
        try {
            if (transindeptId == null) {
                //todo разбораться с хешем
                if (true) {
                    //if (tms == "0") {

                    Complectations complectations = ParusService.getService().listComplectations("0");

                    syncResult.stats.numDeletes += provider
                            .delete(ComplectationProvider.URI, null, null);

                    ContentValues[] cValues = complectations.toContentValues();
                    List<ContentValues[]> chunkValues = ContentValuesUtils.splitArrayIntoChunk(cValues, 100);
                    for (ContentValues[] chunkValue : chunkValues) {
                        syncResult.stats.numUpdates += provider
                                .bulkInsert(ComplectationProvider.URI, chunkValue);
                        Log.d("ComplectationsChunkValue>", "chunkValue size>>>" + chunkValue.length);
                    }

                    Log.d("Complectations>", "Complectations FIRST INSERT");
                } else {
                    //n.getData(null, "UPDATE_INVOICE_BY_TMS", "listInvoices", tms);
                    Log.d("UPDATE_Complectations_BY_TMS>>>", "UPDATE_Complectations_BY_TMS");
                }
            } else {

                Complectations complectations = ParusService.getService().complectationByNRN(NRN);
                Log.d(NRN, complectations.toString());
                if (complectations.getItems().size() != 0) {
                    syncResult.stats.numUpdates += provider
                            .update(ComplectationProvider.URI, complectations.toContentValues()[0], ComplectationProvider.Columns._ID + "=?", new String[]{transindeptId});
                    Log.d("Complectations_UPDATE>>>", transindeptId + "___" + NRN);

                    ComplectationSpec compsspec = ParusService.getService().complectationSpecByNRN(NRN);
                    //n.getData(transindeptId, "UPDATE_SPEC", "invoiceSpecByNRN", NRN);
                    syncResult.stats.numDeletes += provider
                            .delete(ComplectationSpecProvider.URI, ComplectationSpecProvider.Columns.COMPLECTATION_ID + "=?", new String[]{transindeptId});

                    syncResult.stats.numUpdates += provider
                            .bulkInsert(ComplectationSpecProvider.URI, compsspec.toContentValues(transindeptId));
                    Log.d("Complectations_UPDATE_SPEC>>>", transindeptId + "___" + NRN);
                } else {
                    syncResult.stats.numDeletes += provider
                            .delete(ComplectationProvider.URI, ComplectationProvider.Columns._ID + "=?", new String[]{transindeptId});

                    /*syncResult.stats.numDeletes += provider
                            .delete(TransindeptSpecProvider.URI, TransindeptSpecProvider.Columns.TRANSINDEPT_ID + "=?", new String[]{transindeptId});*/
                }
            }

        } catch (/*Remote*/Exception e) {
            e.printStackTrace();
        }
    }
}
