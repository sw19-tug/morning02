package com.gallery.android.gallery;


import android.Manifest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.TextView;

import org.checkerframework.checker.units.qual.A;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParentIndex;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void DeleteTags() throws Throwable{

        checkAddTag();

        Integer count = activityTestRule2.getActivity().recyclerTags.getAdapter().getItemCount();

        AdapterTags tv = (AdapterTags) activityTestRule2.getActivity().recyclerTags.getAdapter();



        for(Integer i = tv.tags_.size()-1; i >= 0; i--){
            String name = tv.tags_.get(i).getName();
            onView(withId(R.id.TagsRecyclerId)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(i, RecyclerItemClick.clickChildViewWithId(R.id.delete_tag)));

            for (int n = 0; n < tv.tags_.size()-1;n++) {
                assertNotEquals(name, (tv.tags_.get(n).getName()));
            }
        }
    }

    @Test
    public void assignTag() throws Throwable {

        checkAddTag();

        AdapterTags tv = (AdapterTags) activityTestRule2.getActivity().recyclerTags.getAdapter();

        for (int i = 0; i < tv.tags_.size(); i++) {

            onView(withId(R.id.TagsRecyclerId)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(i, RecyclerItemClick.clickChildViewWithId(R.id.tag_check_box)));
        }

        AdapterImages apt = (AdapterImages)activityTestRule.getActivity().recyclerImages.getAdapter();
        ImageContainer img = apt.getListImages().get(0);


        assertNotNull(img.getTags());
        assertTrue(img.getTags().size() == tv.tags_.size());

        for(int i = 0; i < tv.tags_.size(); i++) {
            assertTrue(img.getTags().contains(tv.tags_.get(i)));
        }

    }

    @Test
    public void assignTagSearch() throws Throwable {

        checkAddTag();

        AdapterTags tv = (AdapterTags) activityTestRule2.getActivity().recyclerTags.getAdapter();

        for (int i = 0; i < tv.tags_.size(); i++) {

            onView(withId(R.id.TagsRecyclerId)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(i, RecyclerItemClick.clickChildViewWithId(R.id.tag_check_box)));

        }

        //onView(withId(R.id.apply_button)).perform(click());

       pressBack();
       pressBack();

        for (int i = 0; i < tv.tags_.size(); i++) {

            onView(withId(R.id.search_bar)).check(matches(isDisplayed()));
            onView(withId(R.id.search_bar)).perform(typeText(tv.tags_.get(i).getName()));
            onView(withId(R.id.search_bar)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        }
    }



    @Test
    public void checkTagApplied() throws Throwable {

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

        onView(withId(R.id.tagsButton)).perform(click());


        onView(withId(R.id.tag_check_box)).check(matches(isDisplayed()));
        /*onView(withId(1)).perform(click());

        CheckBox checkBox = activityTestRule2.getActivity().findViewById(1);
        checkBox.setChecked(true);*/








    }




}
