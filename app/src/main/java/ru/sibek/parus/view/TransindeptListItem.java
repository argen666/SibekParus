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
import ru.sibek.parus.sqlite.outinvoices.TransindeptProvider;
import ru.sibek.parus.widget.CursorBinder;


/**
 * @author Daniel Serdyukov
 */
public class TransindeptListItem extends LinearLayout implements CursorBinder {

    // private FeedIconView mIcon;
    Cursor mCur;
    private TextView mTitle;
    private TextView mAgent;
    private TextView mDocDate;
    private TextView mStatus;
    private ImageView mInvoiceIcon;

    public TransindeptListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    @SuppressLint("StringFormatMatches")
    public void bindCursor(Cursor c) {
        mCur = c;
        // mIcon.loadIcon(Provider.getIconUrl(c));
        final String store = TransindeptProvider.getSStore(c);
        if (!TextUtils.isEmpty(store)) {
            mAgent.setText("Склад: " + store/*DateFormat.getDateTimeInstance().format(new Date(TransindeptProvider.getHASH(c)))*/);
        } else {
            mAgent.setText("Поставщик: " + getResources().getString(R.string.hello_world, TransindeptProvider.getId(c)));
        }

        final String title = TransindeptProvider.getSDoctype(c) + ", " + TransindeptProvider.getSpref(c).trim() + "-" + (TransindeptProvider.getSnumb(c));
        if (!TextUtils.isEmpty(title)) {

            mTitle.setText(title);
        } else {
            mTitle.setText(getResources().getString(R.string.hello_world, TransindeptProvider.getId(c)));
        }
        final long docDate = TransindeptProvider.getDdocdate(c);
        if (docDate > 0) {
            mDocDate.setText(new SimpleDateFormat("dd MMM yг.").format(new Date(docDate)));
        } else {
            mDocDate.setText("");
        }
        final String status = TransindeptProvider.getStatus(c);
        if (!TextUtils.isEmpty(status)) {
            mStatus.setText(/*"Статус: "+*/status);
        } else {
            mStatus.setText("Статус: " + getResources().getString(R.string.hello_world, TransindeptProvider.getId(c)));
        }

        final int numStatus = TransindeptProvider.getNStatus(c);
        if (numStatus == 0) {
            mInvoiceIcon.setImageResource(R.drawable.invoice_non_accepted);
        } else {
            mInvoiceIcon.setImageResource(R.drawable.invoice_accepted);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // mIcon = (FeedIconView) findViewById(R.id.feed_icon);
        mTitle = (TextView) findViewById(R.id.title);
        mAgent = (TextView) findViewById(R.id.agent);
        mDocDate = (TextView) findViewById(R.id.doc_date);
        mStatus = (TextView) findViewById(R.id.status);
        mInvoiceIcon = (ImageView) findViewById(R.id.status_image);
    }

}
