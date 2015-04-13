package ru.sibek.parus.fragment.controlpanel;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ru.sibek.parus.ParusApplication;
import ru.sibek.parus.R;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.activity.InvoicesActivity;
import ru.sibek.parus.fragment.Types;
import ru.sibek.parus.sqlite.ininvoices.InvoiceProvider;
import ru.sibek.parus.sqlite.ininvoices.InvoiceSpecProvider;
import ru.sibek.parus.sync.SyncAdapter;
import ru.sibek.parus.view.DummyFragment;

public class OrderControlPanelFragment extends Fragment {
    private static final String SPINNER_POS = "spinner_position";
    private int spinnerPos;
    private String itemTitle = "";
    private String strDate = "";
    String btnActText;
    private int visibility = View.INVISIBLE;
    TextView itemName;
    TextView itemDate;
    Button actionBtn;
    Spinner spinner;

    private Fragment mFragment;

    public static OrderControlPanelFragment newInstance() {
        return OrderControlPanelFragment.newInstance(0);
    }

    public static OrderControlPanelFragment newInstance(int spinnerPosition) {
        OrderControlPanelFragment fragment = new OrderControlPanelFragment();
        Bundle args = new Bundle();
        args.putInt(SPINNER_POS, spinnerPosition);
        fragment.setArguments(args);
        return fragment;
    }

    public OrderControlPanelFragment() {
        // Required empty public constructor
    }


    public void addMasterFragment(Activity mActivity, int position) {


        switch (position) {
            case 0: {
                mFragment = Fragment.instantiate(mActivity, Types.ININVOICES);

                Fragment cp = ControlFragmentFactory.getControlPanel(Types.ININVOICES, position);
                ((InvoicesActivity) mActivity).replaceCP(cp);
                break;
            }
            case 1: {
                mFragment = Fragment.instantiate(mActivity, Types.INORDERS);

                    /*Fragment cp = ControlFragmentFactory.getControlPanel(Types.INORDERS, position);
                    ((InvoicesActivity) mActivity).replaceCP(cp);*/

                break;
            }
            case 2: {
                mFragment = Fragment.instantiate(mActivity, DummyFragment.class.getName());
                break;
            }
        }
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

        View view = inflater.inflate(R.layout.fragment_ininvoices_control_panel, container, false);
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
        actionBtn.setVisibility(btnVisibility);
        actionBtn.setTag(btnTag);
        if (btnActText == null) {
            actionBtn.setText("Отработать накладную");
            actionBtn.setEnabled(true);
        } else {
            actionBtn.setText(btnActText);
            actionBtn.setEnabled(false);

        }
    }

    private View getIninvoicesView(View view) {
        Log.d("getIninvoicesView", "getIninvoicesView");

        spinner = (Spinner) view.findViewById(R.id.ininvoice_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.invoice_array, R.layout.spinner_item);
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

        });


        itemName = (TextView) view.findViewById(R.id.ininvoice_item_name);
        itemName.setText(itemTitle);

        itemDate = (TextView) view.findViewById(R.id.ininvoice_date);
        itemDate.setText(strDate);

        actionBtn = (Button) view.findViewById(R.id.ininvoice_button);
        actionBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                long invoiceId = (long) actionBtn.getTag();
                Log.d("CP_CLICK>>>", invoiceId + "");
                Cursor cur = getActivity().getContentResolver().query(
                        InvoiceSpecProvider.URI, new String[]{
                                InvoiceSpecProvider.Columns.NDISTRIBUTION_SIGN,
                                InvoiceSpecProvider.Columns.SRACK,
                                InvoiceSpecProvider.Columns.LOCAL_ICON
                        }, InvoiceSpecProvider.Columns.INVOICE_ID + "=?", new String[]{String.valueOf(invoiceId)}, null
                );
                boolean isChecked = true;
                boolean isSrack = true;
                try {
                    if (cur.moveToFirst()) {
                        do {
                            if (InvoiceSpecProvider.getSRACK(cur) == null && InvoiceSpecProvider.getNDISTRIBUTION_SIGN(cur) == 1) {
                                isSrack = false;
                                break;
                            }

                            if (InvoiceSpecProvider.getLOCAL_ICON(cur) == 0 || InvoiceSpecProvider.getLOCAL_ICON(cur) == R.drawable.invoice_spec_non_accepted) {
                                isChecked = false;
                                break;
                            }
                        }
                        while (cur.moveToNext());

                        if (!isSrack) {
                            Toast.makeText(getActivity(), "Не определено место хранения!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (!isChecked) {
                            Toast.makeText(getActivity(), "Выберите все позиции!", Toast.LENGTH_LONG).show();
                        } else {
                            //POST
                            Log.d("CP_CLICK>>>", "POST!!!");
                            Cursor cNRN = getActivity().getContentResolver().query(
                                    InvoiceProvider.URI, new String[]{
                                            InvoiceProvider.Columns.NRN
                                    }, InvoiceProvider.Columns._ID + "=?", new String[]{String.valueOf(invoiceId)}, null
                            );
                            cNRN.moveToFirst();
                            long nrn = InvoiceProvider.getNRN(cNRN);

                            final Bundle syncExtras = new Bundle();
                            syncExtras.putLong(SyncAdapter.KEY_POST_INVOICE_ID, invoiceId);
                            ContentResolver.requestSync(ParusApplication.sAccount, ParusAccount.AUTHORITY, syncExtras);
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
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
