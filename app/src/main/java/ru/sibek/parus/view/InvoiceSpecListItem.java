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
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.sibek.parus.R;
import ru.sibek.parus.sqlite.InvoiceSpecProvider;
import ru.sibek.parus.widget.CursorBinder;

/**
 * @author Daniel Serdyukov
 */
public class InvoiceSpecListItem extends LinearLayout implements CursorBinder {

    private TextView mTitle;

    private TextView mTitleNum;

    private TextView mPubDate;

    public InvoiceSpecListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    @SuppressLint("StringFormatMatches")
    public void bindCursor(Cursor c) {
        mTitle.setText(InvoiceSpecProvider.getSNOMENNAME(c));
        mTitleNum.setText(InvoiceSpecProvider.getSNOMEN(c));
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
       // mPubDate = (TextView) findViewById(R.id.pub_date);
    }

}
