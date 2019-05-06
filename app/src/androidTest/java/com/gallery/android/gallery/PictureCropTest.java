package com.gallery.android.gallery;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.Intents.intended;

public class PictureCropTest {


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testButtonsVisible() {
        onView(withId(R.id.idImage)).perform(click());
        onView(withId(R.id.cropButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testDialogIsShown() {

        onView(withId(R.id.idImage)).perform(click());
        onView(withId(R.id.cropButton)).perform(click());

        intended(hasComponent(CropImageActivity.class.getName()));
    }

}
