package ru.sibek.parus.view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;

import ru.sibek.parus.R;
import ru.sibek.parus.fragment.LogoFragment;
import ru.sibek.parus.fragment.Types;
import ru.sibek.parus.fragment.controlpanel.ControlFragmentFactory;

/**
 * Created by argen666 on 01.11.14.
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private Fragment cPanel;
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

    public void setcPanel(Fragment cPanel) {
        this.cPanel = cPanel;
    }

    /* The following are each of the ActionBar.TabListener callbacks */

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
        // TextView emptyDetailView = ((TextView) mActivity.findViewById(R.id.detail_empty_textView));
        //  Fragment detail = mActivity.getFragmentManager().findFragmentById(R.id.detail_frame);
        if (mTag == Types.ININVOICES_TAB) {
            if (cPanel == null) {
                //cPanel = InvoiceControlPanelFragment.newInstance(mClass.getName());
                cPanel = ControlFragmentFactory.getControlPanel(Types.ININVOICES);
                ft.add(R.id.control_panel_frame, cPanel);
                Log.d("IN_NULL", cPanel.getId() + "");
            } else {


                Log.d("IN_!NULL", cPanel.getId() + "");
                ft.attach(cPanel);

            }

        } else {
            if (mTag == Types.OUTINVOICES_TAB) {
                if (cPanel == null) {
                    //cPanel = InvoiceControlPanelFragment.newInstance(mClass.getName());
                    cPanel = ControlFragmentFactory.getControlPanel(Types.TRANSINDEPT);
                    ft.add(R.id.control_panel_frame, cPanel);
                    Log.d("IN_NULL", cPanel.getId() + "");
                } else {
                    // if (cPanel instanceof InvoiceControlPanelFragment) {
                    Log.d("IN_!NULL", cPanel.getId() + "");
                    ft.attach(cPanel);
                    // }

                }
            } else {
                if (mTag == Types.COMPLECTATIONS_TAB) {
                    if (cPanel == null) {
                        //cPanel = InvoiceControlPanelFragment.newInstance(mClass.getName());
                        cPanel = ControlFragmentFactory.getControlPanel(Types.COMPLECTATION);
                        ft.add(R.id.control_panel_frame, cPanel);
                        Log.d("IN_NULL", cPanel.getId() + "");
                    } else {
                        // if (cPanel instanceof InvoiceControlPanelFragment) {
                        Log.d("IN_!NULL", cPanel.getId() + "");
                        ft.attach(cPanel);
                        // }

                    }
                } else {

                    if (mTag == "nomenclature") {

                        if (cPanel != null) {
                            ft.detach(cPanel);
                        }
                        mFragment = Fragment.instantiate(mActivity, DummyFragment.class.getName());

                        ft.replace(R.id.master_frame, mFragment);
                        Fragment f = mActivity.getFragmentManager().findFragmentById(R.id.detail_frame);
                        if (f != null) ft.remove(f);
                    } else {

                        if (mTag == "stores") {

                            if (cPanel != null) {
                                ft.detach(cPanel);
                            }
                            mFragment = Fragment.instantiate(mActivity, DummyFragment.class.getName());

                            ft.replace(R.id.master_frame, mFragment);
                            Fragment f = mActivity.getFragmentManager().findFragmentById(R.id.detail_frame);
                            if (f != null) ft.remove(f);
                        }
                    }
            }
            }
            /*Fragment f = mActivity.getFragmentManager().findFragmentById(R.id.detail_frame);
            if (f != null) ft.remove(f);
            Fragment f1 = mActivity.getFragmentManager().findFragmentById(R.id.master_frame);
            if (f1 != null) ft.remove(f1);
            return;*/

        }

        Fragment logo = mActivity.getFragmentManager().findFragmentById(R.id.frame1);
        if (logo != null && logo instanceof LogoFragment) {
            ft.detach(logo);
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