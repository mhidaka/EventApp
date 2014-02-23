package org.techbooster.app.abc.tools;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;

public class DrawerLayoutManager {

    private DrawerLayout mDrawerLayout;

    public static DrawerLayoutManager newInstance(DrawerLayout drawerLayout) {
        DrawerLayoutManager manager = new DrawerLayoutManager();
        manager.mDrawerLayout = drawerLayout;
        return manager;
    }

    public void toggle() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        } else {
            mDrawerLayout.openDrawer(Gravity.START);
        }
    }

}
