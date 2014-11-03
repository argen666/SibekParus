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
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.DateFormat;
import java.util.Date;

import ru.sibek.parus.R;
import ru.sibek.parus.sqlite.InvoiceProvider;
import ru.sibek.parus.widget.CursorBinder;


/**
 * @author Daniel Serdyukov
 */
public class InvoiceListItem extends LinearLayout implements CursorBinder {

   // private FeedIconView mIcon;

    private TextView mTitle;

    private TextView mAgent;

    private TextView mDocDate;

    public InvoiceListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    @SuppressLint("StringFormatMatches")
    public void bindCursor(Cursor c) {
       // mIcon.loadIcon(Provider.getIconUrl(c));
        final String agent = InvoiceProvider.getSagent(c);
        if (!TextUtils.isEmpty(agent)) {
            mAgent.setText("Поставщик: "+agent);
        } else {
            mAgent.setText("Поставщик: "+getResources().getString(R.string.hello_world, InvoiceProvider.getId(c)));
        }
        final String title = InvoiceProvider.getSDoctype(c)+", "+InvoiceProvider.getSpref(c).trim()+"-"+(InvoiceProvider.getSnumb(c));
        if (!TextUtils.isEmpty(title)) {

            mTitle.setText(title);
        } else {
            mTitle.setText(getResources().getString(R.string.hello_world, InvoiceProvider.getId(c)));
        }
        final long docDate = InvoiceProvider.getDdocdate(c);
        if (docDate > 0) {
            mDocDate.setText(DateFormat.getDateTimeInstance().format(new Date(docDate)));
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
       // mIcon = (FeedIconView) findViewById(R.id.feed_icon);
        mTitle = (TextView) findViewById(R.id.title);
        mAgent = (TextView) findViewById(R.id.agent);
        mDocDate = (TextView) findViewById(R.id.doc_date);
    }

}
