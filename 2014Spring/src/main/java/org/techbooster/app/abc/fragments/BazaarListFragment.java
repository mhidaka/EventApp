package org.techbooster.app.abc.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.devspark.progressfragment.ProgressFragment;
import com.etsy.android.grid.StaggeredGridView;
import com.sys1yagi.indirectinjector.IndirectInjector;

import org.techbooster.app.abc.R;
import org.techbooster.app.abc.controllers.ActionBarController;
import org.techbooster.app.abc.loaders.BazaarEntryLoader;
import org.techbooster.app.abc.models.BazaarEntry;
import org.techbooster.app.abc.tools.IntentUtils;
import org.techbooster.app.abc.views.BazaarListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BazaarListFragment extends ProgressFragment {

    private final static String STATE_KEY_BAZAAR_ENTRY = "bazaar_entry";

    public static BazaarListFragment newInstance() {
        BazaarListFragment fragment = new BazaarListFragment();
        return fragment;
    }

    private StaggeredGridView mStaggeredGridView;

    private List<BazaarEntry> mBazaarEntries;

    @Inject
    private ActionBarController mActionBarController;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        IndirectInjector.inject(getActivity(), this);

        mActionBarController.setTitle(R.string.menu_bazaar);

        setContentView(R.layout.fragment_bazzar);
        mStaggeredGridView = (StaggeredGridView) getView().findViewById(R.id.grid_view);
        setContentShown(false);

        if (savedInstanceState != null) {
            mBazaarEntries = savedInstanceState.getParcelableArrayList(STATE_KEY_BAZAAR_ENTRY);
            setupBazaarList(mBazaarEntries);
        } else {
            new BazaarEntryLoader(getActivity()).getEntries(new BazaarEntryLoader.Listener() {
                @Override
                public void onSuccess(List<BazaarEntry> entries) {
                    if (getActivity() != null) {
                        mBazaarEntries = entries;
                        setupBazaarList(entries);
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    setEmptyText(getString(R.string.network_error));
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBazaarEntries != null && !mBazaarEntries.isEmpty()) {
            outState.putParcelableArrayList(STATE_KEY_BAZAAR_ENTRY,
                    new ArrayList<BazaarEntry>(mBazaarEntries));
        }
    }

    private void setupBazaarList(List<BazaarEntry> entries) {
        final BazaarListAdapter adapter = new BazaarListAdapter(getActivity());
        for (BazaarEntry entry : entries) {
            adapter.add(entry);
        }
        mStaggeredGridView.setAdapter(adapter);
        mStaggeredGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BazaarEntry entry = adapter.getItem(position);
                if (!TextUtils.isEmpty(entry.getUrl())) {
                    IntentUtils.openUrl(getActivity(), entry.getUrl());
                }
            }
        });
        setContentShown(true);
    }

}
