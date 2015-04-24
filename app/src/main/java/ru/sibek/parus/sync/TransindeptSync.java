package ru.sibek.parus.sync;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

import ru.sibek.parus.mappers.outvoices.TransindeptSpec;
import ru.sibek.parus.mappers.outvoices.Transindepts;
import ru.sibek.parus.rest.ParusService;
import ru.sibek.parus.sqlite.outinvoices.TransindeptProvider;
import ru.sibek.parus.sqlite.outinvoices.TransindeptSpecProvider;

/**
 * Created by Developer on 23.04.2015.
 */
public class TransindeptSync {
    public static void syncTransindept(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {
        Log.d("Transindepts>", "syncTransindept");
        try {
            final Cursor transindepts = provider.query(
                    TransindeptProvider.URI, new String[]{
                            TransindeptProvider.Columns._ID,
                            TransindeptProvider.Columns.NRN
                    }, where, whereArgs, null
            );


            try {
                if (transindepts.moveToFirst()) {

                    if (where != null) {

                        do {
                            getTransindepts(transindepts.getString(0), transindepts.getString(1), null, provider, syncResult);
                        } while (transindepts.moveToNext());
                    } else {
                        //дернул шторку

                        final Cursor maxTms = provider.query(
                                TransindeptProvider.URI, new String[]{
                                        "MAX(" + TransindeptProvider.Columns.HASH + ") as" + TransindeptProvider.Columns.HASH,
                                }, null, null, null
                        );
                        maxTms.moveToFirst();
                        String tms = String.valueOf(maxTms.getLong(0));
                        Log.d("MAXTMS>>>", tms);
                        getTransindepts(null, null, tms, provider, syncResult);
                    }
                } else {
                    getTransindepts(null, null, "0", provider, syncResult);
                }
            } finally {
                transindepts.close();
            }
        } catch (RemoteException e) {
            Log.e(SyncAdapter.class.getName(), e.getMessage(), e);
            ++syncResult.stats.numIoExceptions;
        }

    }

    public static void getTransindepts(String transindeptId, String NRN, String tms, ContentProviderClient provider, SyncResult syncResult) {

        // NetworkTask n = new NetworkTask(provider, syncResult);
        try {
            if (transindeptId == null) {
                //todo разбораться с хешем
                if (true) {
                    //if (tms == "0") {
                    Transindepts transindepts = ParusService.getService().listTransindepts("0");

                    syncResult.stats.numDeletes += provider
                            .delete(TransindeptProvider.URI, null, null);

                    syncResult.stats.numUpdates += provider
                            .bulkInsert(TransindeptProvider.URI, transindepts.toContentValues());

                    //n.getData(null, "FULL_INSERT_INVOICE", "listInvoices", "0");
                    Log.d("Transindepts>", "Transindepts FIRST INSERT");
                } else {
                    //n.getData(null, "UPDATE_INVOICE_BY_TMS", "listInvoices", tms);
                    Log.d("UPDATE_Transindepts_BY_TMS>>>", "UPDATE_Transindepts_BY_TMS");
                }
            } else {

                //n.getData(transindeptId, "UPDATE_INVOICE", "invoiceByNRN", NRN);

                Transindepts transindepts = ParusService.getService().transindeptByNRN(NRN);
                Log.d(NRN, transindepts.toString());
                syncResult.stats.numUpdates += provider
                        .update(TransindeptProvider.URI, transindepts.toContentValues()[0], TransindeptProvider.Columns._ID + "=?", new String[]{transindeptId});
                Log.d("Transindepts_UPDATE>>>", transindeptId + "___" + NRN);

                TransindeptSpec transspec = ParusService.getService().transindeptSpecByNRN(NRN);
                //n.getData(transindeptId, "UPDATE_SPEC", "invoiceSpecByNRN", NRN);
                syncResult.stats.numDeletes += provider
                        .delete(TransindeptSpecProvider.URI, TransindeptSpecProvider.Columns.TRANSINDEPT_ID + "=?", new String[]{transindeptId});

                syncResult.stats.numUpdates += provider
                        .bulkInsert(TransindeptSpecProvider.URI, transspec.toContentValues(transindeptId));
                Log.d("Transindepts_UPDATE_SPEC>>>", transindeptId + "___" + NRN);

            }

        } catch (/*Remote*/Exception e) {
            e.printStackTrace();
        }
    }
}
