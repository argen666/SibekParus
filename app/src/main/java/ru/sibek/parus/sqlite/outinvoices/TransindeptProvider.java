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

package ru.sibek.parus.sqlite.outinvoices;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;

import ru.sibek.parus.sqlite.SQLiteTableProvider;

public class TransindeptProvider extends SQLiteTableProvider {

    public static final String TABLE_NAME = "transindept";

    public static final Uri URI = Uri.parse("content://ru.sibek.parus/" + TABLE_NAME);

    public TransindeptProvider() {
        super(TABLE_NAME);
    }

    public static long getId(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns._ID));
    }

    public static String getSpref(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SPREF));
    }

    public static long getDdocdate(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.DDOC_DATE));
    }

    public static String getNCompany(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.NCOMPANY));
    }

    public static String getSDoctype(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SDOCTYPE));
    }

    public static String getNSUMMWITHNDS(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.NSUMMWITHNDS));
    }

    public static int getNStatus(Cursor c) {
        return c.getInt(c.getColumnIndex(Columns.NSTATUS));
    }

    public static int getLOCAL_NStatus(Cursor c) {
        return c.getInt(c.getColumnIndex(Columns.LOCAL_NSTATUS));
    }

    public static int getNRN(Cursor c) {
        return c.getInt(c.getColumnIndex(Columns.NRN));
    }

    public static long getHASH(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.HASH));
    }

    public static long getSnumb(Cursor c) {
        return c.getLong(c.getColumnIndex(Columns.SNUMB));
    }


    public static String getSagent(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SAGENT));
    }

    @Override
    public Uri getBaseUri() {
        return URI;
    }

    @Override
    public void onContentChanged(Context context, int operation, Bundle extras) {
        if (operation == INSERT) {
           /* extras.keySet();
            final Bundle syncExtras = new Bundle();
            syncExtras.putLong(SyncAdapter.KEY_TRANSINDEPT_ID, extras.getLong(KEY_LAST_ID, -1));
            ContentResolver.requestSync(ParusApplication.sAccount, ParusAccount.AUTHORITY, syncExtras);
            Log.d("QQcontentChanged", "insert");*/

        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table if not exists " + TABLE_NAME +
                "(" + Columns._ID + " integer primary key on conflict replace, "
                + Columns.NCOMPANY + " integer, "
                + Columns.SJUR_PERS + " text, "
                + Columns.NDOCTYPE + " integer, "
                + Columns.SDOCTYPE + " text, "
                + Columns.SPREF + " text, "
                + Columns.SNUMB + " text, "
                + Columns.DDOC_DATE + " integer, "
                + Columns.HASH + " integer, "
                + Columns.NSTATUS + " integer, "
                + Columns.LOCAL_NSTATUS + " integer, "
                + Columns.SSTATUS_IN_STATUS + " text, "
                + Columns.NRN + " integer, "
                + Columns.DWORK_DATE + " integer, "
                + Columns.SAGENT + " text, "
                + Columns.SAGENT_NAME + " text, "
                + Columns.NSTORE + " integer, "
                + Columns.SSTORE + " text, "
                + Columns.SSTOPER + " text, "
                + Columns.SMOL + " text, "
                + Columns.SSHEEPVIEW + " text, "
                + Columns.SIN_STORE + " text, "
                + Columns.SIN_MOL + " text, "
                + Columns.SIN_STOPER + " text, "
                + Columns.SFACEACC + " text, "
                + Columns.NSUMMWITHNDS + " integer)");
        //ахуенный триггер
        /*db.execSQL("create trigger if not exists after delete on " + TABLE_NAME +
                " begin " +
                " delete from " + InvoiceSpecProvider.TABLE_NAME + " where " + InvoiceSpecProvider.Columns.INVOICE_ID + "=old." + Columns._ID + ";" +
                " end;");*/
    }

    public interface Columns extends BaseColumns {
        String NSTORE = "NSTORE";
        String SSTORE = "SSTORE";
        String SSTOPER = "SSTOPER";
        String SMOL = "SMOL";
        String SSHEEPVIEW = "SSHEEPVIEW";
        String SIN_STORE = "SIN_STORE";
        String SIN_MOL = "SIN_MOL";
        String SIN_STOPER = "SIN_STOPER";
        String SFACEACC = "SFACEACC";
        String NSUMMWITHNDS = "NSUMMWITHNDS";

        String NCOMPANY = "NCOMPANY";
        String SJUR_PERS = "SJUR_PERS";
        String NDOCTYPE = "NDOCTYPE";
        String SDOCTYPE = "SDOCTYPE";
        String SPREF = "SPREF";
        String SNUMB = "SNUMB";
        String DDOC_DATE = "DDOC_DATE";
        String NSTATUS = "NSTATUS";
        String SSTATUS_IN_STATUS = "SSTATUS_IN_STATUS";
        String DWORK_DATE = "DWORK_DATE";
        String SAGENT = "SAGENT";
        String SAGENT_NAME = "SAGENT_NAME";
        String NSUMMTAX = "NSUMMTAX";
        String NRN = "NRN";
        String LOCAL_NSTATUS = "LOCAL_NSTATUS";
        String HASH = "HASH";
    }

}
