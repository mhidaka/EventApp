package org.techbooster.app.abc.loaders;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.techbooster.app.abc.models.ConferenceSession;
import org.techbooster.app.abc.tools.VolleyManager;

import java.util.ArrayList;
import java.util.List;

public class ConferenceSessionLoader {

    public interface Listener {
        public void onSuccess(List<ConferenceSession> sessions);

        public void onError(VolleyError error);
    }

    private VolleyManager mVolleyManager;

    public ConferenceSessionLoader(Context context) {
        mVolleyManager = VolleyManager.getInstance(context);
    }

    public void getSessions(String conferenceUrl, final Listener listener) {
        mVolleyManager.get(conferenceUrl, new VolleyManager.ResponseListener() {
            @Override
            public void onResponse(String body) {
                List<ConferenceSession> sessions = new ArrayList<ConferenceSession>();
                Document document = Jsoup.parse(body);

                //track name
                Elements h1 = document.select("h1.entry-title");
                String trackName = null;
                if (!h1.isEmpty()) {
                    trackName = h1.first().text();
                }
                Elements elements = document.select("table.track");
                if (!elements.isEmpty()) {
                    Elements trs = elements.first().select("tr");
                    for (Element tr : trs) {
                        ConferenceSession session = new ConferenceSession();
                        session.setTrackName(trackName);
                        session.setSessionTitle(text(tr.select("span.session_title")));
                        session.setSpeakerName(text(tr.select("span.speaker_name")));
                        if (!tr.select("span.speaker_profile").isEmpty()) {
                            session.setSpeakerProfile(text(tr.select("span.speaker_profile")));
                        }
                        session.setBeginTime(text(tr.select("span.starttime")));
                        if (!tr.select("span.session_description").isEmpty()) {
                            session.setDescription(
                                    tr.select("span.session_description").first().text()
                                            .replace("【 講演内容 】<br />", "")
                            );
                        }
                        session.setRoom(tr.select("span.roomname").first().text());

                        sessions.add(session);
                    }
                }
                listener.onSuccess(sessions);
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(error);
            }
        });
    }

    private static String text(Elements elms){
        if (elms == null || elms.isEmpty()) {
            return "";
        }
        Element elm = elms.first();
        String text = elm.text();
        return text != null ? text : "";
    }
}
