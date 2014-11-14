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

package ru.sibek.parus.sqlite;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;

import ru.sibek.parus.ParusApplication;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.sync.SyncAdapter;


public class StorageProvider extends SQLiteTableProvider {

    public static final String TABLE_NAME = "storages";

    public static final Uri URI = Uri.parse("content://ru.sibek.parus/" + TABLE_NAME);

    public StorageProvider() {
        super(TABLE_NAME);
    }

    public static long getId(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns._ID));
    }

    public static String getSNAME(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SNAME));
    }

    public static String getSNUMB(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SNUMB));
    }

    public static long getNRN(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NRN));
    }

    public static long getNDISTRIBUTION_SIGN(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NDISTRIBUTION_SIGN));
    }


    @Override
    public void onContentChanged(Context context, int operation, Bundle extras) {

        if (operation == INSERT) {
            extras.keySet();
            final Bundle syncExtras = new Bundle();
            syncExtras.putLong(SyncAdapter.KEY_STORAGE_ID, extras.getLong(KEY_LAST_ID, -1));
            ContentResolver.requestSync(ParusApplication.sAccount, ParusAccount.AUTHORITY, syncExtras);
            Log.d("QQcontentChanged", "insert");

        }
    }


    @Override
    public Uri getBaseUri() {
        return URI;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME +
                "(" + Columns._ID + " integer primary key on conflict replace, "
                + Columns.SNAME + " text, "
                + Columns.SNUMB + " text, "
                + Columns.NDISTRIBUTION_SIGN + " integer, "
                + Columns.NRN + " integer);");
    }

    public interface Columns extends BaseColumns {
        String SNAME = "SNAME";
        String SNUMB = "SNUMB";
        String NRN = "NRN";
        String NDISTRIBUTION_SIGN = "NDISTRIBUTION_SIGN";
    }

}
