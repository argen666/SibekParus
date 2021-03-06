package ru.sibek.parus.sync;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

import com.squareup.otto.parus.BusProvider;
import com.squareup.otto.parus.TransindeptDeletedEvent;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import retrofit.RetrofitError;
import retrofit.client.Response;
import ru.sibek.parus.mappers.ContentValuesUtils;
import ru.sibek.parus.mappers.Status;
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
                    if (where == null) {
                        getTransindepts(null, null, "0", provider, syncResult);
                    }
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

                    ContentValues[] cValues = transindepts.toContentValues();
                    List<ContentValues[]> chunkValues = ContentValuesUtils.splitArrayIntoChunk(cValues, 100);
                    for (ContentValues[] chunkValue : chunkValues) {
                        syncResult.stats.numUpdates += provider
                                .bulkInsert(TransindeptProvider.URI, chunkValue);
                        Log.d("TransindeptChunkValue>", "chunkValue size>>>" + chunkValue.length);
                    }

                    /*syncResult.stats.numUpdates += provider
                            .bulkInsert(TransindeptProvider.URI, cValues);*/

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
                if (transindepts.getItems().size() != 0) {
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
                } else {
                    Log.d("Transindepts_DELETE!!!>>>", transindeptId + "___" + NRN);
                    syncResult.stats.numDeletes += provider
                            .delete(TransindeptProvider.URI, TransindeptProvider.Columns._ID + "=?", new String[]{transindeptId});

                    /*syncResult.stats.numDeletes += provider
                            .delete(TransindeptSpecProvider.URI, TransindeptSpecProvider.Columns.TRANSINDEPT_ID + "=?", new String[]{transindeptId});*/
                }
            }

        } catch (/*Remote*/Exception e) {
            e.printStackTrace();
        }
    }

    public static void syncTransindeptSpecDelete(Context context, ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {
        try {
            final Cursor transindepts = provider.query(
                    TransindeptSpecProvider.URI, new String[]{
                            TransindeptSpecProvider.Columns.TRANSINDEPT_ID,
                            TransindeptSpecProvider.Columns.NRN,
                            TransindeptSpecProvider.Columns.NPRN
                    }, where, whereArgs, null
            );


            try {
                if (transindepts.moveToFirst()) {


                    deleteTransindeptSpec(transindepts.getString(0), transindepts.getString(1), transindepts.getString(2), provider, syncResult, context);

                } else {

                }
            } finally {
                transindepts.close();
            }
        } catch (RemoteException e) {
            Log.e(SyncAdapter.class.getName(), e.getMessage(), e);
            ++syncResult.stats.numIoExceptions;
        }
    }


    private static void deleteTransindeptSpec(String id, String nrn, String nprn, ContentProviderClient provider, SyncResult syncResult, Context context) {
        try {
            Status status = ParusService.getService().deleteTransindeptSpecByNRN(nrn);
            //Toast.makeText(context,"Success",Toast.LENGTH_LONG).show();
            if (status!=null) {
                BusProvider.getInstance().post(new TransindeptDeletedEvent(status.getNRN(),status.getSMSG()));
            }
        } catch (RetrofitError e) {
            try {
                Log.e("ERROR>>", new Scanner(e.getResponse().getBody().in(), "UTF-8").useDelimiter("\\A").next());
            } catch (IOException e1) {

                Log.e("ERROR>>", "((((");
            }

        }
        getTransindepts(id, nprn, null, provider, syncResult);
        Log.d("Transindepts_DELETE_SPEC>>> ", nrn);
    }
}
