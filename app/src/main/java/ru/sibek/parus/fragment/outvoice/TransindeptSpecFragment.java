package ru.sibek.parus.fragment.outvoice;

import android.accounts.Account;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.squareup.otto.parus.BusProvider;
import com.squareup.otto.parus.TransindeptDeletedEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import retrofit.RetrofitError;
import ru.sibek.parus.ParusApplication;
import ru.sibek.parus.R;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.fragment.SwipeToRefreshList;
import ru.sibek.parus.mappers.Status;
import ru.sibek.parus.mappers.outvoices.Nquant;
import ru.sibek.parus.rest.ParusService;
import ru.sibek.parus.sqlite.outinvoices.TransindeptSpecProvider;
import ru.sibek.parus.sync.SyncAdapter;
import ru.sibek.parus.widget.CursorBinderAdapter;

/**
 * Created by Developer on 13.10.2014.
 */
public class TransindeptSpecFragment extends SwipeToRefreshList implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_TRANSINDEPT_ID = "ru.sibek.parus.sync.KEY_TRANSINDEPT_ID";

    private long mInvoiceId;
    private ImageView iconState;
    // Fragment specFragment=null;
    Map<Long, Fragment> specsDetails = new HashMap<Long, Fragment>();

    public Fragment getSpecDetailByID(Long id) {
        return specsDetails.get(id);
    }

    private CursorAdapter mListAdapter;

    public static TransindeptSpecFragment newInstance(long invoiceId) {
        final TransindeptSpecFragment fragment = new TransindeptSpecFragment();
        final Bundle args = new Bundle();
        args.putLong(KEY_TRANSINDEPT_ID, invoiceId);
        fragment.setArguments(args);
        return fragment;
    }

    public void setId(long id) {
        this.getArguments().remove(KEY_TRANSINDEPT_ID);
        this.getArguments().putLong(KEY_TRANSINDEPT_ID, id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInvoiceId = getArguments().getLong(KEY_TRANSINDEPT_ID, -1);
        mListAdapter = new CursorBinderAdapter(getActivity(), R.layout.li_transindept_spec);
        setListAdapter(mListAdapter);
        getLoaderManager().initLoader(R.id.transindept_spec_loader, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == R.id.transindept_spec_loader) {
            return new CursorLoader(
                    getActivity().getApplicationContext(),
                    TransindeptSpecProvider.URI, null,
                    TransindeptSpecProvider.Columns.TRANSINDEPT_ID + "=?",
                    new String[]{String.valueOf(mInvoiceId)},
                    TransindeptSpecProvider.Columns.SNOMEN + " DESC"
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == R.id.transindept_spec_loader) {
            mListAdapter.swapCursor(data);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == R.id.transindept_spec_loader) {
            mListAdapter.swapCursor(null);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Cursor cursor = mListAdapter.getCursor();
        final Double nstoreQuant = TransindeptSpecProvider.getNSTOREQUANT(cursor);
        final long selectedNRN = TransindeptSpecProvider.getNRN(cursor);
        if (nstoreQuant.compareTo(new Double(0)) == 0) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "На складе нет остатков", Toast.LENGTH_LONG).show();
            return;
        }
        final View dialogView = inflater.inflate(R.layout.change_transindept_spec_menu_layout, null);
        builder.setView(dialogView);
        builder
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        EditText valueView = (EditText) dialogView.findViewById(R.id.spec_quant_text);
                        final String text = valueView.getText().toString();
                        //text = text.isEmpty() ? "0" : text;
                        if (text.isEmpty()) {
                            return;
                        }
                        Double nquant = Double.valueOf(text);
                        if (nstoreQuant >= nquant) {
                            final Status[] status = new Status[]{null};
                            Thread myThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Nquant n = new Nquant();
                                        n.setNQUANT(text);
                                        status[0] = ParusService.getService().updateTransindeptSpecNQuant(selectedNRN, n);
                                    } catch (RetrofitError e) {
                                        try {
                                            Log.e("ERROR>>", new Scanner(e.getResponse().getBody().in(), "UTF-8").useDelimiter("\\A").next());
                                        } catch (IOException e1) {

                                            Log.e("ERROR>>", "((((");
                                        }

                                    }
                                }
                            });

                            myThread.start();


                            do {
                                try {
                                    myThread.join(250);//Подождать окончания  четверть секунды.
                                } catch (InterruptedException e) {
                                }
                            }
                            while (myThread.isAlive());
                            if (status[0] != null && status[0].getNRN() != -1) {
                                //обновляем спеку
                                onRefresh(ParusApplication.sAccount);

                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Обновлено!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        status[0].getSMSG(), Toast.LENGTH_LONG).show();
                            }
                        } else

                        {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Остатков недостаточно", Toast.LENGTH_LONG).show();
                        }


                            /*final Bundle extras = new Bundle();
                            extras.putLong(SyncAdapter.KEY_DELETE_TRANSINDEPT_SPEC_ID, trId);
                            ContentResolver.requestSync(ParusApplication.sAccount, ParusAccount.AUTHORITY, extras);*/
                    }
                }).show();


    }


    @Subscribe
    public void onInvoiceChanged(TransindeptDeletedEvent event) {
        if (event.getNRN() != -1) {
            Toast.makeText(getActivity(), "Удалено!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), event.getSMSG(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }


    @Override
    protected void onRefresh(Account account) {
        final Bundle extras = new Bundle();
        extras.putLong(SyncAdapter.KEY_TRANSINDEPT_ID, mInvoiceId);
        ContentResolver.requestSync(account, ParusAccount.AUTHORITY, extras);
    }

    public void refreshSpec(Account account) {
        onRefresh(account);
    }

    @Override
    protected void onSyncStatusChanged(Account account, boolean isSyncActive) {
        setRefreshing(isSyncActive);
    }

}
