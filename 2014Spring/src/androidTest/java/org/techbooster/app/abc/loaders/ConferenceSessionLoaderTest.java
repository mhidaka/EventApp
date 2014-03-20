package org.techbooster.app.abc.loaders;

import android.test.InstrumentationTestCase;

import com.android.volley.VolleyError;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.techbooster.app.abc.consts.UrlConsts;
import org.techbooster.app.abc.models.ConferenceSession;
import org.techbooster.app.abc.testtools.AssetsLoader;
import org.techbooster.app.abc.tools.VolleyManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

public class ConferenceSessionLoaderTest extends InstrumentationTestCase {

    @InjectMocks
    ConferenceSessionLoader mConferenceSessionLoader;

    @MockitoAnnotations.Mock
    VolleyManager mVolleyManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache",
                getInstrumentation().getTargetContext().getCacheDir().getPath());
    }

    public void testGetSessions() throws Exception {
        mConferenceSessionLoader =
                new ConferenceSessionLoader(getInstrumentation().getTargetContext());

        MockitoAnnotations.initMocks(this);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String html = AssetsLoader
                        .load(getInstrumentation().getContext(), "conference_keynote.html");

                VolleyManager.ResponseListener listener =
                        (VolleyManager.ResponseListener) invocation.getArguments()[1];

                listener.onResponse(html);
                return null;
            }
        }).when(mVolleyManager).get(anyString(), any(VolleyManager.ResponseListener.class));

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        mConferenceSessionLoader.getSessions(UrlConsts.CONFERENCE_KEYNOTE_URL,
                new ConferenceSessionLoader.Listener() {
                    @Override
                    public void onSuccess(List<ConferenceSession> sessions) {
                        assertNotNull(sessions);
                        assertFalse(sessions.isEmpty());
                        assertEquals(8, sessions.size());
                        ConferenceSession session = sessions.get(0);
                        assertEquals("基調講演／Reborn", session.getTrackName());
                        assertEquals("グローバル・ネットワークの成立と新しい技術革新の展望", session.getSessionTitle());
                        assertEquals("丸山 不二夫", session.getSpeakerName());
                        assertEquals(
                                "特定非営利活動法人 日本Androidの会 名誉会長 東京大学卒。一橋大学大学院博士課程修了。 1987年から稚内に移住。稚内北星学園短期大学学長。 2000年の同短大の四年制大学移行に伴い、稚内北星学園大学学長を二期務める。 その後、早稲田大学情報生産システム研究科客員教授、 公立はこだて未来大学情報科学部特任教授。 日本Javaユーザ会会長、日本Androidの会会長、クラウド研究会代表等を歴任。",
                                session.getSpeakerProfile());
                        assertEquals("10:15", session.getBeginTime());
                        assertEquals("Rebornトラック(秋葉原ダイビル2F T0)", session.getRoom());
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        fail();
                        countDownLatch.countDown();
                    }
                }
        );

        countDownLatch.await(5, TimeUnit.SECONDS);
        assertEquals(0, countDownLatch.getCount());
    }
}
