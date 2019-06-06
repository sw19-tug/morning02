package com.gallery.android.gallery;

import android.Manifest;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import android.view.KeyEvent;
import android.widget.CheckBox;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.gallery.android.gallery.TestUtils.withRecyclerView;
import static org.junit.Assert.assertFalse;
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
            });


    public void goToTagsActivity() {

        onView(withId(R.id.RecyclerId)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.ImageLayout)));


        onView(withId(R.id.fullscreen_image_view)).check(matches(isDisplayed()));
        onView(withId(R.id.tagsButton)).perform(click());
    }

    @Test
    public void checkAddTag() throws Throwable, InterruptedException {

        goToTagsActivity();


        onView(withId(R.id.button_tagsactivity_menu)).perform(click());
        onView(withText("Add new tag")).check(matches(isDisplayed()));
        onView(withText("Add new tag")).perform(click());


        onView(withId(R.id.input)).perform(typeText("TestNewTag"));
        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        onView(withText("TestNewTag")).check(matches(isDisplayed()));

    }

    @Test
    public void checkAddDoubleTags() throws Throwable {

        checkAddTag();

        int itemcount = activityTestRule2.getActivity().recyclerTags.getAdapter().getItemCount();

        onView(withId(R.id.button_tagsactivity_menu)).perform(click());
        onView(withText("Add new tag")).check(matches(isDisplayed()));
        onView(withText("Add new tag")).perform(click());


        onView(withId(R.id.input)).perform(typeText("TestNewTag"));
        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        int itemcount_after = activityTestRule2.getActivity().recyclerTags.getAdapter().getItemCount();
        assertSame(itemcount, itemcount_after);
    }

    @Test
    public void DeleteTags() throws Throwable{

        goToTagsActivity();

        onView(withId(R.id.recyclerview_tagsactivity_tagscontainer)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.button_tagitem_delete)));

        Thread.sleep(100);
        pressBack();
        onView(withId(R.id.tagsButton)).perform(click());

        onView(withText("T1")).check(doesNotExist());
    }

    @Test
    public void assignTag() throws Throwable {

        for (int i = 0; i < activityTestRule.getActivity().recyclerImages.getAdapter().getItemCount(); i++) {
            goToTagsActivity();

            onView(withId(R.id.recyclerview_tagsactivity_tagscontainer)).check(matches(isDisplayed()));

            Thread.sleep(100);


            for (int n = 0; n < activityTestRule2.getActivity().recyclerTags.getAdapter().getItemCount(); n++) {

                onView(withId(R.id.recyclerview_tagsactivity_tagscontainer))
                        .perform(RecyclerViewActions.actionOnItemAtPosition(n, MyViewAction.clickChildViewWithId(R.id.checkbox_tagitem_tick)));
            }

            pressBack();
            Thread.sleep(100);
            onView(withId(R.id.tagsButton)).perform(click());
            Thread.sleep(1000);
            onView(withId(R.id.recyclerview_tagsactivity_tagscontainer)).check(matches(isDisplayed()));

            for (int n = 0; n < activityTestRule2.getActivity().recyclerTags.getAdapter().getItemCount(); n++) {
                onView(withRecyclerView(R.id.recyclerview_tagsactivity_tagscontainer)
                        .atPositionOnView(n, R.id.checkbox_tagitem_tick))
                        .check(matches(isChecked()));
            }
            pressBack();
            pressBack();
        }

    }

    @Test
    public void removeTagfromPicture() throws Throwable {

        checkIfAllSelected();
        onView(withId(R.id.recyclerview_tagsactivity_tagscontainer))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.checkbox_tagitem_tick)));

        pressBack();
        onView(withId(R.id.tagsButton)).perform(click());

        onView(withRecyclerView(R.id.recyclerview_tagsactivity_tagscontainer)
                .atPositionOnView(0, R.id.checkbox_tagitem_tick))
                .check(matches(isNotChecked()));
    }
