package ru.sibek.parus.fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
import ru.sibek.parus.sqlite.ininvoices.InvoiceSpecProvider;
import ru.sibek.parus.sqlite.storages.CellsProvider;
import ru.sibek.parus.sqlite.storages.RacksProvider;
import ru.sibek.parus.sqlite.storages.StorageProvider;

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
    private long storageId;
    private long rackId;
    private long cellId;

    private Button mSaveButton;
    private Button mCancelButton;
    private boolean isChanged = false;

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
            isChanged = false;
            mSaveButton = (Button) getView().findViewById(R.id.button_detail_spec_save);
            mCancelButton = (Button) getView().findViewById(R.id.button_detail_spec_cancel);

            mCancelButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (isChanged) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder
                                .setMessage("Отменить все изменения?")
                                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        getFragmentManager().popBackStack();
                                    }
                                })
                                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                    }
                                })
                                .show();
                    } else getFragmentManager().popBackStack();

                }
            });
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
        storageId = StorageHelper.getStorageIdByNstore(getActivity(), InvoiceSpecProvider.getNSTORE(c));
        mStorageButton.setText(localStorage == null ? InvoiceSpecProvider.getSSTORE(c) == null ? "Cклад: - " : "Cклад: " + InvoiceSpecProvider.getSSTORE(c) : "Cклад: " + localStorage);

        mRackButton = (Button) getView().findViewById(R.id.button_rack);
        mCellButton = (Button) getView().findViewById(R.id.button_cell);
        //  if (InvoiceSpecProvider.getNDISTRIBUTION_SIGN(c) != 0) {
        if (InvoiceSpecProvider.getNRACK(c) != 0) {
            rackId = StorageHelper.getRackIdByNrack(getActivity(), InvoiceSpecProvider.getNRACK(c));
            mRackButton.setVisibility(View.VISIBLE);
            mCellButton.setVisibility(View.VISIBLE);
        }


        final String localRack = InvoiceSpecProvider.getLOCAL_SRACK(c);
        mRackButton.setText(localRack == null ? InvoiceSpecProvider.getSRACK(c) == null ? "Стеллаж: - " : "Стеллаж: " + InvoiceSpecProvider.getSRACK(c) : "Стеллаж: " + localRack);


        final String localCell = InvoiceSpecProvider.getLOCAL_SCELL(c);
        mCellButton.setText(localCell == null ? InvoiceSpecProvider.getSCELL(c) == null ? "Место: - " : "Место: " + InvoiceSpecProvider.getSCELL(c) : "Место: " + localCell);

        mRackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isChanged = true;
                registerForContextMenu(mRackButton);
                getActivity().openContextMenu(mRackButton);

            }
        });

        mCellButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isChanged = true;
                registerForContextMenu(mCellButton);
                getActivity().openContextMenu(mCellButton);

            }
        });


        //  }
        /**/
        mStorageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isChanged = true;
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
                        menu.add(R.id.button_storage, (int) StorageProvider.getId(cur), Menu.NONE, StorageProvider.getSNUMB(cur));
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
            Cursor cur = StorageHelper.getRacksByStorageId(getActivity(), storageId);

            try {
                if (cur.moveToFirst()) {
                    do {
                        menu.add(R.id.button_rack, (int) RacksProvider.getId(cur), Menu.NONE, RacksProvider.getSFULLNAME(cur));
                    } while (cur.moveToNext());
                } else {
                    Toast.makeText(getActivity(), "Нет стеллажей", Toast.LENGTH_LONG).show();
                }
            } finally {
                cur.close();
            }
        }

        if (v.getId() == R.id.button_cell) {
            menu.setHeaderTitle("Выберите место!");
            Cursor cur = StorageHelper.getCellsByRackId(getActivity(), rackId);

            try {
                if (cur.moveToFirst()) {
                    do {
                        menu.add(R.id.button_cell, (int) CellsProvider.getId(cur), Menu.NONE, CellsProvider.getSFULLNAME(cur));
                    } while (cur.moveToNext());
                } else {
                    Toast.makeText(getActivity(), "Нет мест", Toast.LENGTH_LONG).show();
                }
            } finally {
                cur.close();
            }
        }


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
            case R.id.button_storage: {
                storageId = item.getItemId();
                mStorageButton.setText("Cклад: " + item.toString());
                mRackButton.setText("Стеллаж: -");
/*                if (mRackButton==null) {mRackButton=(Button) getView().findViewById(R.id.button_rack);}
                if (mCellButton==null) {mCellButton=(Button) getView().findViewById(R.id.button_cell);}*/
                if (!StorageHelper.getRacksByStorageId(getActivity(), storageId).moveToFirst()) {
                    mRackButton.setVisibility(View.GONE);
                    mCellButton.setVisibility(View.GONE);

                } else {
                    mRackButton.setVisibility(View.VISIBLE);
                    if (mCellButton.getVisibility() == View.VISIBLE)
                        mCellButton.setVisibility(View.GONE);
                }
                //if (mRackButton!=null){mRackButton.setEnabled(false);}
                Log.d("CKICK STORAGE>>>>", item.toString() + ">>" + item.getItemId());
                break;
            }
            case R.id.button_rack: {
                rackId = item.getItemId();
                mRackButton.setText("Стеллаж: " + item.toString());
                mCellButton.setText("Место: -");
                mCellButton.setVisibility(View.VISIBLE);
                Log.d("CKICK RACK>>>>", item.toString() + ">>" + item.getItemId());
                break;
            }
            case R.id.button_cell: {
                cellId = item.getItemId();
                mCellButton.setText("Место: " + item.toString());
                break;
            }
        }

        return super.onContextItemSelected(item);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == R.id.spec_detail_loader) {
            // mListAdapter.swapCursor(null);
            //
        }
    }

}
