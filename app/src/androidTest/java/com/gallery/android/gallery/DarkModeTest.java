package com.gallery.android.gallery;

import android.Manifest;
import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.util.Log;
import android.widget.EditText;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class DarkModeTest {
    public ActivityTestRule<MainActivity> activityTestRule;
    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {

            });

    @Test
    public void buttonExists(){
        onView(withId(R.id.NModeswitchAB)).perform(click());
    }

    @Test
    public void darkModeTest(){
        Activity a=activityTestRule.getActivity();
        onView(withId(R.id.search)).perform(click());
        EditText search=a.findViewById(R.id.search_bar);
        onView(isAssignableFrom(EditText.class)).perform(typeText("aaaaaa"));
        Integer buttonColor1 =  search.getCurrentTextColor();
        Log.e("hoh",String.valueOf(buttonColor1));
        onView(withId(R.id.NModeswitchAB)).perform(click());
        Activity b=activityTestRule.getActivity();
        onView(withId(R.id.search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("bbbbbbb")) ;
        EditText search2=b.findViewById(R.id.search_bar);
        Integer buttonColor2 =  search2.getCurrentTextColor();
        Assert.assertNotEquals(buttonColor1,buttonColor2);
    }
}
