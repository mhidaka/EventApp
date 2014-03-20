package org.techbooster.app.abc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.sys1yagi.indirectinjector.IndirectInjector;

import org.techbooster.app.abc.R;
import org.techbooster.app.abc.consts.RequestConsts;
import org.techbooster.app.abc.controllers.ActionBarController;

import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ConferenceFragment extends Fragment {

    enum Conference {
        KEYNOTE(R.string.conference_keynote,
                ConferenceSessionFragment.newInstance(RequestConsts.CONFERENCE_KEYNOTE_URL)),
        DESIGN(R.string.conference_design,
                ConferenceSessionFragment.newInstance(RequestConsts.CONFERENCE_DESIGN_URL)),
        MAKER(R.string.conference_maker,
                ConferenceSessionFragment.newInstance(RequestConsts.CONFERENCE_MAKER_URL)),
        CONTENT(R.string.conference_content,
                ConferenceSessionFragment.newInstance(RequestConsts.CONFERENCE_CONTENT_URL)),
        DEV(R.string.conference_dev,
                ConferenceSessionFragment.newInstance(RequestConsts.CONFERENCE_DEV_URL)),
        DEVICE(R.string.conference_device,
                ConferenceSessionFragment.newInstance(RequestConsts.CONFERENCE_DEVICE_URL)),
        EFFECTIVE(R.string.conference_effective,
                ConferenceSessionFragment.newInstance(RequestConsts.CONFERENCE_EFFECTIVE_URL)),
        LT(R.string.conference_lt,
                ConferenceSessionFragment.newInstance(RequestConsts.CONFERENCE_LT_URL)),
        BUSINESS(R.string.conference_business,
                ConferenceSessionFragment.newInstance(RequestConsts.CONFERENCE_BUSINESS_URL));

        private Fragment mFragment;
        private int mTitleResId;

        Conference(int titleId, Fragment fragment) {
            mTitleResId = titleId;
            mFragment = fragment;
        }
    }

    public static ConferenceFragment newInstance() {
        return new ConferenceFragment();
    }

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip mPagerSlidingTabStrip;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

    @Inject
    private ActionBarController mActionBarController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conference, null, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IndirectInjector.inject(getActivity(), this);
        mActionBarController.setTitle(R.string.menu_conference);

        FragmentPagerAdapter adapter =
                new FragmentPagerAdapter(getChildFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        return Conference.values()[position].mFragment;
                    }

                    @Override
                    public int getCount() {
                        return Conference.values().length;
                    }

                    @Override
                    public CharSequence getPageTitle(int position) {
                        return getString(Conference.values()[position].mTitleResId);
                    }
                };
        mViewPager.setAdapter(adapter);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field f = Fragment.class.getDeclaredField("mChildFragmentManager");
            f.setAccessible(true);
            f.set(this, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
