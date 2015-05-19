package ru.sibek.parus.fragment.controlpanel;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
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
import android.widget.TextView;
import android.widget.Toast;

import ru.sibek.parus.R;
import ru.sibek.parus.fragment.Types;
import ru.sibek.parus.fragment.complectation.ComplectationListFragment;
import ru.sibek.parus.sqlite.outinvoices.TransindeptProvider;
import ru.sibek.parus.sqlite.storages.StorageProvider;

public class ComplectationControlPanelFragment extends Fragment {
    private static final String SPINNER_POS = "spinner_position";
    private int spinnerPos;
    private String itemTitle = "";
    private String strDate = "";
    String btnActText;
    private int visibility = View.INVISIBLE;
    TextView itemName;
    TextView itemDate;
    Button actionBtn;
    Button storageBtn;

    private Fragment mFragment;
    private int storageNrn;

    public static ComplectationControlPanelFragment newInstance() {
        return ComplectationControlPanelFragment.newInstance(0);
    }

    public static ComplectationControlPanelFragment newInstance(int spinnerPosition) {
        ComplectationControlPanelFragment fragment = new ComplectationControlPanelFragment();
        Bundle args = new Bundle();
        args.putInt(SPINNER_POS, spinnerPosition);
        fragment.setArguments(args);
        return fragment;
    }

    public ComplectationControlPanelFragment() {
        // Required empty public constructor
    }


    public void addMasterFragment(Activity mActivity) {
        Fragment f = mActivity.getFragmentManager().findFragmentById(R.id.detail_frame);
        if (f != null) getFragmentManager().beginTransaction().remove(f).commit();
       clearPanel();

                mFragment = Fragment.instantiate(mActivity, Types.COMPLECTATION);

                /*Fragment cp = ControlFragmentFactory.getControlPanel(Types.TRANSINDEPT, position);
                ((InvoicesActivity) mActivity).replaceCP(cp);*/

        getFragmentManager().beginTransaction().replace(R.id.master_frame, mFragment).commit();
        Log.d("addMasterFragment", mFragment + "");


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            spinnerPos = getArguments().getInt(SPINNER_POS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_complectation_control_panel, container, false);
        view = getIninvoicesView(view);
        return view;
    }

    public void addInfoToPanel(String title, String date, int btnVisibility, String btnText, Object btnTag) {
        itemTitle = title;
        strDate = date;
        btnActText = btnText;
        visibility = btnVisibility;
        itemName.setText(itemTitle);
        itemDate.setText(date);
        itemName.setVisibility(btnVisibility);
        itemDate.setVisibility(btnVisibility);
        actionBtn.setVisibility(btnVisibility);
        storageBtn.setVisibility(btnVisibility);
        actionBtn.setTag(btnTag);
        if (btnActText == null) {
            actionBtn.setText("Сформировать РН");
            actionBtn.setEnabled(true);
        } else {
            actionBtn.setText(btnActText);
            actionBtn.setEnabled(false);

        }
    }

    public void clearPanel() {
        itemName.setVisibility(View.INVISIBLE);
        itemDate.setVisibility(View.INVISIBLE);
        actionBtn.setVisibility(View.INVISIBLE);
        storageBtn.setVisibility(View.INVISIBLE);
    }

