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

package ru.sibek.parus.sqlite.ininvoices;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import ru.sibek.parus.sqlite.SQLiteTableProvider;


public class OrderSpecProvider extends SQLiteTableProvider {

    public static final String TABLE_NAME = "invoicesspec";

    public static final Uri URI = Uri.parse("content://ru.sibek.parus/" + TABLE_NAME);

    public OrderSpecProvider() {
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

    public static String getSRACK(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SRACK));
    }

    public static String getSCELL(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SCELL));
    }

    public static String getSNOMEN(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SNOMEN));
    }

    public static String getSMEAS_MAIN(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SMEAS_MAIN));
    }

    public static double getNQUANT(Cursor c) {
        return c.getDouble(c.getColumnIndex(Columns.NQUANT));
    }

    public static long getNSTORE(Cursor c) {
        return c.getInt(c.getColumnIndex(Columns.NSTORE));
    }

    public static long getNPRICE(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NPRICE));
    }

    public static long getNRN(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NRN));
    }

    public static long getNPRN(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NPRN));
    }

    public static long getNSUMMTAX(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NSUMMTAX));
    }

    public static long getNDISTRIBUTION_SIGN(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NDISTRIBUTION_SIGN));
    }

    public static long getINVOICE_ID(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.INVOICE_ID));
    }

    public static long getNRACK(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NRACK));
    }

    public static long getLOCAL_ICON(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.LOCAL_ICON));
    }

    public static String getLOCAL_STORE(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.LOCAL_SSTORE));
    }

    public static String getLOCAL_SRACK(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.LOCAL_SRACK));
    }

    public static String getLOCAL_SCELL(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.LOCAL_SCELL));
    }

    public static double getLOCAL_NQUANT(Cursor c) {
        return c.getDouble(c.getColumnIndex(Columns.LOCAL_NQUANT));
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
                + Columns.NSTORE + " integer, "
                + Columns.SSTORE + " text, "
                + Columns.SNOTE + " text, "
                + Columns.SMEAS_MAIN + " text, "
                + Columns.NQUANT + " integer, "
                + Columns.NPRICE + " integer, "
                + Columns.NSUMMTAX + " integer, "
                + Columns.NRACK + " integer, "
                + Columns.SRACK + " text, "
                + Columns.SCELL + " text, "
                + Columns.NDISTRIBUTION_SIGN + " integer, "
                + Columns.NRN + " integer, "
                + Columns.NPRN + " integer, "


                + Columns.LOCAL_NQUANT + " integer, "
                + Columns.LOCAL_SRACK + " text, "
                + Columns.LOCAL_SCELL + " text, "
                + Columns.LOCAL_SSTORE + " text, "
                + Columns.LOCAL_ICON + " integer, "


                + Columns.INVOICE_ID + " integer);");
        db.execSQL("create index if not exists " +
                TABLE_NAME + "_" + Columns.INVOICE_ID + "_index" +
                " on " + TABLE_NAME + "(" + Columns.INVOICE_ID + ");");
    }

    public interface Columns extends BaseColumns {
        String SNOMEN = "SNOMEN";
        String SNOMENNAME = "SNOMENNAME";
        String SSERNUMB = "SSERNUMB";
        String SSTORE = "SSTORE";
        String NSTORE = "NSTORE";
        String SMEAS_MAIN = "SMEAS_MAIN";
        String SNOTE = "SNOTE";
        String NQUANT = "NQUANT";
        String NPRICE = "NPRICE";
        String NSUMMTAX = "NSUMMTAX";
        String INVOICE_ID = "INVOICE_ID";
        String NRN = "NRN";
        String NPRN = "NPRN";

        String NRACK = "NRACK";
        String SRACK = "SRACK";
        String SCELL = "SCELL";
        String NDISTRIBUTION_SIGN = "NDISTRIBUTION_SIGN";


        String LOCAL_NQUANT = "LOCAL_NQUANT";
        String LOCAL_SRACK = "LOCAL_SRACK";
        String LOCAL_SCELL = "LOCAL_SCELL";
        String LOCAL_SSTORE = "LOCAL_SSTORE";
        String LOCAL_ICON = "LOCAL_ICON";

        /*String TITLE = "title";
        String LINK = "link";
        String PUB_DATE = "pubDate";
        String AUTHOR = "author";
        String FEED_ID = "feedId";*/
    }

}
