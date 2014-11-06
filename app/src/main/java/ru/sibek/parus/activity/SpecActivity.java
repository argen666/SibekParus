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

package ru.sibek.parus.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;

import ru.sibek.parus.R;
import ru.sibek.parus.fragment.InvoicesSpecFragment;
import ru.sibek.parus.sqlite.InvoiceProvider;


/**
 * @author Daniel Serdyukov
 */
public class SpecActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String EXTRA_FEED_ID = "ru.sibek.parus.EXTRA_INVOICE_ID";

    private long mFeedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_single_frame);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mFeedId = getIntent().getLongExtra(EXTRA_FEED_ID, -1);
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame1, InvoicesSpecFragment.newInstance(mFeedId))
                    .commit();
        }
        getLoaderManager().initLoader(R.id.invoices_spec_loader, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == R.id.invoices_loader) {
            return new CursorLoader(
                    getApplicationContext(),
                    ContentUris.withAppendedId(InvoiceProvider.URI, mFeedId),
                    null, null, null, null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == R.id.invoices_loader && data.moveToFirst()) {
            getActionBar().setTitle(InvoiceProvider.getSagent(data));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
