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

package ru.sibek.parus.fragment;

import android.accounts.Account;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ru.sibek.parus.R;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.sqlite.InvoiceProvider;
import ru.sibek.parus.widget.CursorBinderAdapter;

//import com.elegion.newsfeed.activity.NewsActivity;
//import ru.sibek.parus.activity.SpecActivity;


public class InvoicesListFragment extends SwipeToRefreshList implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter mListAdapter;
    private InvoicesSpecFragment specFragment;

    //TODO: Создавать тут бандл <ид инвойса,Фрагмент спеки> и при нажатии на инвойс проверять есть ли для него спека...также сделать для детальной спеки

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListAdapter = new CursorBinderAdapter(getActivity(), R.layout.li_invoice);
        setListAdapter(mListAdapter);
        getLoaderManager().initLoader(R.id.invoices_loader, null, this);

    }

    private void initControlPanel() {
       /* ControlPanel.cDate=(TextView) getActivity().findViewById(R.id.ininvoice_date);
        ControlPanel.cItemName=(TextView) getActivity().findViewById(R.id.ininvoice_item_name);
        ControlPanel.cButton=(Button) getActivity().findViewById(R.id.ininvoice_button);*/
        ControlPanel.controlFragment= (ControlPanelFragment) getFragmentManager().findFragmentById(R.id.control_panel_frame);

        specFragment = (InvoicesSpecFragment) getFragmentManager().findFragmentById(R.id.detail_frame);
        //Log.d("KKKK",cf.toString());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == R.id.invoices_loader) {
           // String[] projection = {InvoiceProvider.Columns._ID, InvoiceProvider.Columns.SNUMB,InvoiceProvider.Columns.SPREF + " * " + InvoiceProvider.Columns.DDOC_DATE + " as data"};
            return new CursorLoader(
                    getActivity().getApplicationContext(),
                    InvoiceProvider.URI, null, null, null, InvoiceProvider.Columns.NSTATUS+" ASC, "+ InvoiceProvider.Columns.DDOC_DATE+" DESC"


            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        initControlPanel();


        //TextView emptyDetailView = ((TextView) getActivity().findViewById(R.id.detail_empty_textView));
       // emptyDetailView.setVisibility(View.VISIBLE);
        if (loader.getId() == R.id.invoices_loader) {
            mListAdapter.swapCursor(data);
            //emptyDetailView.setText("Выберите накладную");
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == R.id.invoices_loader) {
            mListAdapter.swapCursor(null);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       /* final Intent intent = new Intent(getActivity(), NewsActivity.class);
        intent.putExtra(NewsActivity.EXTRA_FEED_ID, id);
        startActivity(intent);*/
    Log.d("ITEM_CLICK: ","pos: "+position+"; id= "+id);
        Cursor curInv = mListAdapter.getCursor();
       // feed = getActivity().getContentResolver().query(InvoiceProvider.URI, new String[]{InvoiceProvider.Columns.SSTATUS} ,InvoiceProvider.Columns._ID+ "=?",new String[]{String.valueOf(id)},null);
        Log.d("ITEM_CLICK: ",InvoiceProvider.getNStatus(curInv)+"");
        String btnText=null;
        if (InvoiceProvider.getNStatus(curInv)!=0){
            btnText="Отработано";
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
           getFragmentManager().beginTransaction().replace(R.id.detail_frame, InvoicesSpecFragment.newInstance(id)).commit();
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
                    InvoiceProvider.URI, InvoiceProvider.Columns._ID + "=?",
                    new String[]{String.valueOf(InvoiceProvider.getId(feed))}
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
        static class ControlPanel
        {
            static ControlPanelFragment controlFragment;
        /*static TextView cDate;
        static TextView cItemName;
        static Button cButton;*/
        }


}
