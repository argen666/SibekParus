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
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ru.sibek.parus.R;
import ru.sibek.parus.fragment.InvoicesListFragment;
import ru.sibek.parus.sqlite.InvoiceProvider;


/**
 * @author Daniel Serdyukov
 */
public class InvoicesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_single_frame);
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame1, new InvoicesListFragment())
                    .commit();
        }

       /* final ContentValues values = new ContentValues();
        values.put(InvoiceProvider.Columns.SAGENT, "OOLOLO");
        getContentResolver().insert(InvoiceProvider.URI, values);*/

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
