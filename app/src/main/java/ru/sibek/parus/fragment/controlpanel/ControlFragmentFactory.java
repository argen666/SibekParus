package ru.sibek.parus.fragment.controlpanel;

import android.app.Fragment;

import ru.sibek.parus.fragment.Types;

/**
 * Created by Developer on 13.04.2015.
 */
public class ControlFragmentFactory {
    public static Fragment getControlPanel(String type, int position) {
        Fragment cp = null;
        //TODO instantiate fragment without if....with Type
        if (type == Types.ININVOICES) {
            cp = InvoiceControlPanelFragment.newInstance(position);
        }
        if (type == Types.INORDERS) {
            cp = OrderControlPanelFragment.newInstance(position);
        }
        if (type == Types.TRANSINDEPT) {
            cp = TransindeptControlPanelFragment.newInstance(position);
        }
        return cp;
    }

    public static Fragment getControlPanel(String type) {
        return getControlPanel(type, 0);
    }
}