    private View getIninvoicesView(View view) {
        Log.d("getIninvoicesView", "getIninvoicesView");
       /* spinner = (Spinner) view.findViewById(R.id.complectation_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.outvoice_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);
        spinner.setSelection(spinnerPos);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View v,
                                       int id, long arg3) {
                Log.d("Spinner_OnItemSelected", id + "");
                addMasterFragment(getActivity(), id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });*/


        itemName = (TextView) view.findViewById(R.id.complectation_item_name);
        itemName.setText(itemTitle);

        itemDate = (TextView) view.findViewById(R.id.complectation_date);
        itemDate.setText(strDate);
        storageBtn = (Button) view.findViewById(R.id.complectation_storage_button);
        storageBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                registerForContextMenu(storageBtn);
                getActivity().openContextMenu(storageBtn);
            }
        });

        actionBtn = (Button) view.findViewById(R.id.complectation_button);
        actionBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               /* long invoiceId = (long) actionBtn.getTag();
                Log.d("CP_CLICK>>>", invoiceId + "");
                Cursor cur = getActivity().getContentResolver().query(
                        TransindeptSpecProvider.URI, new String[]{
                                TransindeptSpecProvider.Columns.NDISTRIBUTION_SIGN,
                                TransindeptSpecProvider.Columns.SRACK,
                                TransindeptSpecProvider.Columns.LOCAL_ICON
                        }, TransindeptSpecProvider.Columns.complectation_ID + "=?", new String[]{String.valueOf(invoiceId)}, null
                );
                boolean isChecked = true;
                boolean isSrack = true;
                try {
                    if (cur.moveToFirst()) {
                        do {


                            if (TransindeptSpecProvider.getLOCAL_ICON(cur) == 0 || TransindeptSpecProvider.getLOCAL_ICON(cur) == R.drawable.invoice_spec_non_accepted) {
                                isChecked = false;
                                break;
                            }
                        }
                        while (cur.moveToNext());



                        if (!isChecked) {
                            Toast.makeText(getActivity(), "Выберите все позиции!", Toast.LENGTH_LONG).show();
                        } else {
                            //POST
                            Log.d("CP_CLICK>>>", "POST!!!");
                            Cursor cNRN = getActivity().getContentResolver().query(
                                    TransindeptProvider.URI, new String[]{
                                            TransindeptProvider.Columns.NRN
                                    }, TransindeptProvider.Columns._ID + "=?", new String[]{String.valueOf(invoiceId)}, null
                            );
                            cNRN.moveToFirst();
                            final long nrn = TransindeptProvider.getNRN(cNRN);

                            Thread myThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        ParusService.getService().applyTransindeptAsFactWithIncome(nrn);
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
                            //обновляем
                            ((TransindeptSpecFragment) ((TransindeptListFragment) mFragment).getSpecInvoiceByID(invoiceId)).refreshSpec(ParusApplication.sAccount);
                            Fragment f = getActivity().getFragmentManager().findFragmentById(R.id.detail_frame);
                            if (f != null)
                                getActivity().getFragmentManager().beginTransaction().remove(f).commit();
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Отработано", Toast.LENGTH_LONG).show();
                                }
                            }, 1500);
                        }

                    } else {
                        Log.d("CP_CLICK>>>", "NO SPECS???");
                    }
                } finally {
                    cur.close();
                }*/
            }
        });
        addMasterFragment(getActivity());
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.complectation_storage_button) {
            menu.setHeaderTitle("Выберите склад!");
            Cursor cur = getActivity().getContentResolver().query(
                    StorageProvider.URI, new String[]{
                            StorageProvider.Columns._ID,
                            StorageProvider.Columns.NRN,
                            StorageProvider.Columns.SNUMB
                    }, null, null, StorageProvider.Columns.SNUMB + " ASC"
            );
            menu.add(R.id.button_storage, 0, Menu.NONE, "Все склады");
            try {
                if (cur.moveToFirst()) {
                    do {
                        menu.add(R.id.button_storage, (int) StorageProvider.getNRN(cur), Menu.NONE, StorageProvider.getSNUMB(cur) + "");
                    } while (cur.moveToNext());
                } else {

                    Toast.makeText(getActivity(), "Нет складов", Toast.LENGTH_LONG).show();
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
                storageNrn = item.getItemId();
                storageBtn.setText(item.toString());
                LoaderManager loaderManager = mFragment.getLoaderManager();
                Log.d(">>>RESET>>", storageNrn + "");
                if (storageNrn != 0) {
                    String selection = TransindeptProvider.Columns.NSTORE + "=?";
                    String[] selectionArgs = new String[]{String.valueOf(storageNrn)};
                    Bundle args = new Bundle();
                    args.putString("selection", selection);
                    args.putStringArray("selectionArgs", selectionArgs);
                    loaderManager.restartLoader(R.id.complectation_loader, args, (ComplectationListFragment) mFragment);
                } else {
                    loaderManager.restartLoader(R.id.complectation_loader, null, (ComplectationListFragment) mFragment);
                }


            }
        }
        return super.onContextItemSelected(item);
    }

    ;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
