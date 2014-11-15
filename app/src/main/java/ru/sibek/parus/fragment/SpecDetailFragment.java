package ru.sibek.parus.fragment;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.sibek.parus.R;
import ru.sibek.parus.sqlite.InvoiceSpecProvider;
import ru.sibek.parus.sqlite.RacksProvider;
import ru.sibek.parus.sqlite.StorageProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String KEY_SPEC_ID = "ru.sibek.parus.KEY_SPEC_ID";
    private long mSpecId;
    private Cursor cur;
    private Button mStorageButton;
    private Button mRackButton;
    private Button mCellButton;
    private EditText mQuantText;
    private TextView mTitle;
    private TextView mMeas;

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
            showButtons(data);
            /*NumberPicker np = (NumberPicker) getActivity().findViewById(R.id.numberPicker1);
            np.setDisplayedValues( new String[] {
                    InvoiceSpecProvider.getSSTORE(data),"DDDDDD", "United Kingdom" } );*/
        }
    }

    private void showButtons(Cursor c) {
        mTitle = (TextView) getView().findViewById(R.id.title_detail_spec);
        mTitle.setText(InvoiceSpecProvider.getSNOMENNAME(c));
        mMeas = (TextView) getView().findViewById(R.id.text_meas_spec);
        mMeas.setText(InvoiceSpecProvider.getSMEAS_MAIN(c).toUpperCase());
        mQuantText = (EditText) getView().findViewById(R.id.text_quant);
        double localQuant = InvoiceSpecProvider.getLOCAL_NQUANT(c);
        double quant = InvoiceSpecProvider.getNQUANT(c);
        if (localQuant == 0) {
            if (quant == (long) quant) {
                mQuantText.setText(String.format("%d", (long) quant));
            } else {
                mQuantText.setText(String.format("%s", quant));
            }
        } else {
            if (localQuant == (long) localQuant) {
                mQuantText.setText(String.format("%d", (long) localQuant));
            } else {
                mQuantText.setText(String.format("%s", localQuant));
            }
        }
        mStorageButton = (Button) getView().findViewById(R.id.button_storage);

        final String localStorage = InvoiceSpecProvider.getLOCAL_STORE(c);
        mStorageButton.setText(localStorage == null ? InvoiceSpecProvider.getSSTORE(c) == null ? "Cклад: - " : "Cклад: " + InvoiceSpecProvider.getSSTORE(c) : "Cклад: " + localStorage);
        if (InvoiceSpecProvider.getNDISTRIBUTION_SIGN(c) != 0) {
            mRackButton = (Button) getView().findViewById(R.id.button_rack);
            mRackButton.setVisibility(View.VISIBLE);
            final String localRack = InvoiceSpecProvider.getLOCAL_SRACK(c);
            mRackButton.setText(localRack == null ? InvoiceSpecProvider.getSRACK(c) == null ? "Стеллаж: - " : "Стеллаж: " + InvoiceSpecProvider.getSRACK(c) : "Стеллаж: " + localRack);
            mCellButton = (Button) getView().findViewById(R.id.button_cell);
            mCellButton.setVisibility(View.VISIBLE);
            final String localCell = InvoiceSpecProvider.getLOCAL_SCELL(c);
            mCellButton.setText(localCell == null ? InvoiceSpecProvider.getSCELL(c) == null ? "Место: - " : "Место: " + InvoiceSpecProvider.getSCELL(c) : "Место: " + localCell);
        }
       /* mRackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                registerForContextMenu(mRackButton);
                getActivity().openContextMenu(mRackButton);

            }
        });*/
        mStorageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                registerForContextMenu(mStorageButton);
                getActivity().openContextMenu(mStorageButton);

            }
        });


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.button_storage) {
            menu.setHeaderTitle("Выберите склад!");
            Cursor cur = getActivity().getContentResolver().query(
                    StorageProvider.URI, new String[]{
                            StorageProvider.Columns._ID,
                            StorageProvider.Columns.SNUMB
                    }, null, null, StorageProvider.Columns.SNUMB + " ASC"
            );

            try {
                if (cur.moveToFirst()) {
                    do {
                        menu.add(Menu.NONE, (int) StorageProvider.getId(cur), Menu.NONE, StorageProvider.getSNUMB(cur));
                    } while (cur.moveToNext());
                } else {
                    Toast.makeText(getActivity(), "Нет складов", Toast.LENGTH_LONG).show();
                }
            } finally {
                cur.close();
            }


        }

        if (v.getId() == R.id.button_rack) {
            menu.setHeaderTitle("Выберите стеллаж!");
            Cursor cur = getActivity().getContentResolver().query(
                    RacksProvider.URI, new String[]{
                            RacksProvider.Columns._ID,
                            RacksProvider.Columns.SFULLNAME
                    }, null, null, RacksProvider.Columns.SFULLNAME + " ASC"
            );

            try {
                if (cur.moveToFirst()) {
                    do {
                        menu.add(Menu.NONE, (int) RacksProvider.getId(cur), Menu.NONE, RacksProvider.getSFULLNAME(cur));
                    } while (cur.moveToNext());
                } else {
                    Toast.makeText(getActivity(), "Нет стеллажей", Toast.LENGTH_LONG).show();
                }
            } finally {
                cur.close();
            }
        }

        if (v.getId() == R.id.button_cell) {
            menu.setHeaderTitle("Select Storage!");
            menu.add(Menu.NONE, 0, Menu.NONE, "Menu A");
            menu.add(Menu.NONE, 1, Menu.NONE, "Menu B");
        }


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
        // item.
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == R.id.spec_detail_loader) {
            // mListAdapter.swapCursor(null);
        }
    }

}
