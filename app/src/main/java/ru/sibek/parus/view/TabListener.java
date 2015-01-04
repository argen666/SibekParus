package ru.sibek.parus.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;

import ru.sibek.parus.R;
import ru.sibek.parus.fragment.ControlPanelFragment;
import ru.sibek.parus.fragment.LogoFragment;
import ru.sibek.parus.fragment.Types;

/**
 * Created by argen666 on 01.11.14.
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private ControlPanelFragment cPanel;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;

    /**
     * Constructor used each time a new tab is created.
     *
     * @param activity The host Activity, used to instantiate the fragment
     * @param tag      The identifier tag for the fragment
     * @param clz      The fragment's Class, used to instantiate the fragment
     */
    public TabListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

    /* The following are each of the ActionBar.TabListener callbacks */

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
        // TextView emptyDetailView = ((TextView) mActivity.findViewById(R.id.detail_empty_textView));
        //  Fragment detail = mActivity.getFragmentManager().findFragmentById(R.id.detail_frame);
        if (mTag == Types.ININVOICES) {
            if (cPanel == null) {
                cPanel = ControlPanelFragment.newInstance(Types.ININVOICES);
                ft.add(R.id.control_panel_frame, cPanel);
                Log.d("IN_NULL", cPanel.getId() + "");
            } else {
                Log.d("IN_!NULL", cPanel.getId() + "");
                ft.attach(cPanel);
                //ft.attach(cPanel);
                // запихать ControlPanelFr которф    предварительно сохранить
            }
            //     emptyDetailView.setVisibility(View.VISIBLE);
            //       emptyDetailView.setText("Выберите накладную");


        } else {

            Fragment f = mActivity.getFragmentManager().findFragmentById(R.id.detail_frame);
            if (f != null) ft.remove(f);
            return;
            /*if (cPanel == null) {
                cPanel=ControlPanelFragment.newInstance("other");
                ft.add(R.id.control_panel_frame, cPanel);
                Log.d("OTHER_NULL", cPanel.getId()+"");
            } else {
                Log.d("OTHER_!NULL", cPanel.getId()+"");
                ft.replace(R.id.control_panel_frame,cPanel);
            }*/

            //       emptyDetailView.setVisibility(View.VISIBLE);
            //       emptyDetailView.setText("Выберите empty");
        }

        Fragment logo = mActivity.getFragmentManager().findFragmentById(R.id.frame1);
        if (mFragment == null) {
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            if (logo != null && logo instanceof LogoFragment) {
                ft.detach(logo);
                ft.add(R.id.master_frame, mFragment, mTag);

            } else {
                ft.add(R.id.master_frame, mFragment, mTag);
            }
            // If not, instantiate and add it to the activity

        } else {
            // If it exists, simply attach it in order to show it
            if (logo != null && logo instanceof LogoFragment) {
                ft.detach(logo);
                ft.attach(mFragment);

            } else {
                ft.attach(mFragment);
            }
        }


    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);

        }
        if (cPanel != null) {
            ft.detach(cPanel);
        }
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
}