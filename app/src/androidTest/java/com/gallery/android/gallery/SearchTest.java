package com.gallery.android.gallery;

import android.Manifest;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.io.File;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;

public class SearchTest {
    ActivityTestRule<MainActivity> activityTestRule;
    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {

                    TestHelper.createFile("testSearch.png");
                }
            });

    @Test
    public void buttonExistsTest(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()));
    }

    @Test
    public void testSearch(){

        onView(withId(R.id.search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("testSearch"), pressKey(KeyEvent.KEYCODE_ENTER));
        ImageContainer imagecontainer = ((AdapterImages)activityTestRule.getActivity().recyclerImages.getAdapter()).getListImages().get(0);
        assertTrue(imagecontainer.getFilename().contains("testSearch"));
    }
}
