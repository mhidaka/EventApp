package org.techbooster.app.abc.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.devspark.progressfragment.ProgressFragment;
import com.etsy.android.grid.StaggeredGridView;

import org.techbooster.app.abc.R;
import org.techbooster.app.abc.loaders.BazaarEntryLoader;
import org.techbooster.app.abc.models.BazaarEntry;
import org.techbooster.app.abc.tools.IntentUtils;
import org.techbooster.app.abc.views.BazaarListAdapter;

import java.util.List;

public class BazaarListFragment extends ProgressFragment {

    public static BazaarListFragment newInstance() {
        BazaarListFragment fragment = new BazaarListFragment();
        return fragment;
    }

    private StaggeredGridView mStaggeredGridView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setContentView(R.layout.fragment_bazzar);
        mStaggeredGridView = (StaggeredGridView) getView().findViewById(R.id.grid_view);
        setContentShown(false);

        new BazaarEntryLoader(getActivity()).getEntries(new BazaarEntryLoader.Listener() {
            @Override
            public void onSuccess(List<BazaarEntry> entries) {
                if (getActivity() != null) {
                    setupBazaarList(entries);
                }
            }

            @Override
            public void onError(VolleyError error) {
                setEmptyText(getString(R.string.network_error));
            }
        });
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
