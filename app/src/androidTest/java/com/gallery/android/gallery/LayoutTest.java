package com.gallery.android.gallery;

import android.Manifest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LayoutTest {

    private ActivityTestRule<MainActivity> activityTestRule;

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("test1.png");
                    TestHelper.createFile("test2.png");
                }
            });

    @AfterClass
    public static void tearDownClass() {
        TestHelper.deleteFile("test1.png");
        TestHelper.deleteFile("test2.png");
    }

    @Test
    public void testImagesVisible() {

        RecyclerView rView=activityTestRule.getActivity().recyclerImages;
        for (int childCount = rView.getChildCount(), i = 0; i < childCount; ++i) {

            AdapterImages.ViewHolderImages holder = (AdapterImages.ViewHolderImages) rView.getChildViewHolder(rView.getChildAt(i));
            int id = holder.photo.getId();
            onView(withId(id)).check(matches(isDisplayed()));
        }
    }
}
