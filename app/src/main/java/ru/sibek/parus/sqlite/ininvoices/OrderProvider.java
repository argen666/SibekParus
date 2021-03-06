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

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;

import ru.sibek.parus.ParusApplication;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.sqlite.SQLiteTableProvider;
import ru.sibek.parus.sync.SyncAdapter;

public class OrderProvider extends SQLiteTableProvider {

    public static final String TABLE_NAME = "orders";

    public static final Uri URI = Uri.parse("content://ru.sibek.parus/" + TABLE_NAME);

    public OrderProvider() {
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

    public static String getStatus(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SSTATUS));
    }

    public static String getSstore(Cursor c) {
        return c.getString(c.getColumnIndex(Columns.SSTORE));
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
            extras.keySet();
            final Bundle syncExtras = new Bundle();
            syncExtras.putLong(SyncAdapter.KEY_INORDER_ID, extras.getLong(KEY_LAST_ID, -1));
            ContentResolver.requestSync(ParusApplication.sAccount, ParusAccount.AUTHORITY, syncExtras);
            //Log.d("QQcontentChanged", "insert");

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
                + Columns.NSTORE + " integer, "
                + Columns.SSTORE + " text, "
                + Columns.DDOC_DATE + " integer, "
                + Columns.HASH + " integer, "
                + Columns.NSTATUS + " integer, "
                + Columns.LOCAL_NSTATUS + " integer, "
                + Columns.SSTATUS + " text, "
                + Columns.NRN + " integer, "
                + Columns.DWORK_DATE + " integer, "
                + Columns.SAGENT + " text, "
                + Columns.NSUMM + " integer, "
                + Columns.NSUMMTAX + " integer)");
        //ахуенный триггер
        db.execSQL("create trigger if not exists after delete on " + TABLE_NAME +
                " begin " +
                " delete from " + OrderSpecProvider.TABLE_NAME + " where " + OrderSpecProvider.Columns.ORDER_ID + "=old." + Columns._ID + ";" +
                " end;");
    }

    public interface Columns extends BaseColumns {
        String NCOMPANY = "NCOMPANY";
        String SJUR_PERS = "SJUR_PERS";
        String NDOCTYPE = "NDOCTYPE";
        String SDOCTYPE = "SDOCTYPE";
        String SPREF = "SPREF";
        String SNUMB = "SNUMB";
        String SSTORE = "SSTORE";
        String NSTORE = "NSTORE";
        String DDOC_DATE = "DDOC_DATE";
        String NSTATUS = "NSTATUS";
        String SSTATUS = "SSTATUS";
        String DWORK_DATE = "DWORK_DATE";
        String SAGENT = "SAGENT";
        String NSUMM = "NSUMM";
        String NSUMMTAX = "NSUMMTAX";
        String NRN = "NRN";
        String LOCAL_NSTATUS = "LOCAL_NSTATUS";
        String HASH = "HASH";
    }

}
