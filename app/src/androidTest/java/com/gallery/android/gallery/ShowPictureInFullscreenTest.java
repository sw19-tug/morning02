package com.gallery.android.gallery;

import android.Manifest;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ShowPictureInFullscreenTest {

    private ActivityTestRule<MainActivity> activityTestRule;

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("test.png");
                }
            });

    @AfterClass
    public static void tearDownClass() {
        TestHelper.deleteFile("test.png");
    }

    @Test
    public void testRecylerViewVisible() throws Exception {
        // clicks on a picture and checks if it is opened
        onView(withId(R.id.RecyclerId)).check(matches(isDisplayed()));
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.gallery.android.gallery", appContext.getPackageName());
    }

    @Test
    public void testClick() throws Throwable, InterruptedException {
        RecyclerView recycler_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        AdapterImages adapter_images = (AdapterImages) recycler_view.getAdapter();
        for (int i = 0; i < adapter_images.getItemCount(); i++ ) {
            runOnUiThread(new MyRunnable(recycler_view, i) {
                public void run() {
                    this.resycler_view.findViewHolderForAdapterPosition(adapter_position).itemView.performClick();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex1) {
                        return;
                    }
                }
            });
            onView(withId(R.id.fullscreen_image_view)).check(matches(isDisplayed()));
        }
    }
}
