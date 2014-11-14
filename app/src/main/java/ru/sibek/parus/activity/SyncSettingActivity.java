package ru.sibek.parus.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import ru.sibek.parus.R;
import ru.sibek.parus.fragment.SyncSettings;

/**
 * Created by Developer on 28.10.2014.
 */
public class SyncSettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_single_frame);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame1, new SyncSettings())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
