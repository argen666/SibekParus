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


public class RacksProvider extends SQLiteTableProvider {

    public static final String TABLE_NAME = "racks";

    public static final Uri URI = Uri.parse("content://ru.sibek.parus/" + TABLE_NAME);

    public RacksProvider() {
        super(TABLE_NAME);
    }

    public static long getId(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns._ID));
    }

    public static String getSFULLNAME(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SFULLNAME));
    }

    public static long getNRN(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NRN));
    }

    public static long getSTORAGE_ID(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.STORAGE_ID));
    }

    @Override
    public void onContentChanged(Context context, int operation, Bundle extras) {

        if (operation == INSERT) {
            extras.keySet();
            final Bundle syncExtras = new Bundle();
            syncExtras.putLong(SyncAdapter.KEY_RACK_ID, extras.getLong(KEY_LAST_ID, -1));
            ContentResolver.requestSync(ParusApplication.sAccount, ParusAccount.AUTHORITY, syncExtras);
            Log.d("RACK_ContentChanged", "insert");

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
                + Columns.SFULLNAME + " text, "
                + Columns.NRN + " integer, "

                + Columns.STORAGE_ID + " integer);");

        db.execSQL("create index if not exists " +
                TABLE_NAME + "_" + Columns.STORAGE_ID + "_index" +
                " on " + TABLE_NAME + "(" + Columns.STORAGE_ID + ");");
    }

    public interface Columns extends BaseColumns {
        String SFULLNAME = "SFULLNAME";
        String NRN = "NRN";
        String STORAGE_ID = "STORAGE_ID";
    }

}
