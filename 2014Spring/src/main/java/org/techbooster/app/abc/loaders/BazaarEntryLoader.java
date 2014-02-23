package org.techbooster.app.abc.loaders;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.techbooster.app.abc.consts.RequestConsts;
import org.techbooster.app.abc.models.BazaarEntry;
import org.techbooster.app.abc.tools.VolleyManager;

import java.util.ArrayList;
import java.util.List;

public class BazaarEntryLoader {

    public interface Listener {
        public void onSuccess(List<BazaarEntry> entries);

        public void onError(VolleyError error);
    }

    private VolleyManager mVolleyManager;

    public BazaarEntryLoader(Context context) {
        mVolleyManager = VolleyManager.getInstance(context);
    }

    public void getEntries(final Listener listener) {
        mVolleyManager.get(RequestConsts.BAZAAR_ENTRY_URL, new VolleyManager.ResponseListener() {
            @Override
            public void onResponse(String body) {
                List<BazaarEntry> entries = new ArrayList<BazaarEntry>();
                Document document = Jsoup.parse(body);
                Elements elements = document.select("table");
                for(Element element : elements){
                    BazaarEntry entry = new BazaarEntry();
                    Elements trs = element.select("tr");
                    if(trs.size() >= 3){
                        entry.setName(trs.get(0).text());
                        entry.setTitle(trs.get(1).text());
                        entry.setSummary(trs.get(2).text());
                    }
                    entries.add(entry);
                }
                listener.onSuccess(entries);
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(error);
            }
        });
    }

}
