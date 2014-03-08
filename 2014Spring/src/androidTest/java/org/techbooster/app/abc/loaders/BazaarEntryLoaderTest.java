package org.techbooster.app.abc.loaders;

import android.test.InstrumentationTestCase;

import com.android.volley.VolleyError;

import org.mockito.InjectMocks;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.techbooster.app.abc.models.BazaarEntry;
import org.techbooster.app.abc.testtools.AssetsLoader;
import org.techbooster.app.abc.tools.VolleyManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class BazaarEntryLoaderTest extends InstrumentationTestCase {

    @InjectMocks
    BazaarEntryLoader mBazaarEntryLoader;

    @MockitoAnnotations.Mock
    VolleyManager mVolleyManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache",
                getInstrumentation().getTargetContext().getCacheDir().getPath());
    }

    public void testGetEntries() throws Exception {
        mBazaarEntryLoader = new BazaarEntryLoader(getInstrumentation().getTargetContext());

        MockitoAnnotations.initMocks(this);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String html = AssetsLoader.load(getInstrumentation().getContext(), "bazaar.html");

                VolleyManager.ResponseListener listener =
                        (VolleyManager.ResponseListener) invocation.getArguments()[1];

                listener.onResponse(html);
                return null;
            }
        }).when(mVolleyManager).get(anyString(), any(VolleyManager.ResponseListener.class));

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        mBazaarEntryLoader.getEntries(new BazaarEntryLoader.Listener() {
            @Override
            public void onSuccess(List<BazaarEntry> entries) {
                assertNotNull(entries);
                assertFalse(entries.isEmpty());
                BazaarEntry entry = entries.get(0);
                assertEquals("B1 . アシアル株式会社", entry.getName());
                assertEquals("MonacaとOnsen UIで作るハイブリッドアプリ", entry.getTitle());
                assertEquals(
                        "HTML5ハイブリッドアプリケーション開発に特化した開発プラットフォームMonacaとUIフレームワークOnsen UIの展示を行います。",
                        entry.getSummary());
                countDownLatch.countDown();
            }

            @Override
            public void onError(VolleyError error) {
                fail();
                countDownLatch.countDown();
            }
        });

        countDownLatch.await(5, TimeUnit.SECONDS);
        assertEquals(0, countDownLatch.getCount());
    }
}
