package org.techbooster.app.abc.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import org.techbooster.app.abc.R;
import org.techbooster.app.abc.loaders.BazaarEntryLoader;
import org.techbooster.app.abc.models.BazaarEntry;
import org.techbooster.app.abc.tools.IntentUtils;
import org.techbooster.app.abc.views.BazaarListAdapter;

import java.util.List;

public class BazaarListFragment extends ListFragment {

    public static BazaarListFragment newInstance() {
        BazaarListFragment fragment = new BazaarListFragment();

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setDivider(null);
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
        BazaarListAdapter adapter = new BazaarListAdapter(getActivity());
        for (BazaarEntry entry : entries) {
            adapter.add(entry);
        }

        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter =
                new SwingBottomInAnimationAdapter(adapter);
        swingBottomInAnimationAdapter.setAnimationDelayMillis(500);
        swingBottomInAnimationAdapter.setAbsListView(getListView());

        setListAdapter(swingBottomInAnimationAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        BazaarEntry entry = (BazaarEntry) l.getAdapter().getItem(position);
        if (!TextUtils.isEmpty(entry.getUrl())) {
            IntentUtils.openUrl(getActivity(), entry.getUrl());
        }
    }
}
