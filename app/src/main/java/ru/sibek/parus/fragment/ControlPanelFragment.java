package ru.sibek.parus.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import ru.sibek.parus.ParusApplication;
import ru.sibek.parus.R;
import ru.sibek.parus.account.ParusAccount;
import ru.sibek.parus.sqlite.InvoiceProvider;
import ru.sibek.parus.sqlite.InvoiceSpecProvider;
import ru.sibek.parus.sync.SyncAdapter;

public class ControlPanelFragment extends Fragment {
    private static final String TYPE = "type";
    private static final String ENTITY_ID = "ENTITY_ID";
    //private static final String ARG_PARAM2 = "param2";

    private String type;
    private String itemTitle = "";
    private String strDate = "";
    String btnActText;
    private int visibility = View.INVISIBLE;

    TextView itemName;
    TextView itemDate;
    Button actionBtn;

    public static ControlPanelFragment newInstance(String type) {
        ControlPanelFragment fragment = new ControlPanelFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    public ControlPanelFragment() {
        // Required empty public constructor
    }

    public long getEntityId() {
        return getArguments().getLong(ENTITY_ID);
    }

    public void setEntityId(long entityId) {
        getArguments().putLong(ENTITY_ID, entityId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
            Log.d("QQQ", type);
            // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_control_panel, container, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.control_panel1);

        if (layout.getChildCount() > 0) {
            layout.removeAllViews();
        }
        if (type == Types.ININVOICES) {

            view = getIninvoicesView(view, layout);

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


                                /*class MyThread implements Runnable {
                                    long nrn;

                                    public MyThread(long nrn) {
                                        this.nrn = nrn;
                                    }

                                    public void run() {
                                        Response r = (Response) ParusService.getService().applyInvoiceAsFact(nrn);
                                        Log.d("CP_CLICK>>>", r.toString());
                                    }
                                }
                                Runnable r = new MyThread(nrn);
                                new Thread(r).start();*/


                            }

                        } else {
                            Log.d("CP_CLICK>>>", "NO SPECS???");
                        }
                    } finally {
                        cur.close();
                    }
                }
            });
        }
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
        actionBtn.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        if (btnActText == null) {
            actionBtn.setText("Отработать накладную");
            actionBtn.setEnabled(true);
        } else {
            actionBtn.setText(btnActText);
            actionBtn.setEnabled(false);

        }

    }

    private View getIninvoicesView(View view, LinearLayout layout) {
        TextView moduleName = new TextView(getActivity());
        moduleName.setText(R.string.ininvoice);
        moduleName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        moduleName.setTextSize(26);
        moduleName.setId(R.id.ininvoice_module_name);
        moduleName.setGravity(Gravity.CENTER_VERTICAL);
        moduleName.setLayoutParams(new LayoutParams(
                /*LayoutParams.WRAP_CONTENT,*/(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 430, getResources().getDisplayMetrics()),
                LayoutParams.MATCH_PARENT/*,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics())*/
        ));
        moduleName.setPadding(20, 0, 0, 0);
        layout.addView(moduleName);
       /*LinearLayout linLayout = new LinearLayout(getActivity());
        linLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams linLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        linLayout.setLayoutParams(linLayoutParam);*/
        itemName = new TextView(getActivity());
        itemName.setText(itemTitle);
        itemName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        itemName.setTextSize(26);
        itemName.setId(R.id.ininvoice_item_name);
        itemName.setGravity(Gravity.CENTER_VERTICAL);
        itemName.setLayoutParams(new LayoutParams(
                /*LayoutParams.WRAP_CONTENT,*/(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics()),
                LayoutParams.MATCH_PARENT/*,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics())*/
        ));
        //linLayout.addView(itemName);
        //layout.addView(linLayout);
        layout.addView(itemName);
        actionBtn = new Button(getActivity());
        actionBtn.setLayoutParams(new LayoutParams(
                /*LayoutParams.WRAP_CONTENT,*/(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 230, getResources().getDisplayMetrics()),
                LayoutParams.MATCH_PARENT/*,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics())*/
        ));
        actionBtn.setId(R.id.ininvoice_button);
        actionBtn.setText("Отработать накладную");
        actionBtn.setVisibility(visibility);
        layout.addView(actionBtn);

        itemDate = new TextView(getActivity());
        itemDate.setId(R.id.ininvoice_date);
        itemDate.setText(strDate);
        itemDate.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        itemDate.setTextSize(26);
        itemDate.setPadding(50, 0, 0, 0);
        itemDate.setGravity(Gravity.CENTER_VERTICAL);
        itemDate.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()),
                LayoutParams.MATCH_PARENT/*,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics())*/
        ));
        layout.addView(itemDate);
        return layout;
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
