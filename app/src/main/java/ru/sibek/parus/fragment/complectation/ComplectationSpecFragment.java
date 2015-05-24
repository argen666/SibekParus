package ru.sibek.parus.fragment.complectation;

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

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import ru.sibek.parus.ParusApplication;
import ru.sibek.parus.R;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.fragment.SwipeToRefreshList;
import ru.sibek.parus.mappers.Status;
import ru.sibek.parus.mappers.outvoices.Nquant;
import ru.sibek.parus.rest.ParusService;
import ru.sibek.parus.sqlite.complectations.ComplectationSpecProvider;
import ru.sibek.parus.sync.SyncAdapter;
import ru.sibek.parus.widget.CursorBinderAdapter;

/**
 * Created by Developer on 13.10.2014.
 */
public class ComplectationSpecFragment extends SwipeToRefreshList implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_COMPLECTATION_ID = "ru.sibek.parus.sync.KEY_TRANSINDEPT_ID";

    private long mInvoiceId;
    private ImageView iconState;
    // Fragment specFragment=null;
    Map<Long, Fragment> specsDetails = new HashMap<Long, Fragment>();

    public Fragment getSpecDetailByID(Long id) {
        return specsDetails.get(id);
    }

    private CursorAdapter mListAdapter;

    public static ComplectationSpecFragment newInstance(long invoiceId) {
        final ComplectationSpecFragment fragment = new ComplectationSpecFragment();
        final Bundle args = new Bundle();
        args.putLong(KEY_COMPLECTATION_ID, invoiceId);
        fragment.setArguments(args);
        return fragment;
    }

    public void setId(long id) {
        this.getArguments().remove(KEY_COMPLECTATION_ID);
        this.getArguments().putLong(KEY_COMPLECTATION_ID, id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInvoiceId = getArguments().getLong(KEY_COMPLECTATION_ID, -1);
        mListAdapter = new CursorBinderAdapter(getActivity(), R.layout.li_complectation_spec);
        setListAdapter(mListAdapter);
        getLoaderManager().initLoader(R.id.complectation_spec_loader, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == R.id.complectation_spec_loader) {
            return new CursorLoader(
                    getActivity().getApplicationContext(),
                    ComplectationSpecProvider.URI, null,
                    ComplectationSpecProvider.Columns.COMPLECTATION_ID + "=?",
                    new String[]{String.valueOf(mInvoiceId)},
                    ComplectationSpecProvider.Columns.SMATRES_NOMEN + " DESC"
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == R.id.complectation_spec_loader) {
            mListAdapter.swapCursor(data);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == R.id.complectation_spec_loader) {
            mListAdapter.swapCursor(null);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Cursor cursor = mListAdapter.getCursor();
        final Double nstoreQuant = ComplectationSpecProvider.getNSTOREQUANT(cursor);
        final Double nplanQuant = ComplectationSpecProvider.getPLANQUANT(cursor);
        final long selectedNRN = ComplectationSpecProvider.getNRN(cursor);
        if (nstoreQuant.compareTo(new Double(0)) == 0) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "На складе нет остатков", Toast.LENGTH_LONG).show();
            return;
        }
        final View dialogView = inflater.inflate(R.layout.change_complectation_spec_menu_layout, null);
        final EditText valueView = (EditText) dialogView.findViewById(R.id.spec_quant_text);
        valueView.setText(nplanQuant + "");
        builder.setView(dialogView);
        builder
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                        final String text = valueView.getText().toString();
                        //text = text.isEmpty() ? "0" : text;
                        if (text.isEmpty()) {
                            return;
                        }
                        Double nquant = Double.valueOf(text);
                        if (nstoreQuant >= nquant) {
                            final Status[] status = {null};
                            Thread myThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Nquant n = new Nquant();
                                        n.setNQUANT(text);
                                        status[0] = ParusService.getService().complectationSpecComplect(selectedNRN, n);
                                    } catch (RetrofitError e) {
                                        //try {
                                        //  Log.e("ERROR>>", new Scanner(e.getResponse().getBody().in(), "UTF-8").useDelimiter("\\A").next());
                                        //} catch (IOException e1) {

                                        Log.e("ERROR>>", e.toString());
                                        // }

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
                            //обновляем спеку
                            if (status[0] != null && status[0].getNRN() != -1) {
                                onRefresh(ParusApplication.sAccount);
                                Toast.makeText(getActivity(), "Скомплектовано", Toast.LENGTH_LONG).show();
                            } else {
                                if (status[0] == null) {
                                    Toast.makeText(getActivity(), "Ошибка ответа сервера", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(), status[0].getSMSG(), Toast.LENGTH_LONG).show();
                                }
                            }

                           /* Toast.makeText(getActivity().getApplicationContext(),
                                    "text__" + text + "==" + selectedNRN, Toast.LENGTH_LONG).show();*/
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

    @Override
    protected void onRefresh(Account account) {
        final Bundle extras = new Bundle();
        extras.putLong(SyncAdapter.KEY_COMPLECTATION_ID, mInvoiceId);
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
