package ru.sibek.parus.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sibek.parus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecDetailFragment extends Fragment {


    public SpecDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spec_detail, container, false);
    }


}
