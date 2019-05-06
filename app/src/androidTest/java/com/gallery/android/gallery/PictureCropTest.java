package com.gallery.android.gallery;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PictureCropTest {


    @Rule
    public ActivityTestRule<ImageFullscreenActivity> activityTestRule = new ActivityTestRule<>(ImageFullscreenActivity.class);

    @Test
    public void testButtonsVisible() {
        onView(withId(R.id.button_crop)).check(matches(isDisplayed()));
    }

}
