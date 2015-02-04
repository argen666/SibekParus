package ru.sibek.parus.sync;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

import ru.sibek.parus.rest.NetworkTask;
import ru.sibek.parus.sqlite.ininvoices.InvoiceProvider;

/**
 * Created by Developer on 04.02.2015.
 */
public class InvoicesSync {

    public static void syncInvoices(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {
        //Альтернативный Алгоритм: Качать все накладные, после чего!  все спецификации. В спецификации ид накладной=нулл. При вставке в таблицу
        // спепификаций срабатывает триггер который делает апдейт своего поля ИД_НАКЛАДНОЙ полученного where inv.NRN=spec.NRPN
        try {
            final Cursor feeds = provider.query(
                    InvoiceProvider.URI, new String[]{
                            InvoiceProvider.Columns._ID,
                            InvoiceProvider.Columns.NRN
                    }, where, whereArgs, null
            );


            try {
                if (feeds.moveToFirst()) {

                    if (where != null) {

                        do {
                            getInvoices(feeds.getString(0), feeds.getString(1), null, provider, syncResult);
                        } while (feeds.moveToNext());
                    } else {
                        //дернул шторку

                        final Cursor maxTms = provider.query(
                                InvoiceProvider.URI, new String[]{
                                        "MAX(" + InvoiceProvider.Columns.HASH + ") as" + InvoiceProvider.Columns.HASH,
                                }, null, null, null
                        );
                        maxTms.moveToFirst();
                        String tms = String.valueOf(maxTms.getLong(0));
                        Log.d("MAXTMS>>>", tms);
                        getInvoices(null, null, tms, provider, syncResult);
                    }
                } else {
                    getInvoices(null, null, "0", provider, syncResult);
                }
            } finally {
                feeds.close();
            }
        } catch (RemoteException e) {
            Log.e(SyncAdapter.class.getName(), e.getMessage(), e);
            ++syncResult.stats.numIoExceptions;
        }

    }

    public static void getInvoices(String invoiceID, String NRN, String tms, ContentProviderClient provider, SyncResult syncResult) {

        NetworkTask n = new NetworkTask(provider, syncResult);
        try {
            if (invoiceID == null) {
                if (tms == "0") {
                    n.getData(null, "FULL_INSERT_INVOICE", "listInvoices", tms);
                    Log.d("INVOICE_FIRST>>>", "FIRST INSERT");
                } else {
                    n.getData(null, "UPDATE_INVOICE_BY_TMS", "listInvoices", tms);
                    Log.d("UPDATE_INVOICE_BY_TMS>>>", "UPDATE_INVOICE_BY_TMS");
                }
            } else {

                n.getData(invoiceID, "UPDATE_INVOICE", "invoiceByNRN", NRN);
                Log.d("INVOICE_UPDATE>>>", invoiceID + "___" + NRN);
                n.getData(invoiceID, "UPDATE_SPEC", "invoiceSpecByNRN", NRN);
                Log.d("INVOICE_UPDATE_SPEC>>>", invoiceID + "___" + NRN);

            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
