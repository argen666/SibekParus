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
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.sibek.parus.R;
import ru.sibek.parus.sqlite.InvoiceSpecProvider;
import ru.sibek.parus.widget.CursorBinder;

/**
 * @author Daniel Serdyukov
 */
public class InvoiceSpecListItem extends LinearLayout implements CursorBinder {

    private boolean SELECT = false;
    private TextView mTitle;

    private TextView mTitleNum;

    private TextView mPubDate;

    private TextView mSpecQuant;
    private TextView mSpecStore;
    private TextView mSpecPlace;

    private ImageView mSelect;

    public InvoiceSpecListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    @SuppressLint("StringFormatMatches")
    public void bindCursor(Cursor c) {
        mTitle.setText(InvoiceSpecProvider.getSNOMENNAME(c)/*+">>"+InvoiceSpecProvider.getNRN(c)+"/"+InvoiceSpecProvider.getNPRN(c)*/);
        mTitleNum.setText(InvoiceSpecProvider.getSNOMEN(c) + "    ");

        double quant = InvoiceSpecProvider.getNQUANT(c);
        if (quant == (long) quant) {
            mSpecQuant.setText(String.format("%d", (long) quant) + InvoiceSpecProvider.getSMEAS_MAIN(c).toUpperCase());
        } else {
            mSpecQuant.setText(String.format("%s", quant) + InvoiceSpecProvider.getSMEAS_MAIN(c).toUpperCase());
        }
        final String sstore = InvoiceSpecProvider.getSSTORE(c);
        if (sstore != null) {
            mSpecStore.setText(" Cклад: " + InvoiceSpecProvider.getSSTORE(c));
            mSpecStore.setTextColor(Color.BLACK);
        } else {
            mSpecStore.setText("Cклад: - ");
            mSpecStore.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
        final long storageSign = InvoiceSpecProvider.getNDISTRIBUTION_SIGN(c);
        if (storageSign == 1) {
            final String rack = InvoiceSpecProvider.getSRACK(c);
            final String cell = InvoiceSpecProvider.getSCELL(c);
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


        mSelect.setImageResource(R.drawable.invoice_spec_non_accepted);
        mSelect.setTag(SELECT);
        mSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((boolean) mSelect.getTag()) {
                    SELECT = false;
                    mSelect.setTag(SELECT);
                    mSelect.setImageResource(R.drawable.invoice_spec_non_accepted);
                } else {
                    SELECT = true;
                    mSelect.setTag(SELECT);
                    mSelect.setImageResource(R.drawable.invoice_spec_accepted);
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
