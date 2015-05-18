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

package ru.sibek.parus.sqlite.complectations;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import ru.sibek.parus.sqlite.SQLiteTableProvider;


public class ComplectationSpecProvider extends SQLiteTableProvider {

    public static final String TABLE_NAME = "complectationsspec";

    public static final Uri URI = Uri.parse("content://ru.sibek.parus/" + TABLE_NAME);

    public ComplectationSpecProvider() {
        super(TABLE_NAME);
    }

    public static long getId(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns._ID));
    }

    public static String getSMATRES_NAME(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SMATRES_NAME));
    }

  /*  public static String getSSERNUMB(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SSERNUMB));
    }


    public static String getSNOTE(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SNOTE));
    }*/

    public static String getSRACK(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SRACK));
    }

  /*  public static String getSCELL(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SCELL));
    }*/

    public static String getSNOMEN(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SMATRES_NOMEN));
    }

    public static String getSMEAS_MAIN(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SMATRES_UMEAS));
    }

    public static double getPLANQUANT(Cursor c) {
        return c.getDouble(c.getColumnIndex(Columns.NQUANT_PLAN));
    }

    public static double getNSTOREQUANT(Cursor c) {
        return c.getDouble(c.getColumnIndex(Columns.SQUANT));
    }


    public static long getNRN(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NRN));
    }

    public static long getNPRN(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.NPRN));
    }


    public static long getCOMPLECTATION_ID(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.COMPLECTATION_ID));
    }


    public static long getLOCAL_ICON(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.LOCAL_ICON));
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

                + Columns.SMATRES_CODE + " text, "
                + Columns.SMATRES_NAME + " text, "
                + Columns.SMATRES_NOMEN + " text, "
                + Columns.SMATRES_MODIF + " text, "
                + Columns.SPART_OF_CODE + " text, "
                + Columns.SPART_OF_NAME + " text, "
                + Columns.SPART_OF_NOMEN + " text, "
                + Columns.SPART_OF_MODIF + " text, "

                + Columns.DDATE_PLAN + " integer, "
                + Columns.SRACK + " text, "
                + Columns.SMATRES_UMEAS + " text, "
                + Columns.NQUANT_SPEC + " integer, "
                + Columns.NQUANT_PROD + " integer, "
                + Columns.NQUANT_PLAN + " integer, "
                + Columns.NQUANT_CMPL + " integer, "
                + Columns.NQUANT_DLVR + " integer, "
                + Columns.SQUANT + " text, "

                + Columns.NRN + " integer, "
                + Columns.NPRN + " integer, "


                + Columns.LOCAL_NQUANT + " integer, "
                + Columns.LOCAL_ICON + " integer, "


                + Columns.COMPLECTATION_ID + " integer);");
        db.execSQL("create index if not exists " +
                TABLE_NAME + "_" + Columns.COMPLECTATION_ID + "_index" +
                " on " + TABLE_NAME + "(" + Columns.COMPLECTATION_ID + ");");

    }

    public interface Columns extends BaseColumns {
        String SMATRES_CODE = "SMATRES_CODE";
        String SMATRES_NAME = "SMATRES_NAME";
        String SMATRES_NOMEN = "SMATRES_NOMEN";
        String SMATRES_MODIF = "SMATRES_MODIF";
        String SPART_OF_CODE = "SPART_OF_CODE";
        String SPART_OF_NAME = "SPART_OF_NAME";
        String SPART_OF_NOMEN = "SPART_OF_NOMEN";
        String SPART_OF_MODIF = "SPART_OF_MODIF";
        String DDATE_PLAN = "DDATE_PLAN";
        String SMATRES_UMEAS = "SMATRES_UMEAS";
        String NQUANT_PROD = "NQUANT_PROD";
        String NQUANT_PLAN = "NQUANT_PLAN";
        String NQUANT_CMPL = "NQUANT_CMPL";
        String NQUANT_DLVR = "NQUANT_DLVR";
        String SQUANT = "SQUANT";
        String NQUANT_SPEC = "NQUANT_SPEC";

        String COMPLECTATION_ID = "COMPLECTATION_ID";


        String NRN = "NRN";
        String NPRN = "NPRN";

        String SRACK = "SRACK";

        String LOCAL_NQUANT = "LOCAL_NQUANT";
        String LOCAL_ICON = "LOCAL_ICON";
    }

}
