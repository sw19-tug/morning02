package com.gallery.android.gallery;


import android.Manifest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.assertSame;

public class TagsTest {

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


    private ActivityTestRule<TagActivity> activityTestRule2;
    @Rule
    public final TestRule chain2 = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule2 = new ActivityTestRule<TagActivity>(TagActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("test.png");
                }
            });

    private ActivityTestRule<TagActivity> activityTestRule3;
    @Rule
    public final TestRule chain3 = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule3 = new ActivityTestRule<TagActivity>(TagActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("test.png");
                }
            });


    @Test
    public void checkAddTag() throws Throwable, InterruptedException {
        RecyclerView recycler_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        AdapterImages adapter_images = (AdapterImages) recycler_view.getAdapter();
            runOnUiThread(new MyRunnable(recycler_view, 0) {
                public void run() {
                    this.resycler_view.findViewHolderForAdapterPosition(0).itemView.performClick();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex1) {
                        return;
                    }
                }
            });
            onView(withId(R.id.fullscreen_image_view)).check(matches(isDisplayed()));
            onView(withId(R.id.tagsButton)).perform(click());
            onView(withId(R.id.add_tag)).perform(click());
            onView(withId(R.id.input)).check(matches(isDisplayed()));
            onView(withId(R.id.input)).perform(typeText("Test"));
            onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

            onView(withText("Test")).check(matches(isDisplayed()));


    }


    @Test
    public void checkAddDoubleTags() throws Throwable {

        checkAddTag();

        Integer count = activityTestRule2.getActivity().recyclerTags.getAdapter().getItemCount();

        onView(withId(R.id.add_tag)).perform(click());
        onView(withId(R.id.input)).check(matches(isDisplayed()));
        onView(withId(R.id.input)).perform(typeText("Test"));
        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        Integer count_after = activityTestRule2.getActivity().recyclerTags.getAdapter().getItemCount();
        assertSame(count , count_after);
    }




}
