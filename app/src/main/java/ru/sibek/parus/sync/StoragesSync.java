package ru.sibek.parus.sync;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

import ru.sibek.parus.rest.NetworkTask;
import ru.sibek.parus.sqlite.storages.RacksProvider;
import ru.sibek.parus.sqlite.storages.StorageProvider;

/**
 * Created by Developer on 04.02.2015.
 */
public class StoragesSync {
    public static void syncStorages(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {
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

    private static void getStorages(String storageID, String NRN, ContentProviderClient provider, SyncResult syncResult) {

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

    public static void syncRacks(ContentProviderClient provider, SyncResult syncResult, String where, String[] whereArgs) {
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

    private static void getRacks(String rackID, String NRN, ContentProviderClient provider, SyncResult syncResult) {

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
}
