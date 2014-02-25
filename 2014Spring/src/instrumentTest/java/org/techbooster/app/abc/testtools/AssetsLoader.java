package org.techbooster.app.abc.testtools;

import android.content.Context;

import org.apache.commons.io.IOUtils;

public class AssetsLoader {

    public static String load(Context context, String fileName) {
        try {
            return IOUtils.toString(context.getResources().getAssets().open(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
