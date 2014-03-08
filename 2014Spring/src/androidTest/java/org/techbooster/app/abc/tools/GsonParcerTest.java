package org.techbooster.app.abc.tools;

import android.os.Parcelable;

import com.google.gson.reflect.TypeToken;

import junit.framework.TestCase;

import org.techbooster.app.abc.models.ConferenceSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GsonParcerTest extends TestCase {
    public void testWrap() throws Exception {
        ConferenceSession session = new ConferenceSession();
        session.setTrackName("test track");
        session.setSessionTitle("title");
        session.setSpeakerName("tester");
        Parcelable parcelable = GsonParcer.wrap(session);

        ConferenceSession object = GsonParcer.unwrap(parcelable);

        assertEquals("test track", object.getTrackName());
        assertEquals("title", object.getSessionTitle());
        assertEquals("tester", object.getSpeakerName());
    }

    public void testWrapList() throws Exception {

        List<ConferenceSession> sessions = new ArrayList<ConferenceSession>();
        for (int i = 0; i < 10; i++) {
            ConferenceSession session = new ConferenceSession();
            session.setTrackName("test track" + i);
            session.setSessionTitle("title" + i);
            session.setSpeakerName("tester" + i);
            sessions.add(session);
        }
        Parcelable parcelable = GsonParcer.wrap(sessions);

        List<ConferenceSession> objects =
                GsonParcer.unwrap(parcelable, new TypeToken<Collection<ConferenceSession>>() {
                });

        for (int i = 0; i < 10; i++) {
            ConferenceSession object = objects.get(i);
            assertEquals("test track" + i, object.getTrackName());
            assertEquals("title" + i, object.getSessionTitle());
            assertEquals("tester" + i, object.getSpeakerName());
        }

    }
}
