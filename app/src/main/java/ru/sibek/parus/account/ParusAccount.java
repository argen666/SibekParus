package ru.sibek.parus.account;

import android.accounts.Account;
import android.os.Parcel;

/**
 * Created by Developer on 24.10.2014.
 */
public class ParusAccount extends Account {

    public static final String TYPE = "ru.sibek.parus";

    public static final String TOKEN_FULL_ACCESS = "ru.sibek.parus.TOKEN_FULL_ACCESS";

    public static final String KEY_PASSWORD = "ru.sibek.parus.KEY_PASSWORD";

    public ParusAccount(Parcel in) {
        super(in);
    }

    public ParusAccount(String name) {
        super(name, TYPE);
    }

}
