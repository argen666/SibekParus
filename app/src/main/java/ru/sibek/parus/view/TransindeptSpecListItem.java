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
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.sibek.parus.R;
import ru.sibek.parus.sqlite.outinvoices.TransindeptProvider;
import ru.sibek.parus.sqlite.outinvoices.TransindeptSpecProvider;
import ru.sibek.parus.widget.CursorBinder;

/**
 * @author Daniel Serdyukov
 */
public class TransindeptSpecListItem extends LinearLayout implements CursorBinder {

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

    public TransindeptSpecListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    @SuppressLint("StringFormatMatches")
    public void bindCursor(Cursor c) {
        specId = TransindeptSpecProvider.getId(c);

        mTitle.setText(TransindeptSpecProvider.getSNOMENNAME(c)/*+">>"+TransindeptSpecProvider.getLOCAL_ICON(c)*//*+">>"+TransindeptSpecProvider.getNRN(c)+"/"+TransindeptSpecProvider.getNPRN(c)*/);
        mTitleNum.setText(TransindeptSpecProvider.getSNOMEN(c) + "    ");

        double quant = TransindeptSpecProvider.getQUANT(c);
        if (quant == (long) quant) {
            mSpecQuant.setText(String.format("%d", (long) quant) + TransindeptSpecProvider.getSMEAS_MAIN(c).toUpperCase());
        } else {
            mSpecQuant.setText(String.format("%s", quant) + TransindeptSpecProvider.getSMEAS_MAIN(c).toUpperCase());
        }
//        final String sstore = TransindeptSpecProvider.getSSTORE(c);
//        if (sstore != null) {
//
        mSpecStoreQuant.setText(" Остаток: " + TransindeptSpecProvider.getNSTOREQUANT(c));
//            mSpecStoreQuant.setTextColor(Color.BLACK);
//        } else {
//            mSpecStoreQuant.setText("Cклад: - ");
//            mSpecStoreQuant.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
//        }
        //final long storageSign = TransindeptSpecProvider.getNDISTRIBUTION_SIGN(c);
        //if (storageSign == 1) {
        final String rack = TransindeptSpecProvider.getSRACK(c);
        final String cell = TransindeptSpecProvider.getSCELL(c);
        if (rack != null) {
            mSpecPlace.setText(" Место: " + rack + "/" + cell);
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
                TransindeptSpecProvider.URI,
                new String[]{TransindeptSpecProvider.Columns.LOCAL_ICON},
                TransindeptSpecProvider.Columns._ID+"=?",
                new String[]{String.valueOf(specId)},
                null
        );*/
        //mDelete.setTag(R.drawable.delete);
        //mDelete.setImageResource(R.drawable.delete);
        mDelete.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                mDelete.setSelected(arg1.getAction() == MotionEvent.ACTION_DOWN);
                return true;
            }
        });
        final long iconId = TransindeptSpecProvider.getLOCAL_ICON(c);
        if (iconId != 0) {
            mSelect.setTag((int) iconId);
            mSelect.setImageResource((int) iconId);
        } else {
            mSelect.setTag(R.drawable.invoice_spec_non_accepted);
            mSelect.setImageResource(R.drawable.invoice_spec_non_accepted);
        }


        mSelect.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (iconId == R.drawable.invoice_spec_accepted) {
                    // mSelect.setTag(R.drawable.invoice_spec_non_accepted);
                    // mSelect.setImageResource(R.drawable.invoice_spec_non_accepted);

                   /* NetworkTask n = new NetworkTask(provider, null);
                    n.getData(null,null,...);*/
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(TransindeptSpecProvider.Columns.LOCAL_ICON, R.drawable.invoice_spec_non_accepted);

                    long id = getContext().getContentResolver().update(TransindeptSpecProvider.URI, contentValues, TransindeptProvider.Columns._ID + "=?", new String[]{String.valueOf(specId)});
                    Log.d("INSERTED>>>>", id + "");


                } else {
                    //SELECT = true;
                    //  mSelect.setTag(R.drawable.invoice_spec_accepted);
                    //  mSelect.setImageResource(R.drawable.invoice_spec_accepted);

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(TransindeptSpecProvider.Columns.LOCAL_ICON, R.drawable.invoice_spec_accepted);
                    long id = getContext().getContentResolver().update(TransindeptSpecProvider.URI, contentValues, TransindeptProvider.Columns._ID + "=?", new String[]{String.valueOf(specId)});
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



