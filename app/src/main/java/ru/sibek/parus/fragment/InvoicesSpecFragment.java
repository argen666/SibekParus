package ru.sibek.parus.fragment;

import android.accounts.Account;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import ru.sibek.parus.R;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.sqlite.InvoiceSpecProvider;
import ru.sibek.parus.sync.SyncAdapter;
import ru.sibek.parus.widget.CursorBinderAdapter;

/**
 * Created by Developer on 13.10.2014.
 */
public class InvoicesSpecFragment extends SwipeToRefreshList implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_INVOICE_ID = "ru.sibek.parus.KEY_INVOICE_ID";

    private long mInvoiceId;
    private ImageView iconState;
    // Fragment specFragment=null;
    Map<Long, Fragment> specsDetails = new HashMap<Long, Fragment>();

    public Fragment getSpecDetailByID(Long id) {
        return specsDetails.get(id);
    }

    private CursorAdapter mListAdapter;

    public static InvoicesSpecFragment newInstance(long invoiceId) {
        final InvoicesSpecFragment fragment = new InvoicesSpecFragment();
        final Bundle args = new Bundle();
        args.putLong(KEY_INVOICE_ID, invoiceId);
        fragment.setArguments(args);
        return fragment;
    }

    public void setId(long id) {
        this.getArguments().remove(KEY_INVOICE_ID);
        this.getArguments().putLong(KEY_INVOICE_ID, id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInvoiceId = getArguments().getLong(KEY_INVOICE_ID, -1);
        mListAdapter = new CursorBinderAdapter(getActivity(), R.layout.li_invoice_spec);
        setListAdapter(mListAdapter);
        getLoaderManager().initLoader(R.id.invoices_spec_loader, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == R.id.invoices_spec_loader) {
            return new CursorLoader(
                    getActivity().getApplicationContext(),
                    InvoiceSpecProvider.URI, null,
                    InvoiceSpecProvider.Columns.INVOICE_ID + "=?",
                    new String[]{String.valueOf(mInvoiceId)},
                    InvoiceSpecProvider.Columns.SNOMEN + " DESC"
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == R.id.invoices_spec_loader) {
            mListAdapter.swapCursor(data);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == R.id.invoices_spec_loader) {
            mListAdapter.swapCursor(null);
        }
    }
//TODO:uncomment this
  /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        iconState = (ImageView) ((LinearLayout) view).findViewById(R.id.spec_image);
        if ((int) iconState.getTag() == R.drawable.invoice_spec_non_accepted) {

            Log.d("SPEC_ITEM_CLICK: ", "pos: " + position + "; id= " + id);
        //TODO: check this!
        final FragmentManager fm = getActivity().getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //ft.addToBackStack(null);

        if (getSpecDetailByID(id) == null) {
            SpecDetailFragment specDetail = SpecDetailFragment.newInstance(id);
            specsDetails.put(id, specDetail);
            ft.replace(R.id.detail_frame, specDetail).addToBackStack(null).commit();
            Log.d("CREATE DETAIL>>>>", specDetail.getId() + "");
        } else {
            ft.replace(R.id.detail_frame, (SpecDetailFragment) getSpecDetailByID(id)).addToBackStack(null).commit();
            Log.d("RESTORE DETAIL>>>>", ((SpecDetailFragment) getSpecDetailByID(id)).getId() + "");
        }
        } else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Снимите галочку", Toast.LENGTH_LONG);
            toast.show();
        }
    }*/

    @Override
    protected void onRefresh(Account account) {
        final Bundle extras = new Bundle();
        extras.putLong(SyncAdapter.KEY_INVOICE_ID, mInvoiceId);
        ContentResolver.requestSync(account, ParusAccount.AUTHORITY, extras);
    }

    @Override
    protected void onSyncStatusChanged(Account account, boolean isSyncActive) {
        setRefreshing(isSyncActive);
    }

}
