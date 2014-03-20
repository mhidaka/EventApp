package org.techbooster.app.abc.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.sys1yagi.indirectinjector.IndirectInjector;

import org.techbooster.app.abc.R;
import org.techbooster.app.abc.consts.UrlConsts;
import org.techbooster.app.abc.controllers.ActionBarController;
import org.techbooster.app.abc.controllers.FragmentTransitionController;
import org.techbooster.app.abc.tools.IntentUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NavigationDrawerFragment extends Fragment {

    private final static String TAG = NavigationDrawerFragment.class.getSimpleName();

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    enum Menu {
        TOP(R.string.menu_top, TopFragment.newInstance()),
        SUMMARY(R.string.menu_summary, EventSummaryFragment.newInstance()),
        CONFERENCE(R.string.menu_conference, ConferenceFragment.newInstance()),
        BAZAAR(R.string.menu_bazaar, BazaarListFragment.newInstance()),
        MAP(R.string.menu_map, AccessFragment.newInstance()),
        OFFICIAL_SITE(R.string.menu_official_site, null) {
            @Override
            public void transitTo(Context context,
                                  FragmentTransitionController fragmentTransitionController) {
                IntentUtils.openUrl(context, UrlConsts.OFFICIAL_SITE_URL);
            }
        },
        OBFT(R.string.menu_obft, null) {
            @Override
            public void transitTo(Context context,
                                  FragmentTransitionController fragmentTransitionController) {
                IntentUtils.openUrl(context, UrlConsts.OBFT_URL);
            }
        },
        OSS_LICENSE(R.string.menu_open_source_license, OpenSourceLicenseFragment.newInstance()),;

        private int mTitleResId;
        private Fragment mFragment;

        Menu(int titleId, Fragment fragment) {
            mTitleResId = titleId;
            mFragment = fragment;
        }

        public int getTitleResId() {
            return mTitleResId;
        }

        public void transitTo(Context context,
                              FragmentTransitionController fragmentTransitionController) {
            fragmentTransitionController.replaceFragment(mFragment);
        }
    }

    @Inject
    private FragmentTransitionController mFragmentTransitionController;

    @Inject
    private ActionBarController mActionBarController;

    @Inject
    private DrawerLayout mDrawerLayout;

    @Inject
    private FrameLayout mFragmentContainerView;

    @InjectView(R.id.menu_list)
    ListView mDrawerListView;

    private ActionBarDrawerToggle mDrawerToggle;

    private int mCurrentSelectedPosition = 2;

    private boolean mFromSavedInstanceState;

    private boolean mUserLearnedDrawer;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IndirectInjector.inject(getActivity(), this);

        setHasOptionsMenu(true);
        setUpNavigationDrawer();
        if (savedInstanceState == null) {
            selectItem(mCurrentSelectedPosition);
        }
    }

    private boolean isClickFooterView(int position) {
        return mDrawerListView.getCount() - 1 == position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_navigation_drawer, null, false);
        ButterKnife.inject(this, view);

        ImageView jagLink = (ImageView) inflater.inflate(R.layout.listitem_jag_link, null, false);
        mDrawerListView.addFooterView(jagLink);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isClickFooterView(position)) {
                    IntentUtils.openUrl(getActivity(), getString(R.string.jag_link));
                    return;
                }
                selectItem(position);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1);
        for (Menu menu : Menu.values()) {
            adapter.add(getString(menu.getTitleResId()));
        }

        mDrawerListView.setAdapter(adapter);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

        return view;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void setUpNavigationDrawer() {

        if (mDrawerLayout == null || mFragmentContainerView == null) {
            Log.i(TAG, "non drawer mode.");
            return;
        }

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {

                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }

        Menu menu = Menu.values()[position];
        menu.transitTo(getActivity(), mFragmentTransitionController);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
}
