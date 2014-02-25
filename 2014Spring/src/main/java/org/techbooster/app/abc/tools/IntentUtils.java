package org.techbooster.app.abc.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {

    public static void openUrl(Context context, String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }
}
