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

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ru.sibek.parus.R;
import ru.sibek.parus.fragment.InvoicesListFragment;
import ru.sibek.parus.fragment.LogoFragment;
import ru.sibek.parus.fragment.Types;
import ru.sibek.parus.sqlite.InvoiceProvider;
import ru.sibek.parus.view.DummyFragment;
import ru.sibek.parus.view.TabListener;


/**
 * @author Daniel Serdyukov
 */
public class InvoicesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_single_frame);
        showTabs();

        if (savedInstanceState == null) {
           getFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame1, new LogoFragment())
                    .commit();
        }

       /* final ContentValues values = new ContentValues();
        values.put(InvoiceProvider.Columns.SAGENT, "OOLOLO");
        getContentResolver().insert(InvoiceProvider.URI, values);*/

    }

    private void showTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab = actionBar.newTab()
                .setText("Приход")
                .setTabListener(new TabListener<InvoicesListFragment>(
                        this, Types.ININVOICES, InvoicesListFragment.class));
        actionBar.addTab(tab,false);

        tab = actionBar.newTab()
                .setText("Расход")
                .setTabListener(new TabListener<DummyFragment>(
                        this, "outvoice", DummyFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText("Комплект.")
                .setTabListener(new TabListener<DummyFragment>(
                        this, "complectation", DummyFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText("Номенкл.")
                .setTabListener(new TabListener<DummyFragment>(
                        this, "nomenclature", DummyFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText("Склады")
                .setTabListener(new TabListener<DummyFragment>(
                        this, "stores", DummyFragment.class));
        actionBar.addTab(tab,false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.feeds, menu);
        return super.onCreateOptionsMenu(menu);
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            new AddFeedPopup().show(getFragmentManager(), AddFeedPopup.class.getName());
            return true;
        } else if (item.getItemId() == R.id.action_prefs) {
            startActivity(new Intent(this, SyncSettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

}
