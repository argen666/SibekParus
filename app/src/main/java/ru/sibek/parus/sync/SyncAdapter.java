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


import ru.sibek.parus.rest.NetworkTask;
import ru.sibek.parus.sqlite.InvoiceProvider;

/**
 * @author =Troy= <Daniel Serdyukov>
 * @version 1.0
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String KEY_INVOICE_ID = "ru.sibek.parus.sync.KEY_INVOICE_ID";

    public SyncAdapter(Context context) {
        super(context, true);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider,
                              SyncResult syncResult) {
        final long feedId = extras.getLong(KEY_INVOICE_ID, -1);
        if (feedId > 0) {
            syncInvoices(provider, syncResult, InvoiceProvider.Columns._ID + "=?", new String[]{String.valueOf(feedId)});
        } else {
            syncInvoices(provider, syncResult, null, null);
        }
        Log.d("PARUS_Sync","Start"+feedId);
    }

    private void syncInvoices(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {
        //TODO: Алгоритм: Качать все накладные, после чего!  все спецификации. В спецификации ид накладной=нулл. При вставке в таблицу
        //TODO: спепификаций срабатывает триггер который делает апдейт своего поля ИД_НАКЛАДНОЙ полученного where inv.NRN=spec.NRPN
        try {
            final Cursor feeds = provider.query(
                    InvoiceProvider.URI, new String[]{
                            InvoiceProvider.Columns._ID
                    }, where, whereArgs, null
            );

            try {
                if (feeds.moveToFirst()) {
                    do {//

                      //  getInvoices(feeds.getString(0),provider,syncResult);
                    } while (feeds.moveToNext());
                }
                else {
                    getInvoices(null,provider,syncResult);
                }
            } finally {
                feeds.close();
            }
        } catch (RemoteException e) {
            Log.e(SyncAdapter.class.getName(), e.getMessage(), e);
            ++syncResult.stats.numIoExceptions;
        }
    }

    private void syncInvoice(String invoiceID, ContentProviderClient provider, SyncResult syncResult) {
       /* syncResult.stats.numUpdates += provider
                .update(InvoiceProvider.URI, channelValues, FeedProvider.Columns._ID + "=?", new String[]{invoiceID});*/
    }

   /* private void syncFeed(String feedId, String feedUrl, ContentProviderClient provider, SyncResult syncResult) {
        try {
            final HttpURLConnection cn = (HttpURLConnection) new URL(feedUrl).openConnection();
            try {
                final RssFeedParser parser = new RssFeedParser(cn.getInputStream());
                try {
                    parser.parse(feedId, provider, syncResult);
                } finally {
                    parser.close();
                }
            } finally {
                cn.disconnect();
            }
        } catch (IOException e) {
            Log.e(SyncAdapter.class.getName(), e.getMessage(), e);
            ++syncResult.stats.numIoExceptions;
        }
    }*/

    private void getInvoices(String invoiceID, ContentProviderClient provider, SyncResult syncResult){

        NetworkTask n = new NetworkTask(provider,syncResult);
        try {
            n.getData(invoiceID,"listInvoices","59945");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
