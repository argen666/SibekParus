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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.sibek.parus.R;
import ru.sibek.parus.sqlite.ininvoices.OrderProvider;
import ru.sibek.parus.sqlite.ininvoices.OrderSpecProvider;
import ru.sibek.parus.widget.CursorBinder;

/**
 * @author Daniel Serdyukov
 */
public class OrderSpecListItem extends LinearLayout implements CursorBinder {

    private TextView mTitle;

    private TextView mTitleNum;

    private TextView mPubDate;

    private TextView mSpecQuant;
    private TextView mSpecStore;
    private TextView mSpecPlace;

    private ImageView mSelect;

    private long specId;
    private ContentProviderClient provider = null;

    public OrderSpecListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    @SuppressLint("StringFormatMatches")
    public void bindCursor(Cursor c) {
        specId = OrderSpecProvider.getId(c);

        mTitle.setText(OrderSpecProvider.getSNOMENNAME(c)/*+">>"+OrderSpecProvider.getLOCAL_ICON(c)*//*+">>"+OrderSpecProvider.getNRN(c)+"/"+OrderSpecProvider.getNPRN(c)*/);
        mTitleNum.setText(OrderSpecProvider.getSNOMEN(c) + "    ");

        double quant = OrderSpecProvider.getNPLANQUANT(c);
        if (quant == (long) quant) {
            mSpecQuant.setText(String.format("%d", (long) quant) + OrderSpecProvider.getSMEAS_MAIN(c).toUpperCase());
        } else {
            mSpecQuant.setText(String.format("%s", quant) + OrderSpecProvider.getSMEAS_MAIN(c).toUpperCase());
        }
        /*final String sstore = OrderSpecProvider.getSSTORE(c);
        if (sstore != null) {
            mSpecStore.setText(" Cклад: " + OrderSpecProvider.getSSTORE(c));
            mSpecStore.setTextColor(Color.BLACK);
        } else {
            mSpecStore.setText("Cклад: - ");
            mSpecStore.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }*/
        final long storageSign = OrderSpecProvider.getNDISTRIBUTION_SIGN(c);
        if (storageSign == 1) {
            final String rack = OrderSpecProvider.getSRACK(c);
            final String cell = OrderSpecProvider.getSCELL(c);
            if (rack != null) {
                mSpecPlace.setText(" Место: " + rack + "/" + cell);
                mSpecPlace.setTextColor(Color.BLACK);
            } else {
                mSpecPlace.setText(" Место: - ");
                mSpecPlace.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        } else {
            mSpecPlace.setText("");
            //mSpecPlace.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }


       /* Cursor curIcn = getContext().getContentResolver().query(
                OrderSpecProvider.URI,
                new String[]{OrderSpecProvider.Columns.LOCAL_ICON},
                OrderSpecProvider.Columns._ID+"=?",
                new String[]{String.valueOf(specId)},
                null
        );*/
        final long iconId = OrderSpecProvider.getLOCAL_ICON(c);
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
                    contentValues.put(OrderSpecProvider.Columns.LOCAL_ICON, R.drawable.invoice_spec_non_accepted);

                    long id = getContext().getContentResolver().update(OrderSpecProvider.URI, contentValues, OrderProvider.Columns._ID + "=?", new String[]{String.valueOf(specId)});
                    Log.d("INSERTED>>>>", id + "");


                } else {
                    //SELECT = true;
                    //  mSelect.setTag(R.drawable.invoice_spec_accepted);
                    //  mSelect.setImageResource(R.drawable.invoice_spec_accepted);

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(OrderSpecProvider.Columns.LOCAL_ICON, R.drawable.invoice_spec_accepted);
                    long id = getContext().getContentResolver().update(OrderSpecProvider.URI, contentValues, OrderProvider.Columns._ID + "=?", new String[]{String.valueOf(specId)});
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
        mSpecStore = (TextView) findViewById(R.id.spec_store);
        mSpecPlace = (TextView) findViewById(R.id.spec_place);

        mSelect = (ImageView) findViewById(R.id.spec_image);
        // mPubDate = (TextView) findViewById(R.id.pub_date);

    }


}



