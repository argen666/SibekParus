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
import ru.sibek.parus.sqlite.ininvoices.InvoiceProvider;
import ru.sibek.parus.sqlite.storages.RacksProvider;
import ru.sibek.parus.sqlite.storages.StorageProvider;


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


    private void startSync(final ContentProviderClient provider, final SyncResult syncResult, final String where, final String[] whereArgs, String action) {
        //TODO: Если синхронизация не закончена не давать вызывать еще раз или вообще писать подождите...
        switch (action) {
            case SyncActions.SYNC_INVOICES: {

                Thread myThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        InvoicesSync.syncInvoices(provider, syncResult, where, whereArgs);
                    }
                });

                myThread.start();

                break;
            }

            case SyncActions.SYNC_STORAGES: {

                Thread myThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        StoragesSync.syncStorages(provider, syncResult, where, whereArgs);
                    }
                });

                myThread.start();
                break;
            }

            case SyncActions.SYNC_RACKS: {

                Thread myThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        StoragesSync.syncRacks(provider, syncResult, where, whereArgs);
                    }
                });

                myThread.start();
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
                InvoicesSync.getInvoices(invoiceId, NRN, null, provider, syncResult);
            } else {
                Log.d("OOPS!", "Network is down");
            }
            Log.d("CP_CLICK>>>", r.toString());

        } catch (/*Remote*/Exception e) {
            e.printStackTrace();
        }
    }



}
