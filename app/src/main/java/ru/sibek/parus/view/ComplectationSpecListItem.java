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

package ru.sibek.parus.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.sibek.parus.ParusApplication;
import ru.sibek.parus.R;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.sqlite.complectations.ComplectationSpecProvider;
import ru.sibek.parus.sqlite.outinvoices.TransindeptProvider;
import ru.sibek.parus.sync.SyncAdapter;
import ru.sibek.parus.widget.CursorBinder;

/**
 * @author Daniel Serdyukov
 */
public class ComplectationSpecListItem extends LinearLayout implements CursorBinder {

    private TextView mTitle;

    private TextView mTitleNum;

    private TextView mPubDate;

    private TextView mSpecQuant;
    private TextView mSpecStoreQuant;
    private TextView mSpecPlace;

    private ImageView mSelect;
    private ImageView mDelete;

    private long specId;
    private ContentProviderClient provider = null;

    public ComplectationSpecListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    @SuppressLint("StringFormatMatches")
    public void bindCursor(Cursor c) {
        specId = ComplectationSpecProvider.getId(c);

        mTitle.setText(ComplectationSpecProvider.getSMATRES_NAME(c)/*+">>"+ComplectationSpecProvider.getLOCAL_ICON(c)*//*+">>"+ComplectationSpecProvider.getNRN(c)+"/"+ComplectationSpecProvider.getNPRN(c)*/);
        mTitleNum.setText(ComplectationSpecProvider.getSNOMEN(c) + "    ");

        double quant = ComplectationSpecProvider.getNSTOREQUANT(c);
        if (quant == (long) quant) {
            mSpecQuant.setText(String.format("%d", (long) quant) + ComplectationSpecProvider.getSMEAS_MAIN(c).toUpperCase());
        } else {
            mSpecQuant.setText(String.format("%s", quant) + ComplectationSpecProvider.getSMEAS_MAIN(c).toUpperCase());
        }
//        final String sstore = ComplectationSpecProvider.getSSTORE(c);
//        if (sstore != null) {
//
        mSpecStoreQuant.setText(" Остаток: " + ComplectationSpecProvider.getNSTOREQUANT(c));
//            mSpecStoreQuant.setTextColor(Color.BLACK);
//        } else {
//            mSpecStoreQuant.setText("Cклад: - ");
//            mSpecStoreQuant.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
//        }
        //final long storageSign = ComplectationSpecProvider.getNDISTRIBUTION_SIGN(c);
        //if (storageSign == 1) {
        final String rack = ComplectationSpecProvider.getSRACK(c);
        //final String cell = ComplectationSpecProvider.getSCELL(c);
        if (rack != null) {
            mSpecPlace.setText(" Место: " + rack);
            mSpecPlace.setTextColor(Color.BLACK);
        } else {
            mSpecPlace.setText(" Место: - ");
            mSpecPlace.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
        // } else {
        // mSpecPlace.setText("");
        //mSpecPlace.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        // }


       /* Cursor curIcn = getContext().getContentResolver().query(
                ComplectationSpecProvider.URI,
                new String[]{ComplectationSpecProvider.Columns.LOCAL_ICON},
                ComplectationSpecProvider.Columns._ID+"=?",
                new String[]{String.valueOf(specId)},
                null
        );*/
        //mDelete.setTag(R.drawable.delete);
        //mDelete.setImageResource(R.drawable.delete);
        final long trId = ComplectationSpecProvider.getId(c);
        //final long trId = ComplectationSpecProvider.getTRANSINDEPT_ID(c);
        mDelete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder
                        .setMessage("Удалить запись?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                final Bundle extras = new Bundle();
                                extras.putLong(SyncAdapter.KEY_DELETE_TRANSINDEPT_SPEC_ID, trId);
                                ContentResolver.requestSync(ParusApplication.sAccount, ParusAccount.AUTHORITY, extras);
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        }).show();
            }
        });
      /*  mDelete.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                mDelete.setSelected(arg1.getAction() == MotionEvent.ACTION_DOWN);
                return true;
            }
        });*/
        final long iconId = ComplectationSpecProvider.getLOCAL_ICON(c);
        if (iconId != 0) {
            mSelect.setTag((int) iconId);
            mSelect.setImageResource((int) iconId);
        } else {
            mSelect.setTag(R.drawable.invoice_spec_non_accepted);
            mSelect.setImageResource(R.drawable.invoice_spec_non_accepted);
        }

        //mSelect.setTag(SELECT);

        mSelect.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (iconId == R.drawable.invoice_spec_accepted) {
                    // mSelect.setTag(R.drawable.invoice_spec_non_accepted);
                    // mSelect.setImageResource(R.drawable.invoice_spec_non_accepted);

                   /* NetworkTask n = new NetworkTask(provider, null);
                    n.getData(null,null,...);*/
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ComplectationSpecProvider.Columns.LOCAL_ICON, R.drawable.invoice_spec_non_accepted);

                    long id = getContext().getContentResolver().update(ComplectationSpecProvider.URI, contentValues, TransindeptProvider.Columns._ID + "=?", new String[]{String.valueOf(specId)});
                    Log.d("INSERTED>>>>", id + "");


                } else {
                    //SELECT = true;
                    //  mSelect.setTag(R.drawable.invoice_spec_accepted);
                    //  mSelect.setImageResource(R.drawable.invoice_spec_accepted);

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ComplectationSpecProvider.Columns.LOCAL_ICON, R.drawable.invoice_spec_accepted);
                    long id = getContext().getContentResolver().update(ComplectationSpecProvider.URI, contentValues, TransindeptProvider.Columns._ID + "=?", new String[]{String.valueOf(specId)});
                    Log.d("INSERTED>>>>", id + "");
                }
            }
        });

       /* final long pubDate = InvoiceProvider.getDdocdate(c));
        if (pubDate > 0) {
            mPubDate.setText(DateFormat.getDateTimeInstance().format(new Date(pubDate)));
        }*/
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitle = (TextView) findViewById(R.id.title_spec);
        mTitleNum = (TextView) findViewById(R.id.title_spec_number);

        mSpecQuant = (TextView) findViewById(R.id.spec_quant);
        mSpecStoreQuant = (TextView) findViewById(R.id.spec_store_quant);
        mSpecPlace = (TextView) findViewById(R.id.spec_place);

        mSelect = (ImageView) findViewById(R.id.spec_image);
        mDelete = (ImageView) findViewById(R.id.spec_delete_image);
        // mPubDate = (TextView) findViewById(R.id.pub_date);

    }


}



