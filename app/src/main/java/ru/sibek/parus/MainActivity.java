package ru.sibek.parus;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.mappers.Invoices;


public class MainActivity extends FragmentActivity implements InvoicesFragment.OnHeadlineSelectedListener {


    Invoices invoices = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_invoices);

        final AccountManager am = AccountManager.get(this);
        if (am.getAccountsByType(ParusAccount.TYPE).length == 0) {
           // addNewAccount(am);
        }

        InvoicesFragment masterFragment = new InvoicesFragment();
        masterFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, masterFragment).commit();



    }

    private void addNewAccount(AccountManager am) {
        am.addAccount(ParusAccount.TYPE, ParusAccount.TOKEN_FULL_ACCESS, null, null, this,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            future.getResult();
                        } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                            MainActivity.this.finish();
                        }
                    }
                }, null
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar list_item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMasterItemSelected(Invoices.ItemInvoice item) {


    }
}
