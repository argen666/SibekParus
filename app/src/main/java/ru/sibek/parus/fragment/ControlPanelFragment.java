package ru.sibek.parus.fragment;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import ru.sibek.parus.R;

public class ControlPanelFragment extends Fragment {
    private static final String TYPE = "type";
    //private static final String ARG_PARAM2 = "param2";

    private String type;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
            Log.d("QQQ",type);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_control_panel, container, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.control_panel1);

        if (layout.getChildCount()>0)
        {
          layout.removeAllViews();
        }
        if (type==Types.ININVOICES){

           view= getIninvoicesView(view,layout);
        }
        return view;
    }

    private View getIninvoicesView(View view, LinearLayout layout) {
            TextView moduleName = new TextView(getActivity());
        moduleName.setText(R.string.ininvoice);
        moduleName.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        moduleName.setTextSize(26);
        moduleName.setId(R.id.ininvoice_module_name);
        moduleName.setGravity(Gravity.CENTER_VERTICAL);
        moduleName.setLayoutParams(new LayoutParams(
                /*LayoutParams.WRAP_CONTENT,*/(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics()),
                LayoutParams.MATCH_PARENT/*,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics())*/
        ));
        moduleName.setPadding(20,0,0,0);
            layout.addView(moduleName);
       /*LinearLayout linLayout = new LinearLayout(getActivity());
        linLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams linLayoutParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        linLayout.setLayoutParams(linLayoutParam);*/
        TextView itemName = new TextView(getActivity());
       // itemName.setText("ВЭР 001.003.004 КЗ");
        itemName.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
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
        Button actionBtn = new Button(getActivity());
        actionBtn.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()),
                LayoutParams.MATCH_PARENT/*,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics())*/
                ));
        actionBtn.setId(R.id.ininvoice_button);
        actionBtn.setText("Отработать накладную");
        actionBtn.setVisibility(View.INVISIBLE);
            layout.addView(actionBtn);

        TextView itemDate = new TextView(getActivity());
        itemDate.setId(R.id.ininvoice_date);
        //itemDate.setText("12.05.2014,12:10");
        itemDate.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        itemDate.setTextSize(26);
        itemDate.setPadding(50,0,0,0);
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
