package ru.sibek.parus.sync;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

import ru.sibek.parus.rest.NetworkTask;
import ru.sibek.parus.sqlite.ininvoices.OrderProvider;
import ru.sibek.parus.sqlite.ininvoices.OrderProvider;

/**
 * Created by Developer on 04.02.2015.
 */
public class OrdersSync {

    public static void syncOrders(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {
        try {
            final Cursor orders = provider.query(
                    OrderProvider.URI, new String[]{
                            OrderProvider.Columns._ID,
                            OrderProvider.Columns.NRN
                    }, where, whereArgs, null
            );


            try {
                if (orders.moveToFirst()) {

                    if (where != null) {

                        do {
                            getOrders(orders.getString(0), orders.getString(1), null, provider, syncResult);
                        } while (orders.moveToNext());
                    } else {
                        //дернул шторку

                        final Cursor maxTms = provider.query(
                                OrderProvider.URI, new String[]{
                                        "MAX(" + OrderProvider.Columns.HASH + ") as" + OrderProvider.Columns.HASH,
                                }, null, null, null
                        );
                        maxTms.moveToFirst();
                        String tms = String.valueOf(maxTms.getLong(0));
                        Log.d("ORDERS_MAXTMS>>>", tms);
                        getOrders(null, null, tms, provider, syncResult);
                    }
                } else {
                    getOrders(null, null, "0", provider, syncResult);
                }
            } finally {
                orders.close();
            }
        } catch (RemoteException e) {
            Log.e(SyncAdapter.class.getName(), e.getMessage(), e);
            ++syncResult.stats.numIoExceptions;
        }

    }

    public static void getOrders(String invoiceID, String NRN, String tms, ContentProviderClient provider, SyncResult syncResult) {

        NetworkTask n = new NetworkTask(provider, syncResult);
        try {
            if (invoiceID == null) {
                if (tms == "0") {
                    n.getData(null, "FULL_INSERT_ORDERS", "listOrders", tms);
                    Log.d("ORDERS_FIRST>>>", "FIRST INSERT");
                } else {
                    n.getData(null, "UPDATE_ORDER_BY_TMS", "listOrders", tms);
                    Log.d("UPDATE_ORDER_BY_TMS>>>", "UPDATE_ORDER_BY_TMS");
                }
            } else {

                /*n.getData(invoiceID, "UPDATE_INVOICE", "invoiceByNRN", NRN);
                Log.d("INVOICE_UPDATE>>>", invoiceID + "___" + NRN);
                n.getData(invoiceID, "UPDATE_SPEC", "invoiceSpecByNRN", NRN);
                Log.d("INVOICE_UPDATE_SPEC>>>", invoiceID + "___" + NRN);*/

            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
