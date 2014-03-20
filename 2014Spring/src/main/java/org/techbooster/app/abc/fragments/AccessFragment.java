package org.techbooster.app.abc.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.sys1yagi.indirectinjector.IndirectInjector;

import org.techbooster.app.abc.R;
import org.techbooster.app.abc.controllers.ActionBarController;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AccessFragment extends Fragment {

    @InjectView(R.id.open_map_button)
    Button mOpenMapButton;

    @InjectView(R.id.access_webview)
    WebView mWebView;

    @Inject
    private ActionBarController mActionBarController;

    public static AccessFragment newInstance() {
        AccessFragment fragment = new AccessFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        IndirectInjector.inject(getActivity(), this);

        mActionBarController.setTitle(R.string.menu_map);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_access, null, false);
        ButterKnife.inject(this, view);

        mWebView.loadUrl(getString(R.string.map_asset_html));
        mWebView.getSettings().setJavaScriptEnabled(false);

        return view;
    }

    @OnClick(R.id.open_map_button) void openMap() {
        final String action = android.content.Intent.ACTION_VIEW;
        final Uri uri = Uri.parse(getString(R.string.map_link));

        try {
            // Open with Maps app
            final Intent intent = new Intent(action, uri);
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // fall-back, do not specify app
            try {
                final Intent intent = new Intent(action, uri);
                startActivity(intent);
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.map_apps_not_found_error, Toast.LENGTH_SHORT);
            }
        }

    }
}
