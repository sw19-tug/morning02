package com.gallery.android.gallery;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.Intents.intended;

//Code was parially taken from https://code.tutsplus.com/tutorials/capture-and-crop-an-image-with-the-device-camera--mobile-11458

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
        Intents.init();
        onView(withId(R.id.idImage)).perform(click());
        onView(withId(R.id.cropButton)).perform(click());

        intended(hasComponent(CropImageActivity.class.getName()));
    }

    @Test
    public void testEnteringCropState() {
        Intents.init();
        onView(withId(R.id.idImage)).perform(click());
        onView(withId(R.id.cropButton)).perform(click());

        intended(hasComponent("com.android.camera.action.CROP"));
    }

}
