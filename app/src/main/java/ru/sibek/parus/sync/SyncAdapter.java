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
import android.os.Bundle;
import android.util.Log;

import ru.sibek.parus.sqlite.complectations.ComplectationProvider;
import ru.sibek.parus.sqlite.ininvoices.InvoiceProvider;
import ru.sibek.parus.sqlite.ininvoices.OrderProvider;
import ru.sibek.parus.sqlite.outinvoices.TransindeptProvider;
import ru.sibek.parus.sqlite.outinvoices.TransindeptSpecProvider;
import ru.sibek.parus.sqlite.storages.RacksProvider;
import ru.sibek.parus.sqlite.storages.StorageProvider;


public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String ALL_INVOICES = "ALL_INVOICES";
    public static final String ALL_ORDERS = "ALL_ORDERS";
    public static final String ALL_TRANSINDEPTS = "ALL_TRANSINDEPTS";
    public static final String ALL_COMPLECTATIONS = "ALL_COMPLECTATIONS";

    public static final String KEY_POST_INVOICE_ID = "ru.sibek.parus.sync.KEY_POST_INVOICE_ID";
    public static final String KEY_INVOICE_ID = "ru.sibek.parus.sync.KEY_INVOICE_ID";
    public static final String KEY_STORAGE_ID = "ru.sibek.parus.sync.KEY_STORAGE_ID";
    public static final String KEY_RACK_ID = "ru.sibek.parus.sync.KEY_RACK_ID";
    public static final String KEY_INORDER_ID = "ru.sibek.parus.sync.KEY_INORDER_ID";
    public static final String KEY_TRANSINDEPT_ID = "ru.sibek.parus.sync.KEY_TRANSINDEPT_ID";
    public static final String KEY_DELETE_TRANSINDEPT_SPEC_ID = "ru.sibek.parus.sync.KEY_DELETE_TRANSINDEPT_SPEC_ID";
    public static final String KEY_COMPLECTATION_ID = "ru.sibek.parus.sync.KEY_COMPLECTATION_ID";
    private Context context;

    public SyncAdapter(Context context) {
        super(context, true);
        this.context = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider,
                              SyncResult syncResult) {
        final boolean allInvoices = extras.getBoolean(ALL_INVOICES, false);
        final boolean allOrders = extras.getBoolean(ALL_ORDERS, false);
        final boolean allTransindepts = extras.getBoolean(ALL_TRANSINDEPTS, false);
        final boolean allComplectations = extras.getBoolean(ALL_COMPLECTATIONS, false);

        final long postId = extras.getLong(KEY_POST_INVOICE_ID, -1);
        final long feedId = extras.getLong(KEY_INVOICE_ID, -1);
        final long storageId = extras.getLong(KEY_STORAGE_ID, -1);
        final long rackId = extras.getLong(KEY_RACK_ID, -1);
        final long orderId = extras.getLong(KEY_INORDER_ID, -1);
        final long transindeptId = extras.getLong(KEY_TRANSINDEPT_ID, -1);
        final long transindeptSpecDeleteId = extras.getLong(KEY_DELETE_TRANSINDEPT_SPEC_ID, -1);
        final long complectationId = extras.getLong(KEY_COMPLECTATION_ID, -1);
        Log.d("ALL_DOCUMS>>", allInvoices + "  " + allOrders + "  " + allTransindepts + "  " + allComplectations);
        if (allInvoices) {
            Log.d("ALL_INVOICES>>", allInvoices + "");
            startSync(provider, syncResult, null, null, SyncActions.SYNC_INVOICES);
            return;
        }

        if (allOrders) {
            Log.d("ALL_ORDERS>>", allOrders + "");
            startSync(provider, syncResult, null, null, SyncActions.SYNC_INORDERS);
            return;
        }

        if (allTransindepts) {
            Log.d("ALL_TRANSINDEPTS>>", allTransindepts + "");
            startSync(provider, syncResult, null, null, SyncActions.SYNC_TRANSINDEPT);
            return;
        }

        if (allComplectations) {
            Log.d("all_Complectations>>", allComplectations + "");
            startSync(provider, syncResult, null, null, SyncActions.SYNC_COMPLECTATIONS);
            return;
        }
        Log.d(Log.INFO + ">>>>", "feed>>" + feedId + " store>>" + storageId + " rackId>>" + rackId + " orderId>>" + orderId + " transindeptId>>" + transindeptId);
        if (feedId == -1 && storageId == -1 && rackId == -1 && postId == -1 && orderId == -1 && transindeptId == -1 && transindeptSpecDeleteId == -1 && complectationId == -1) {
            startSync(provider, syncResult, null, null, SyncActions.SYNC_INVOICES);
            startSync(provider, syncResult, null, null, SyncActions.SYNC_STORAGES);
            //--startSync(provider, syncResult, null,null, SyncActions.SYNC_POST_INVOICES);
            startSync(provider, syncResult, null, null, SyncActions.SYNC_INORDERS);
            startSync(provider, syncResult, null, null, SyncActions.SYNC_TRANSINDEPT);
            startSync(provider, syncResult, null, null, SyncActions.SYNC_COMPLECTATIONS);
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

        if (orderId > 0) {
            startSync(provider, syncResult, OrderProvider.Columns._ID + "=?", new String[]{String.valueOf(orderId)}, SyncActions.SYNC_INORDERS);
            return;
        }

        if (transindeptId > 0) {
            startSync(provider, syncResult, TransindeptProvider.Columns._ID + "=?", new String[]{String.valueOf(transindeptId)}, SyncActions.SYNC_TRANSINDEPT);
            return;
        }
        if (transindeptSpecDeleteId > 0) {
            startSync(provider, syncResult, TransindeptSpecProvider.Columns._ID + "=?", new String[]{String.valueOf(transindeptSpecDeleteId)}, SyncActions.SYNC_TRANSINDEPT_SPEC_DELETE);
            return;
        }
        if (complectationId > 0) {
            startSync(provider, syncResult, ComplectationProvider.Columns._ID + "=?", new String[]{String.valueOf(complectationId)}, SyncActions.SYNC_COMPLECTATIONS);
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
                do {
                    try {
                        myThread.join(250);
                    } catch (InterruptedException e) {
                    }
                } while (myThread.isAlive());
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
                do {
                    try {
                        myThread.join(250);
                    } catch (InterruptedException e) {
                    }
                } while (myThread.isAlive());
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
                do {
                    try {
                        myThread.join(250);
                    } catch (InterruptedException e) {
                    }
                } while (myThread.isAlive());
                break;
            }

            case SyncActions.SYNC_POST_INVOICES: {
                InvoicesSync.syncPostInvoices(provider, syncResult, where, whereArgs);

                break;
            }

            case SyncActions.SYNC_INORDERS: {
                Thread myThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OrdersSync.syncOrders(provider, syncResult, where, whereArgs);
                    }
                });

                myThread.start();
                do {
                    try {
                        myThread.join(250);
                    } catch (InterruptedException e) {
                    }
                } while (myThread.isAlive());
                break;
            }

            case SyncActions.SYNC_TRANSINDEPT: {
                Log.d(">>", "Start SYNC_TRANSINDEPT");
                Thread myThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TransindeptSync.syncTransindept(provider, syncResult, where, whereArgs);
                    }
                });

                myThread.start();
                do {
                    try {
                        myThread.join(250);
                    } catch (InterruptedException e) {
                    }
                } while (myThread.isAlive());
                break;
            }
            case SyncActions.SYNC_TRANSINDEPT_SPEC_DELETE: {
                Log.d(">>", "Start SYNC_TRANSINDEPT_SPEC_DELETE");
                Thread myThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TransindeptSync.syncTransindeptSpecDelete(context, provider, syncResult, where, whereArgs);
                    }
                });

                myThread.start();
                do {
                    try {
                        myThread.join(250);
                    } catch (InterruptedException e) {
                    }
                } while (myThread.isAlive());
                break;
            }
            case SyncActions.SYNC_COMPLECTATIONS: {
                Log.d(">>", "Start SYNC_COMPLECTATIONS");
                Thread myThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ComplectationSync.syncComplectation(provider, syncResult, where, whereArgs);
                    }
                });

                myThread.start();
                do {
                    try {
                        myThread.join(250);
                    } catch (InterruptedException e) {
                    }
                } while (myThread.isAlive());
                break;
            }

        }


    }


    /*private void syncPostInvoices(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {
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

        } catch (*//*Remote*//*Exception e) {
            e.printStackTrace();
        }
    }*/



}
