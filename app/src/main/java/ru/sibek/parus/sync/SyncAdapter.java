/*
 * Copyright 2012-2014 Daniel Serdyukov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.sibek.parus.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import retrofit.client.Response;
import ru.sibek.parus.rest.NetworkTask;
import ru.sibek.parus.rest.ParusService;
import ru.sibek.parus.sqlite.InvoiceProvider;
import ru.sibek.parus.sqlite.RacksProvider;
import ru.sibek.parus.sqlite.StorageProvider;


public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String KEY_POST_INVOICE_ID = "ru.sibek.parus.sync.KEY_POST_INVOICE_ID";
    public static final String KEY_INVOICE_ID = "ru.sibek.parus.sync.KEY_INVOICE_ID";
    public static final String KEY_STORAGE_ID = "ru.sibek.parus.sync.KEY_STORAGE_ID";
    public static final String KEY_RACK_ID = "ru.sibek.parus.sync.KEY_RACK_ID";

    public SyncAdapter(Context context) {
        super(context, true);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider,
                              SyncResult syncResult) {
        final long postId = extras.getLong(KEY_POST_INVOICE_ID, -1);
        final long feedId = extras.getLong(KEY_INVOICE_ID, -1);
        final long storageId = extras.getLong(KEY_STORAGE_ID, -1);
        final long rackId = extras.getLong(KEY_RACK_ID, -1);
        Log.d(Log.INFO + ">>>>", "feed>>" + feedId + " store>>" + storageId + " rackId>>" + rackId);
        if (feedId == -1 && storageId == -1 && rackId == -1 && postId == -1) {
            startSync(provider, syncResult, null, null, SyncActions.SYNC_INVOICES);
            startSync(provider, syncResult, null, null, SyncActions.SYNC_STORAGES);
            // startSync(provider, syncResult, null,null, SyncActions.SYNC_POST_INVOICES);
        }

        if (postId > 0) {
            startSync(provider, syncResult, InvoiceProvider.Columns._ID + "=?", new String[]{String.valueOf(postId)}, SyncActions.SYNC_POST_INVOICES);
            return;
        }

        if (feedId > 0) {
            startSync(provider, syncResult, InvoiceProvider.Columns._ID + "=?", new String[]{String.valueOf(feedId)}, SyncActions.SYNC_INVOICES);
            return;
        }

        if (storageId > 0) {
            startSync(provider, syncResult, StorageProvider.Columns._ID + "=?", new String[]{String.valueOf(storageId)}, SyncActions.SYNC_STORAGES);
            return;
        }

        if (rackId > 0) {
            startSync(provider, syncResult, RacksProvider.Columns._ID + "=?", new String[]{String.valueOf(rackId)}, SyncActions.SYNC_RACKS);
            return;
        }

        Log.d("PARUS_Sync", "Start" + feedId);
    }


    private void startSync(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs, String action) {
        //TODO: Если синхронизация не закончена не давать вызывать еще раз или вообще писать подождите...
        switch (action) {
            case SyncActions.SYNC_INVOICES: {
                syncInvoices(provider, syncResult, where, whereArgs);
                break;
            }

            case SyncActions.SYNC_STORAGES: {
                syncStorages(provider, syncResult, where, whereArgs);
                break;
            }

            case SyncActions.SYNC_RACKS: {
                syncRacks(provider, syncResult, where, whereArgs);
                break;
            }

            case SyncActions.SYNC_POST_INVOICES: {
                syncPostInvoices(provider, syncResult, where, whereArgs);
                break;
            }

        }


    }

    private void syncPostInvoices(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {
        try {
            final Cursor feeds = provider.query(
                    InvoiceProvider.URI, new String[]{
                            InvoiceProvider.Columns._ID,
                            InvoiceProvider.Columns.NRN
                    }, where, whereArgs, null
            );

            try {
                if (feeds.moveToFirst()) {
                    do {
                        postInvoices(feeds.getString(0), feeds.getString(1), provider, syncResult);
                    } while (feeds.moveToNext());
                } else {
                    // getInvoices(null, null, provider, syncResult);
                }
            } finally {
                feeds.close();
            }
        } catch (RemoteException e) {
            Log.e(SyncAdapter.class.getName(), e.getMessage(), e);
            ++syncResult.stats.numIoExceptions;
        }

    }

    private void postInvoices(String invoiceId, String NRN, ContentProviderClient provider, SyncResult syncResult) {

        NetworkTask n = new NetworkTask(provider, syncResult);
        try {
            //Response r = (Response) n.getData(null, "", "applyInvoiceAsFact", NRN);
            Response r = (Response) ParusService.getService().applyInvoiceAsFact(Long.valueOf(NRN));
            if (r != null) {
                getInvoices(invoiceId, NRN, null, provider, syncResult);
            } else {
                Log.d("OOPS!", "Network is down");
            }
            Log.d("CP_CLICK>>>", r.toString());

        } catch (/*Remote*/Exception e) {
            e.printStackTrace();
        }
    }


    private void syncRacks(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {

        try {
            final Cursor racks = provider.query(
                    RacksProvider.URI, new String[]{
                            RacksProvider.Columns._ID,
                            RacksProvider.Columns.NRN
                    }, where, whereArgs, null
            );

            try {
                if (racks.moveToFirst()) {
                    do {
                        getRacks(racks.getString(0), racks.getString(1), provider, syncResult);
                    } while (racks.moveToNext());
                }
            } finally {
                racks.close();
            }
        } catch (RemoteException e) {
            Log.e(SyncAdapter.class.getName(), e.getMessage(), e);
            ++syncResult.stats.numIoExceptions;
        }

    }


    private void getRacks(String rackID, String NRN, ContentProviderClient provider, SyncResult syncResult) {

        NetworkTask n = new NetworkTask(provider, syncResult);
        try {
                /*n.getData(rackID, "UPDATE_STORAGE", "racksByNRN", NRN);
                Log.d("STORAGE_RACK_UPDATE>>>", rackID + "___" + NRN);*/
            n.getData(rackID, "UPDATE_CELLS", "cellsByNRN", NRN);
            Log.d("STORAGE_UPDATE_CELLS>>>", rackID + "___" + NRN);


        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void syncStorages(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {

        try {
            final Cursor stores = provider.query(
                    StorageProvider.URI, new String[]{
                            StorageProvider.Columns._ID,
                            StorageProvider.Columns.NRN
                    }, where, whereArgs, null
            );

            try {
                if (stores.moveToFirst()) {
                    do {
                        getStorages(stores.getString(0), stores.getString(1), provider, syncResult);
                    } while (stores.moveToNext());
                } else {
                    getStorages(null, null, provider, syncResult);
                }
            } finally {
                stores.close();
            }
        } catch (RemoteException e) {
            Log.e(SyncAdapter.class.getName(), e.getMessage(), e);
            ++syncResult.stats.numIoExceptions;
        }

    }


    private void syncInvoices(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {
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


    private void getStorages(String storageID, String NRN, ContentProviderClient provider, SyncResult syncResult) {

        NetworkTask n = new NetworkTask(provider, syncResult);
        try {
            if (storageID == null) {
                n.getData(null, "FULL_INSERT_STORAGE", "listStorages");
                Log.d("STORAGE_FIRST>>>", "FULL_INSERT_STORAGE");
            } else {

                n.getData(storageID, "UPDATE_STORAGE", "storageByNRN", NRN);
                Log.d("STORAGE_UPDATE>>>", storageID + "___" + NRN);
                n.getData(storageID, "UPDATE_RACKS", "racksByNRN", NRN);
                Log.d("STORAGE_UPDATE_RACK>>>", storageID + "___" + NRN);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void getInvoices(String invoiceID, String NRN, String tms, ContentProviderClient provider, SyncResult syncResult) {

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
