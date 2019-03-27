package com.gallery.android.gallery;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class LayoutTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testImagesVisible() {
        //Matcher<View> itemMatcher;
        //onView(withId(R.id.image15)).check(matches(isDisplayed()));
        RecyclerView rView=activityTestRule.getActivity().recyclerImages;
        for (int childCount = rView.getChildCount(), i = 0; i < childCount; ++i) {
            AdapterImages.ViewHolderImages holder = (AdapterImages.ViewHolderImages) rView.getChildViewHolder(rView.getChildAt(i));
            //itemMatcher.matches(holder.itemView);
            //onView(itemMatcher.matches(holder.itemView).matches(isDisplayed());
            onView(withId(R.id.idImage)).check(matches(isDisplayed()));

        }
        assert(1==1);
    }
    @Test
    public void testFileExists(){

    }


}
