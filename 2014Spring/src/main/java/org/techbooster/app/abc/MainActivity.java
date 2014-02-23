package org.techbooster.app.abc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.sys1yagi.indirectinjector.IndirectInjector;

import org.techbooster.app.abc.controllers.ActionBarController;
import org.techbooster.app.abc.controllers.FragmentTransitionController;
import org.techbooster.app.abc.tools.DrawerLayoutManager;

public class MainActivity extends ActionBarActivity
        implements FragmentTransitionController, ActionBarController {

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
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragmentManager.getFragments().size() > 1) {
            transaction.addToBackStack(null);
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void setTitle(int titleResId) {
        getSupportActionBar().setTitle(titleResId);
    }
}
