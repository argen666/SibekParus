package ru.sibek.parus;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.ContentResolver;
import android.os.Bundle;
import android.preference.PreferenceManager;

import ru.sibek.parus.account.ParusAccount;

/**
 * Created by Developer on 24.10.2014.
 */
public class ParusApplication extends Application {

    public static final String CLIENT_ID = "8189d2cc82e6c716ba3e";

    public static final String CLIENT_SECRET = "9158c03e1972864a44f9b88b9e7efbe03b909a7e";

    public static Account sAccount = null;

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.setDefaultValues(this, R.xml.sync_prefs, false);
        final AccountManager am = AccountManager.get(this);
        if (sAccount == null) {
            sAccount = new Account(getString(R.string.app_name), ParusAccount.TYPE);
        }
        if (am.addAccountExplicitly(sAccount, getPackageName(), new Bundle())) {
            ContentResolver.setSyncAutomatically(sAccount, ParusAccount.AUTHORITY, true);
        }
    }
}
