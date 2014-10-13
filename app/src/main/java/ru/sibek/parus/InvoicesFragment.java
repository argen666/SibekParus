package ru.sibek.parus;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.sibek.parus.mappers.Invoices;
import ru.sibek.parus.mappers.Invoices.Item;

public class InvoicesFragment extends ListFragment {
    OnHeadlineSelectedListener mCallback;
    Object data=null;
    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnHeadlineSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onMasterItemSelected(Item position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an array adapter for the list view, using the Ipsum headlines array

        NetworkTask n = NetworkTask.getInstance();
        n.execute("listInvoices","59945");

        try {
            data = n.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ItemListAdapter adapter = new ItemListAdapter(getActivity());
        if (data instanceof Invoices){
        adapter.setItems(((Invoices) data).getItems());}
        setListAdapter(adapter);


    }

    @Override
    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
       /* if (getFragmentManager().findFragmentById(R.id.invoices_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }*/
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item

       //Item item =  ((List<Item>) v.getTag(0)).get(position);
       //mCallback.onMasterItemSelected(data);
        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }
}