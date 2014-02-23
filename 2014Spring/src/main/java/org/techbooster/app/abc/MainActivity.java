package org.techbooster.app.abc;

import com.sys1yagi.indirectinjector.IndirectInjector;

import org.techbooster.app.abc.controllers.FragmentTransitionController;
import org.techbooster.app.abc.fragments.NavigationDrawerFragment;
import org.techbooster.app.abc.tools.DrawerLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity
        implements FragmentTransitionController {

    private CharSequence mTitle;

    private DrawerLayoutManager mDrawerLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mTitle = getTitle();
        mDrawerLayoutManager =
                DrawerLayoutManager.newInstance(drawerLayout);

        IndirectInjector.addDependency(this, this);
        IndirectInjector.addDependency(this, drawerLayout);
        IndirectInjector.addDependency(this, (FrameLayout) findViewById(R.id.navigation_drawer));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            mDrawerLayoutManager.toggle();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void addFragment(Fragment fragment) {

    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, fragment)
                .commit();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

    }

}
