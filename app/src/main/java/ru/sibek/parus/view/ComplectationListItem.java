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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.sibek.parus.R;
import ru.sibek.parus.sqlite.complectations.ComplectationProvider;
import ru.sibek.parus.widget.CursorBinder;


/**
 * @author Daniel Serdyukov
 */
public class ComplectationListItem extends LinearLayout implements CursorBinder {

    private final Context context;
    // private FeedIconView mIcon;
    Cursor mCur;
    private TextView mTitle;
    private TextView mName;
    private TextView mStore;
    private TextView mDocDate;
    private TextView mStatus;
    private ImageView mInvoiceIcon;

    public ComplectationListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    @SuppressLint("StringFormatMatches")
    public void bindCursor(Cursor c) {
        mCur = c;
        // mIcon.loadIcon(Provider.getIconUrl(c));
        final String store = ComplectationProvider.getSStore(c);
        if (!TextUtils.isEmpty(store)) {
            mStore.setText("Склад: " + store/*DateFormat.getDateTimeInstance().format(new Date(ComplectationProvider.getHASH(c)))*/);
        } else {
            mStore.setText("Склад: -");
        }

        final String title = ComplectationProvider.getSDoctype(c) + ", " + ComplectationProvider.getSpref(c).trim() + "-" + (ComplectationProvider.getSnumb(c));
        if (!TextUtils.isEmpty(title)) {

            mTitle.setText(title);
        } else {
            mTitle.setText(getResources().getString(R.string.hello_world, ComplectationProvider.getId(c)));
        }
        final String name = ComplectationProvider.getSMATRES_NAME(c);
        if (!TextUtils.isEmpty(name)) {

            mName.setText(name);
        } else {
            mName.setText(getResources().getString(R.string.hello_world, ComplectationProvider.getId(c)));
        }
        final long docDate = ComplectationProvider.getDdocdate(c);
        if (docDate > 0) {
            mDocDate.setText(new SimpleDateFormat("dd MMM yг.").format(new Date(docDate)));
        } else {
            mDocDate.setText("");
        }
        final String status = ComplectationProvider.getSPROD_ORDER(c);
        if (!TextUtils.isEmpty(status)) {
            mStatus.setText("Заказ: " + status);
        } else {
            mStatus.setText("Заказ: -");
        }

        //TODO перенести в бд,и возвращать поле isAllowed=0/1
        /*boolean isOk = true;
        Cursor cur=null;
        try {
            cur = context.getContentResolver().query(
                    ComplectationSpecProvider.URI, null,
                    ComplectationSpecProvider.Columns.COMPLECTATION_ID + "=?",
                    new String[]{String.valueOf(ComplectationProvider.getId(c))}, null);

            if (cur.moveToFirst()) {
                do {
                    Double nquantStore = ComplectationSpecProvider.getNSTOREQUANT(cur);
                    Double nquantPlan = ComplectationSpecProvider.getPLANQUANT(cur);
                    Double nquantDlvr = ComplectationSpecProvider.getNQUANT_DLVR(cur);
                    if (nquantStore >= (nquantPlan - nquantDlvr)) {
                        isOk = true;
                    } else {
                        isOk = false;

                    }

                } while (cur.moveToNext());

            } else {
                mInvoiceIcon.setImageResource(R.drawable.invoice_non_accepted);
            }
        } finally {
            cur.close();
        }*/
        int salive = ComplectationProvider.getSALIVE(c);
        if (ComplectationProvider.getSALIVE(c) == 0) {
            mInvoiceIcon.setImageResource(R.drawable.invoice_accepted);
        } else {
            mInvoiceIcon.setImageResource(R.drawable.invoice_non_accepted);
        }
        /*final int numStatus = ComplectationProvider.getNStatus(c);
        if (numStatus == 0) {
            mInvoiceIcon.setImageResource(R.drawable.invoice_non_accepted);
        } else {
            mInvoiceIcon.setImageResource(R.drawable.invoice_accepted);
        }*/
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // mIcon = (FeedIconView) findViewById(R.id.feed_icon);
        mTitle = (TextView) findViewById(R.id.title);
        mName = (TextView) findViewById(R.id.name);
        mStore = (TextView) findViewById(R.id.agent);
        mDocDate = (TextView) findViewById(R.id.doc_date);
        mStatus = (TextView) findViewById(R.id.status);
        mInvoiceIcon = (ImageView) findViewById(R.id.status_image);
    }

}
