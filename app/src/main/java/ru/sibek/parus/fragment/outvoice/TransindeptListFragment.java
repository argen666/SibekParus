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
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import ru.sibek.parus.R;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.fragment.SwipeToRefreshList;
import ru.sibek.parus.fragment.controlpanel.TransindeptControlPanelFragment;
import ru.sibek.parus.fragment.ininvoice.InvoicesSpecFragment;
import ru.sibek.parus.sqlite.outinvoices.TransindeptProvider;
import ru.sibek.parus.widget.CursorBinderAdapter;

//import com.elegion.newsfeed.activity.NewsActivity;
//import ru.sibek.parus.activity.SpecActivity;


public class TransindeptListFragment extends SwipeToRefreshList implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter mListAdapter;
    private InvoicesSpecFragment specFragment;

    //TODO: Создавать тут бандл <ид инвойса,Фрагмент спеки> и при нажатии на инвойс проверять есть ли для него спека...также сделать для детальной спеки
    Map<Long, Fragment> specsInvoices = new HashMap<Long, Fragment>();

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
            Log.d("SCANNER>>>>", "!!!");
        }
        return super.onOptionsItemSelected(item);
    }
}