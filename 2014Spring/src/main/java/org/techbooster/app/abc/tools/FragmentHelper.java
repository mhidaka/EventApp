package org.techbooster.app.abc.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

public class FragmentHelper {


    public static boolean isCurrentFragment(FragmentManager fragmentManager,
                                            Class<? extends Fragment> clazz) {
        Fragment fragment = null;
        List<Fragment> fragments = fragmentManager.getFragments();
        int index = fragments.size();
        while (fragment == null && index >= 0) {
            index--;
            fragment = fragments.get(index);
        }
        return fragment != null && fragment.getClass().equals(clazz);
    }
}
