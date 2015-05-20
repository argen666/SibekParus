package ru.sibek.parus.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.sibek.parus.R;

/**
 * Created by Developer on 03.11.2014.
 */
public class DummyFragment extends Fragment {

    String text = "Раздел в разработке";
    int i=0;
    public DummyFragment() {

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        i++;
        TextView t = (TextView)view.findViewById(R.id.text);
        t.setText(text);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dummy_fragment, container, false);

        return rootView;
    }
}