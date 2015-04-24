package ru.sibek.parus.fragment;

import ru.sibek.parus.fragment.ininvoice.InvoicesListFragment;
import ru.sibek.parus.fragment.ininvoice.OrdersListFragment;
import ru.sibek.parus.fragment.outvoice.TransindeptListFragment;

/**
 * Created by Developer on 03.11.2014.
 */
public interface Types {
    final String ININVOICES_TAB = "ININVOICES_TAB";
    final String ININVOICES = InvoicesListFragment.class.getName();
    final String INORDERS = OrdersListFragment.class.getName();
    final String OUTINVOICES_TAB = "OUTINVOICES_TAB";
    final String TRANSINDEPT = TransindeptListFragment.class.getName();
}
