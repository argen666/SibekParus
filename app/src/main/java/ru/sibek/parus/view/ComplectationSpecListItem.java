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
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.sibek.parus.R;
import ru.sibek.parus.sqlite.complectations.ComplectationSpecProvider;
import ru.sibek.parus.widget.CursorBinder;

/**
 * @author Daniel Serdyukov
 */
public class ComplectationSpecListItem extends LinearLayout implements CursorBinder {

    private TextView mTitle;

    private TextView mTitleNum;

    private TextView mPubDate;

    private TextView mSpecPlanQuant;
    private TextView mSpecStoreQuant;
    private TextView mSpecPlace;

    //private ImageView mSelect;

    private long specId;
    private ContentProviderClient provider = null;
    private TextView mSpecCmplQuant;
    private TextView mSpecDlvrQuant;

    public ComplectationSpecListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    @SuppressLint("StringFormatMatches")
    public void bindCursor(Cursor c) {
        specId = ComplectationSpecProvider.getId(c);

        mTitle.setText(ComplectationSpecProvider.getSMATRES_NAME(c)/*+">>"+ComplectationSpecProvider.getLOCAL_ICON(c)*//*+">>"+ComplectationSpecProvider.getNRN(c)+"/"+ComplectationSpecProvider.getNPRN(c)*/);
        mTitleNum.setText(ComplectationSpecProvider.getSMATRES_NOMEN(c) + "    ");

        double planquant = ComplectationSpecProvider.getPLANQUANT(c);
        if (planquant == (long) planquant) {
            mSpecPlanQuant.setText("План: " + String.format("%d", (long) planquant) + ComplectationSpecProvider.getSMEAS_MAIN(c).toUpperCase());
        } else {
            mSpecPlanQuant.setText("План: " + String.format("%s", planquant) + ComplectationSpecProvider.getSMEAS_MAIN(c).toUpperCase());
        }
        double cmplquant = ComplectationSpecProvider.getNQUANT_CMPL(c);
        if (cmplquant == (long) cmplquant) {
            mSpecCmplQuant.setText("Скомпл.: " + String.format("%d", (long) cmplquant));
        } else {
            mSpecCmplQuant.setText("Скомпл.: " + String.format("%s", cmplquant) + ComplectationSpecProvider.getSMEAS_MAIN(c).toUpperCase());
        }
        double dlvrquant = ComplectationSpecProvider.getNQUANT_DLVR(c);
        if (dlvrquant == (long) dlvrquant) {
            mSpecDlvrQuant.setText("Передано: " + String.format("%d", (long) dlvrquant));
        } else {
            mSpecDlvrQuant.setText("Передано: " + String.format("%s", dlvrquant));
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
            mSpecPlace.setText(" Место: " + rack.trim());
            //mSpecPlace.setTextColor(Color.BLACK);
        } else {
            mSpecPlace.setText(" Место: - ");
            //mSpecPlace.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

       /* final long iconId = ComplectationSpecProvider.getLOCAL_ICON(c);
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

                   *//* NetworkTask n = new NetworkTask(provider, null);
                    n.getData(null,null,...);*//*
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
        });*/

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

        mSpecPlanQuant = (TextView) findViewById(R.id.spec_plan_quant);
        mSpecCmplQuant = (TextView) findViewById(R.id.spec_cmpl_quant);
        mSpecDlvrQuant = (TextView) findViewById(R.id.spec_dlvr_quant);
        mSpecStoreQuant = (TextView) findViewById(R.id.spec_store_quant);
        mSpecPlace = (TextView) findViewById(R.id.spec_place);

        //mSelect = (ImageView) findViewById(R.id.spec_image);

    }


}



