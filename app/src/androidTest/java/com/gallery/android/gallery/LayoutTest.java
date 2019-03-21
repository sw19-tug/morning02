package com.gallery.android.gallery;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
@RunWith(AndroidJUnit4.class)
public class LayoutTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testImagesVisible() {
        onView(withId(R.id.image01)).check(matches(isDisplayed()));
        onView(withId(R.id.image02)).check(matches(isDisplayed()));
        onView(withId(R.id.image03)).check(matches(isDisplayed()));
        onView(withId(R.id.image04)).check(matches(isDisplayed()));
        onView(withId(R.id.image05)).check(matches(isDisplayed()));
        onView(withId(R.id.image06)).check(matches(isDisplayed()));
        onView(withId(R.id.image07)).check(matches(isDisplayed()));
        onView(withId(R.id.image08)).check(matches(isDisplayed()));
        onView(withId(R.id.image09)).check(matches(isDisplayed()));
        onView(withId(R.id.image10)).check(matches(isDisplayed()));
        onView(withId(R.id.image11)).check(matches(isDisplayed()));
        onView(withId(R.id.image12)).check(matches(isDisplayed()));
        onView(withId(R.id.image13)).check(matches(isDisplayed()));
        onView(withId(R.id.image14)).check(matches(isDisplayed()));
        onView(withId(R.id.image15)).check(matches(isDisplayed()));

    }
    @Test
    public void testFileExists(){

    }


}
