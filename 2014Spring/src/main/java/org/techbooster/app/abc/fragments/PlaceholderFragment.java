package org.techbooster.app.abc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sys1yagi.indirectinjector.IndirectInjector;

import org.techbooster.app.abc.R;
import org.techbooster.app.abc.controllers.ActionBarController;

import javax.inject.Inject;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    ActionBarController mActionBarController;

    private int mSectionNumber;

    public PlaceholderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);

        textView.setText(Integer.toString(mSectionNumber));
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IndirectInjector.inject(getActivity(), this);
        mActionBarController.setTitle("place holder " + mSectionNumber);
    }
}
