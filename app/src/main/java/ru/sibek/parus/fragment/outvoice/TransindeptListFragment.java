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

package ru.sibek.parus.fragment.outvoice;

import android.accounts.Account;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import retrofit.RetrofitError;
import ru.sibek.parus.ParusApplication;
import ru.sibek.parus.R;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.fragment.SwipeToRefreshList;
import ru.sibek.parus.fragment.controlpanel.TransindeptControlPanelFragment;
import ru.sibek.parus.fragment.ininvoice.InvoicesSpecFragment;
import ru.sibek.parus.rest.ParusService;
import ru.sibek.parus.sqlite.outinvoices.TransindeptProvider;
import ru.sibek.parus.widget.CursorBinderAdapter;

//import com.elegion.newsfeed.activity.NewsActivity;
//import ru.sibek.parus.activity.SpecActivity;


public class TransindeptListFragment extends SwipeToRefreshList implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter mListAdapter;
    private InvoicesSpecFragment specFragment;
    Pair<Long, Integer> selectedElement = null;

    //TODO: Создавать тут бандл <ид инвойса,Фрагмент спеки> и при нажатии на инвойс проверять есть ли для него спека...также сделать для детальной спеки
    Map<Long, Fragment> specsInvoices = new HashMap<Long, Fragment>();
    private int selectedNRN;

    public Fragment getSpecInvoiceByID(Long id) {
        return specsInvoices.get(id);
    }

    public void setSpecsInvoices(Map<Long, Fragment> specsInvoices) {
        this.specsInvoices = specsInvoices;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mListAdapter = new CursorBinderAdapter(getActivity(), R.layout.li_transindept);
        setListAdapter(mListAdapter);
        getLoaderManager().initLoader(R.id.transindept_loader, null, this);

    }

    private void initControlPanel() {
       /* ControlPanel.cDate=(TextView) getActivity().findViewById(R.id.ininvoice_date);
        ControlPanel.cItemName=(TextView) getActivity().findViewById(R.id.ininvoice_item_name);
        ControlPanel.cButton=(Button) getActivity().findViewById(R.id.ininvoice_button);*/
        ControlPanel.controlFragment = (TransindeptControlPanelFragment) getFragmentManager().findFragmentById(R.id.control_panel_frame);

        //specFragment = (InvoicesSpecFragment) getFragmentManager().findFragmentById(R.id.detail_frame);
        //Log.d("KKKK",cf.toString());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == R.id.transindept_loader) {
            // String[] projection = {InvoiceProvider.Columns._ID, InvoiceProvider.Columns.SNUMB,InvoiceProvider.Columns.SPREF + " * " + InvoiceProvider.Columns.DDOC_DATE + " as data"};
            Log.d(">>>", "RESETLOADER!!!");
            String selection = null;
            String[] selectionArgs = null;
            if (args != null) {
                selection = args.getString("selection");
                selectionArgs = args.getStringArray("selectionArgs");
            }
            return new CursorLoader(
                    getActivity().getApplicationContext(),
                    TransindeptProvider.URI, null, selection, selectionArgs, TransindeptProvider.Columns.NSTATUS + " ASC, " + TransindeptProvider.Columns.DDOC_DATE + " DESC"


            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        initControlPanel();


        //TextView emptyDetailView = ((TextView) getActivity().findViewById(R.id.detail_empty_textView));
        // emptyDetailView.setVisibility(View.VISIBLE);
        if (loader.getId() == R.id.transindept_loader) {
            mListAdapter.swapCursor(data);
            //emptyDetailView.setText("Выберите накладную");
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == R.id.transindept_loader) {
            mListAdapter.swapCursor(null);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       /* final Intent intent = new Intent(getActivity(), NewsActivity.class);
        intent.putExtra(NewsActivity.EXTRA_FEED_ID, id);
        startActivity(intent);*/
        Log.d("ITEM_CLICK: ", "pos: " + position + "; id= " + id);
        Cursor curTrans = mListAdapter.getCursor();
        // feed = getActivity().getContentResolver().query(InvoiceProvider.URI, new String[]{InvoiceProvider.Columns.SSTATUS} ,InvoiceProvider.Columns._ID+ "=?",new String[]{String.valueOf(id)},null);
        Log.d("ITEM_CLICK: ", TransindeptProvider.getNStatus(curTrans) + "");
        selectedElement = new Pair<>(id, TransindeptProvider.getNStatus(curTrans));
        selectedNRN = TransindeptProvider.getNRN(curTrans);
        String btnText = null;
        if (TransindeptProvider.getNStatus(curTrans) != 0) {
            btnText = "Отработано";
        }
        ControlPanel.controlFragment.addInfoToPanel(
                ((TextView) view.findViewById(R.id.title)).getText().toString(),
                ((TextView) view.findViewById(R.id.doc_date)).getText().toString(),
                View.VISIBLE, btnText, id
        );

      /*  if (specFragment==null)
        {
            Log.d("REPLACE!!!","!!!!!");*/
           /* final Intent intent = new Intent(getActivity(), SpecActivity.class);
            intent.putExtra(SpecActivity.EXTRA_FEED_ID, id);
            startActivity(intent);*/

        //TODO: check this

        ((TextView) getActivity().findViewById(R.id.detail_empty_textView)).setVisibility(View.GONE);

        if (getSpecInvoiceByID(id) == null) {
            TransindeptSpecFragment invSpec = TransindeptSpecFragment.newInstance(id);
            specsInvoices.put(id, invSpec);

            getFragmentManager().beginTransaction().replace(R.id.detail_frame, invSpec).commit();
            Log.d("CREATE SPEC>>>>", invSpec.getId() + "");
        } else {
            getFragmentManager().beginTransaction().replace(R.id.detail_frame, (TransindeptSpecFragment) getSpecInvoiceByID(id)).commit();
            Log.d("RESTORE SPEC>>>>", ((TransindeptSpecFragment) getSpecInvoiceByID(id)).getId() + "");
        }
       /* } else {
           // getFragmentManager().beginTransaction()
            specFragment.setId(id);
            getFragmentManager().beginTransaction().replace(R.id.detail_frame, specFragment).commit();
        }*/

        /*ControlPanel.cItemName.setText(((TextView)view.findViewById(R.id.title)).getText());
        ControlPanel.cDate.setText(((TextView)view.findViewById(R.id.doc_date)).getText());
        ControlPanel.cButton.setVisibility(View.VISIBLE);*/
    }

    @Override
    public boolean canDismissView(View view, int position) {
        return true;
    }

    @Override
    public void dismissView(View view, int position) {
        final Cursor feed = mListAdapter.getCursor();
        if (feed.moveToPosition(position)) {
            getActivity().getContentResolver().delete(
                    TransindeptProvider.URI, TransindeptProvider.Columns._ID + "=?",
                    new String[]{String.valueOf(TransindeptProvider.getId(feed))}
            );
        }
    }

    @Override
    protected void onRefresh(Account account) {
        ContentResolver.requestSync(account, ParusAccount.AUTHORITY, new Bundle());
    }

    @Override
    protected void onSyncStatusChanged(Account account, boolean isSyncActive) {
        setRefreshing(isSyncActive);
    }

    static class ControlPanel {
        static TransindeptControlPanelFragment controlFragment;
        /*static TextView cDate;
        static TextView cItemName;
        static Button cButton;*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.scanner_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_scanner) {
            Log.d("SCANNER>>>>", "RUN");
            if (selectedElement != null && selectedElement.second == 0) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
        } else {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "Выберите не отработанный документ!", Toast.LENGTH_LONG);
                toast.show();
            }
        }

        if (item.getItemId() == R.id.action_add) {
            //todo
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "Выбfghfgjhfgh!", Toast.LENGTH_LONG);
            toast.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult.getContents() != null) {
            Log.d("SCANNER>>>>", scanningResult.getContents());
            final String nnomen = "010050001401";// nnomen = scanningResult.getContents();

            if (selectedElement != null && selectedElement.first != 0) {
                //todo добавить элемент - run sync
                Log.d(">>SC_NRN", "___" + selectedNRN);
                Thread myThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ParusService.getService().addTransindeptSpecByMasterNRN(selectedNRN, nnomen);
                        } catch (RetrofitError e) {
                            try {
                                Log.e("ERROR>>", new Scanner(e.getResponse().getBody().in(), "UTF-8").useDelimiter("\\A").next());
                            } catch (IOException e1) {

                                Log.e("ERROR>>", "((((");
                            }

                        }
                    }
                });

                myThread.start();


                do {
                    try {
                        myThread.join(250);//Подождать окончания  четверть секунды.
                    } catch (InterruptedException e) {
                    }
                }
                while (myThread.isAlive());
                //обновляем спеку
                ((TransindeptSpecFragment) specsInvoices.get(selectedElement.first)).onRefresh(ParusApplication.sAccount);
            }
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    scanningResult.getContents(), Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }
}
