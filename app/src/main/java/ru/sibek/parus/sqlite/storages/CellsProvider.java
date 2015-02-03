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

package ru.sibek.parus.sqlite.storages;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import ru.sibek.parus.sqlite.SQLiteTableProvider;


public class CellsProvider extends SQLiteTableProvider {

    public static final String TABLE_NAME = "cells";

    public static final Uri URI = Uri.parse("content://ru.sibek.parus/" + TABLE_NAME);

    public CellsProvider() {
        super(TABLE_NAME);
    }

    public static long getId(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns._ID));
    }

    public static String getSFULLNAME(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SFULLNAME));
    }

    public static long getRACK_ID(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.RACK_ID));
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

                + Columns.RACK_ID + " integer);");

        db.execSQL("create index if not exists " +
                TABLE_NAME + "_" + Columns.RACK_ID + "_index" +
                " on " + TABLE_NAME + "(" + Columns.RACK_ID + ");");
    }

    public interface Columns extends BaseColumns {
        String SFULLNAME = "SFULLNAME";
        String RACK_ID = "RACK_ID";
    }

}
