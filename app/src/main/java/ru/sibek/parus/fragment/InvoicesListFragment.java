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
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ru.sibek.parus.R;
//import com.elegion.newsfeed.activity.NewsActivity;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.sqlite.InvoiceProvider;
import ru.sibek.parus.widget.CursorBinderAdapter;

/**
 * @author Daniel Serdyukov
 */
public class InvoicesListFragment extends SwipeToRefreshList implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter mListAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListAdapter = new CursorBinderAdapter(getActivity(), R.layout.li_feed);
        setListAdapter(mListAdapter);
        getLoaderManager().initLoader(R.id.invoices_loader, null, this);

    }

    private void initControlPanel() {
        ControlPanel.cDate=(TextView) getActivity().findViewById(R.id.ininvoice_date);
        ControlPanel.cItemName=(TextView) getActivity().findViewById(R.id.ininvoice_item_name);
        ControlPanel.cButton=(Button) getActivity().findViewById(R.id.ininvoice_button);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == R.id.invoices_loader) {
            return new CursorLoader(
                    getActivity().getApplicationContext(),
                    InvoiceProvider.URI, null, null, null, null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == R.id.invoices_loader) {
            mListAdapter.swapCursor(data);
        }
        initControlPanel();
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

        ControlPanel.cItemName.setText(((TextView)view.findViewById(R.id.link)).getText());
        ControlPanel.cDate.setText(((TextView)view.findViewById(R.id.pub_date)).getText());
        ControlPanel.cButton.setVisibility(View.VISIBLE);
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
        static TextView cDate;
        static TextView cItemName;
        static Button cButton;
        }
}
