package ru.sibek.parus.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
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

import ru.sibek.parus.R;

public class ControlPanelFragment extends Fragment {
    private static final String TYPE = "type";
    //private static final String ARG_PARAM2 = "param2";

    private String type;
    private  String itemTitle="";
    private  String strDate="";
    String btnActText;
    private  int visibility=View.INVISIBLE;

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

    public void addInfoToPanel(String title, String date, int btnVisibility, String btnText, Object btnTag)
    {
        itemTitle=title;
        strDate=date;
        btnActText = btnText;
        visibility=btnVisibility;
        itemName.setText(itemTitle);
        itemDate.setText(date);
        actionBtn.setVisibility(btnVisibility);
        actionBtn.setTag(btnTag);
        actionBtn.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        if (btnActText==null){
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
        moduleName.setPadding(20,0,0,0);
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
