package ru.sibek.parus.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Developer on 24.10.2014.
 */
public class ParusAuthenticatorService extends Service {

    private ParusAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new ParusAuthenticator(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }

}
