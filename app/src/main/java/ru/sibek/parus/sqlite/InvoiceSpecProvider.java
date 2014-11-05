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

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author Daniel Serdyukov
 */
public class InvoiceSpecProvider extends SQLiteTableProvider {

    public static final String TABLE_NAME = "invoicesspec";

    public static final Uri URI = Uri.parse("content://ru.sibek.parus/" + TABLE_NAME);

    public InvoiceSpecProvider() {
        super(TABLE_NAME);
    }

    public static long getId(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns._ID));
    }

    public static String getSNOMENNAME(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SNOMENNAME));
    }

    public static String getSSERNUMB(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SSERNUMB));
    }

    public static String getSSTORE(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SSTORE));
    }

    public static String getSNOTE(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SNOTE));
    }

    public static String getSNOMEN(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SNOMEN));
    }

    public static long getNQUANT(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NQUANT));
    }

    public static long getNPRICE(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NPRICE));
    }

    public static long getNSUMMTAX(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NSUMMTAX));
    }

    public static long getINVOICE_ID(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.INVOICE_ID));
    }

    @Override
    public Uri getBaseUri() {
        return URI;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME +
                "(" + Columns._ID + " integer primary key on conflict replace, "
                + Columns.SNOMEN + " text, "
                + Columns.SNOMENNAME + " text, "
                + Columns.SSERNUMB + " text, "
                + Columns.SSTORE + " text, "
                + Columns.SNOTE + " text, "
                + Columns.NQUANT + " integer, "
                + Columns.NPRICE + " integer, "
                + Columns.NSUMMTAX + " integer, "
                + Columns.INVOICE_ID + " integer);");
        db.execSQL("create index if not exists " +
                TABLE_NAME + "_" + Columns.INVOICE_ID + "_index" +
                " on " + TABLE_NAME + "(" + Columns.INVOICE_ID + ");");
    }

    public interface Columns extends BaseColumns {
        String SNOMEN= "SNOMEN";
        String SNOMENNAME= "SNOMENNAME";
        String SSERNUMB= "SSERNUMB";
        String SSTORE= "SSTORE";
        String SNOTE= "SNOTE";
        String NQUANT= "NQUANT";
        String NPRICE= "NPRICE";
        String NSUMMTAX= "NSUMMTAX";
        String INVOICE_ID="INVOICE_ID";

        /*String TITLE = "title";
        String LINK = "link";
        String PUB_DATE = "pubDate";
        String AUTHOR = "author";
        String FEED_ID = "feedId";*/
    }

}
