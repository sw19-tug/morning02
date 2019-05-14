package com.gallery.android.gallery;

import android.Manifest;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.Intents.intended;
import static com.googlecode.eyesfree.utils.LogUtils.TAG;

//Code was parially taken from https://code.tutsplus.com/tutorials/capture-and-crop-an-image-with-the-device-camera--mobile-11458

public class PictureCropTest {


    //@Rule
    //public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(new IntentsTestRule<MainActivity>(MainActivity.class) {

            });


    @Test
    public void testButtonsVisible() {
        onView(withId(R.id.idImage)).perform(click());
        onView(withId(R.id.cropButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testDialogIsShown() {
        //Intents.init();
        onView(withId(R.id.idImage)).perform(click());
        onView(withId(R.id.cropButton)).perform(click());

        intended(hasComponent(CropImageActivity.class.getName()));
        intended(hasAction("com.android.camera.action.CROP"));

    }




}
