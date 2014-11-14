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
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import ru.sibek.parus.ParusApplication;
import ru.sibek.parus.R;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.view.SwipeToDismissCallback;
import ru.sibek.parus.view.SwipeToDismissController;


public class SwipeToRefreshList extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SyncStatusObserver,
        AdapterView.OnItemClickListener, SwipeToDismissCallback {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private int mActivatedPosition = ListView.INVALID_POSITION;

    private SwipeRefreshLayout mRefresher;

    private ListView mListView;

    private Object mSyncMonitor;

    private SwipeToDismissController mSwipeToDismissController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fmt_swipe_to_refresh_list, container, false);
        mListView = (ListView) view.findViewById(android.R.id.list);
        setActivateOnItemClick(true);
        return (mRefresher = (SwipeRefreshLayout) view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRefresher.setColorScheme(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        );
        // mSwipeToDismissController = new SwipeToDismissController(mListView, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRefresher.setOnRefreshListener(this);
        mSyncMonitor = ContentResolver.addStatusChangeListener(
                ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE
                        | ContentResolver.SYNC_OBSERVER_TYPE_PENDING,
                this
        );
        mListView.setOnItemClickListener(this);
        // mListView.setOnTouchListener(mSwipeToDismissController);
        mListView.setOnScrollListener(mSwipeToDismissController);
    }

    @Override
    public void onPause() {
        mRefresher.setOnRefreshListener(null);
        ContentResolver.removeStatusChangeListener(mSyncMonitor);
        mListView.setOnItemClickListener(null);
        mListView.setOnTouchListener(null);
        mListView.setOnScrollListener(null);
        super.onPause();
    }

    @Override
    public final void onRefresh() {
        onRefresh(ParusApplication.sAccount);
    }

    @Override
    public final void onStatusChanged(int which) {
        mRefresher.post(new Runnable() {
            @Override
            public void run() {

                onSyncStatusChanged(ParusApplication.sAccount, ContentResolver
                        .isSyncActive(ParusApplication.sAccount, ParusAccount.AUTHORITY));
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean canDismissView(View view, int position) {
        return false;
    }

    @Override
    public void dismissView(View view, int position) {

    }

    public void setListAdapter(ListAdapter adapter) {
//        final DataSetObserver dataSetObserver = mSwipeToDismissController.getDataSetObserver();
        final ListAdapter oldAdapter = mListView.getAdapter();
        if (oldAdapter != null) {
            //           oldAdapter.unregisterDataSetObserver(dataSetObserver);
        }
        mListView.setAdapter(adapter);
        //      adapter.registerDataSetObserver(dataSetObserver);
    }

    protected void onRefresh(Account account) {

    }

    protected void onSyncStatusChanged(Account account, boolean isSyncActive) {

    }

    protected void setRefreshing(boolean refreshing) {
        mRefresher.setRefreshing(refreshing);
    }

    //----------
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            mListView.setItemChecked(mActivatedPosition, false);
        } else {
            mListView.setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        mListView.setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

}
