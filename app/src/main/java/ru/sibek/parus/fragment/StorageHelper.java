package ru.sibek.parus.fragment;

import android.content.Context;
import android.database.Cursor;

import ru.sibek.parus.sqlite.storages.CellsProvider;
import ru.sibek.parus.sqlite.storages.RacksProvider;
import ru.sibek.parus.sqlite.storages.StorageProvider;

/**
 * Created by Developer on 21.11.2014.
 */
public class StorageHelper {
    public static Cursor getRacksByStorageId(Context ctx, long storageId) {
        Cursor cur = ctx.getContentResolver().query(
                RacksProvider.URI, new String[]{
                        RacksProvider.Columns._ID,
                        RacksProvider.Columns.SFULLNAME
                }, RacksProvider.Columns.STORAGE_ID + "=?", new String[]{String.valueOf(storageId)}, RacksProvider.Columns.SFULLNAME + " ASC"
        );
        return cur;
    }

    public static Cursor getCellsByRackId(Context ctx, long rackId) {
        Cursor cur = ctx.getContentResolver().query(
                CellsProvider.URI, new String[]{
                        CellsProvider.Columns._ID,
                        CellsProvider.Columns.SFULLNAME
                }, CellsProvider.Columns.RACK_ID + "=?", new String[]{String.valueOf(rackId)}, RacksProvider.Columns.SFULLNAME + " ASC"
        );
        return cur;
    }

    public static long getStorageIdByNstore(Context ctx, long nstore) {
        Cursor cur = ctx.getContentResolver().query(
                StorageProvider.URI, new String[]{
                        StorageProvider.Columns._ID,
                }, StorageProvider.Columns.NRN + "=?", new String[]{String.valueOf(nstore)}, null
        );
        cur.moveToNext();
        return StorageProvider.getId(cur);
    }

    public static long getRackIdByNrack(Context ctx, long nrack) {
        Cursor cur = ctx.getContentResolver().query(
                RacksProvider.URI, new String[]{
                        RacksProvider.Columns._ID,
                }, RacksProvider.Columns.NRN + "=?", new String[]{String.valueOf(nrack)}, null
        );
        cur.moveToNext();
        return StorageProvider.getId(cur);
    }
}
