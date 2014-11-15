package ru.sibek.parus.fragment;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.sibek.parus.R;
import ru.sibek.parus.sqlite.InvoiceSpecProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_SPEC_ID = "ru.sibek.parus.KEY_SPEC_ID";
    private long mSpecId;
    private Cursor cur;

    public SpecDetailFragment() {
        // Required empty public constructor
    }

    public static SpecDetailFragment newInstance(long specId) {
        final SpecDetailFragment fragment = new SpecDetailFragment();
        final Bundle args = new Bundle();
        args.putLong(KEY_SPEC_ID, specId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSpecId = getArguments().getLong(KEY_SPEC_ID, -1);

        getLoaderManager().initLoader(R.id.spec_detail_loader, null, this);


       /* NumberPicker np = (NumberPicker) getActivity().findViewById(R.id.numberPicker1);
        np.setMaxValue(2);
        np.setMinValue(0);
        np.setDisplayedValues(new String[]{
                "QQQQQQ", "DDDDDD", "United Kingdom"});*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spec_detail, container, false);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == R.id.spec_detail_loader) {
            return new CursorLoader(
                    getActivity().getApplicationContext(),
                    InvoiceSpecProvider.URI, null,
                    InvoiceSpecProvider.Columns._ID + "=?",
                    new String[]{String.valueOf(mSpecId)},
                    InvoiceSpecProvider.Columns.SNOMEN + " DESC"
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == R.id.spec_detail_loader) {
            // mListAdapter.swapCursor(data);
            //cur=data;
            data.moveToFirst();
            showButtons(InvoiceSpecProvider.getNDISTRIBUTION_SIGN(data));
            /*NumberPicker np = (NumberPicker) getActivity().findViewById(R.id.numberPicker1);
            np.setDisplayedValues( new String[] {
                    InvoiceSpecProvider.getSSTORE(data),"DDDDDD", "United Kingdom" } );*/
        }
    }

    private void showButtons(long nDistSign) {
        final Button button = (Button) getActivity().findViewById(R.id.button_storage);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getActivity().openContextMenu(button);
            }
        });

        registerForContextMenu(button);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == R.id.spec_detail_loader) {
            // mListAdapter.swapCursor(null);
        }
    }

}