/*
    @Test
    public void assignTagSearch() throws Throwable {

        checkAddTag();
        AdapterTags tv = (AdapterTags) activityTestRule2.getActivity().recyclerTags.getAdapter();

        for (int i = 0; i < tv.tags_.size(); i++) {

            onView(withId(R.id.recyclerview_tagsactivity_tagscontainer)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(i, RecyclerItemClick.clickChildViewWithId(R.id.checkbox_tagitem_tick)));
        }

        //onView(withId(R.id.apply_button)).perform(click());

       pressBack();
       pressBack();

        for (int i = 0; i < tv.tags_.size(); i++) {

            onView(withId(R.id.search_bar)).check(matches(isDisplayed()));
            onView(withId(R.id.search_bar)).perform(typeText(tv.tags_.get(i).getName()));
            onView(withId(R.id.search_bar)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        }
    }*/


    @Test
    public void removeAllButtonExists() throws Throwable, InterruptedException{
        goToTagsActivity();

        onView(withId(R.id.button_tagsactivity_menu)).check(matches(isDisplayed()));
        onView(withId(R.id.button_tagsactivity_menu)).perform(click());

        onView(withText("Remove all selected tags")).check(matches(isDisplayed()));
        onView(withText("Remove all selected tags")).perform(click());
    }

    @Test
    public void selectAllButtonExists() throws Throwable, InterruptedException{
        goToTagsActivity();

        onView(withId(R.id.button_tagsactivity_menu)).check(matches(isDisplayed()));
        onView(withId(R.id.button_tagsactivity_menu)).perform(click());

        onView(withText("Select all tags")).check(matches(isDisplayed()));
    }

    @Test
    public void checkIfAllSelected() throws Throwable, InterruptedException {
        goToTagsActivity();

        onView(withId(R.id.button_tagsactivity_menu)).perform(click());

        onView(withText("Select all tags")).perform(click());

        for(int i=0; i< activityTestRule2.getActivity().recyclerTags.getAdapter().getItemCount(); i++) {
            CheckBox checkbox = activityTestRule2.getActivity().recyclerTags.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.checkbox_tagitem_tick);
            assertTrue(checkbox.isChecked());
        }
    }

    @Test
    public void checkIfAllDeselected() throws Throwable, InterruptedException {
        checkIfAllSelected();

        onView(withId(R.id.button_tagsactivity_menu)).perform(click());

        onView(withText("Remove all selected tags")).perform(click());

        for(int i=0; i< activityTestRule2.getActivity().recyclerTags.getAdapter().getItemCount(); i++) {
            CheckBox checkbox = activityTestRule2.getActivity().recyclerTags.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.checkbox_tagitem_tick);
            assertFalse(checkbox.isChecked());
        }
    }

    @Test
    public void assignAndDeleteTag() {

        goToTagsActivity();

        onView(withId(R.id.recyclerview_tagsactivity_tagscontainer)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, RecyclerItemClick.clickChildViewWithId(R.id.checkbox_tagitem_tick)));

        pressBack();
        onView(withId(R.id.tagsButton)).perform(click());

        onView(withId(R.id.recyclerview_tagsactivity_tagscontainer)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, RecyclerItemClick.clickChildViewWithId(R.id.button_tagitem_delete)));

        pressBack();
        onView(withId(R.id.tagsButton)).perform(click());

    }

    @Test
    public void add_assign_remove_Tags() throws InterruptedException {

        for (int i = 0; i < activityTestRule.getActivity().recyclerImages.getAdapter().getItemCount(); i++) {
            goToTagsActivity();

            onView(withId(R.id.recyclerview_tagsactivity_tagscontainer)).check(matches(isDisplayed()));

            Thread.sleep(100);


            for (int n = 0; n < activityTestRule2.getActivity().recyclerTags.getAdapter().getItemCount(); n++) {

                onView(withId(R.id.recyclerview_tagsactivity_tagscontainer))
                        .perform(RecyclerViewActions.actionOnItemAtPosition(n, MyViewAction.clickChildViewWithId(R.id.checkbox_tagitem_tick)));
            }

            pressBack();
            Thread.sleep(100);
            onView(withId(R.id.tagsButton)).perform(click());
            Thread.sleep(1000);
            onView(withId(R.id.recyclerview_tagsactivity_tagscontainer)).check(matches(isDisplayed()));

            for (int n = 0; n < activityTestRule2.getActivity().recyclerTags.getAdapter().getItemCount(); n++) {
                onView(withRecyclerView(R.id.recyclerview_tagsactivity_tagscontainer)
                        .atPositionOnView(n, R.id.checkbox_tagitem_tick))
                        .check(matches(isChecked()));
            }
            pressBack();
            pressBack();

            goToTagsActivity();

            onView(withId(R.id.recyclerview_tagsactivity_tagscontainer)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(0, RecyclerItemClick.clickChildViewWithId(R.id.checkbox_tagitem_tick)));

            pressBack();
            onView(withId(R.id.tagsButton)).perform(click());

            onView(withId(R.id.recyclerview_tagsactivity_tagscontainer)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(0, RecyclerItemClick.clickChildViewWithId(R.id.button_tagitem_delete)));

            pressBack();
            onView(withId(R.id.tagsButton)).perform(click());
        }

    }

}

