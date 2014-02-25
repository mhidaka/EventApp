package org.techbooster.app.abc.tools;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyManager {

    public interface ResponseListener {
        public void onResponse(String body);

        public void onError(VolleyError error);
    }

    private static VolleyManager INSTANCE;

    private RequestQueue mRequestQueue;

    private VolleyManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.start();
    }

    public static synchronized VolleyManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new VolleyManager(context.getApplicationContext());
        }
        return INSTANCE;
    }

    public void get(String url, final ResponseListener listener) {

        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        listener.onResponse(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        listener.onError(volleyError);
                    }
                }
        );

        mRequestQueue.add(request);
    }
}
