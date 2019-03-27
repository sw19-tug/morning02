package com.gallery.android.gallery;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class FileLoaderJTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testPathsAreRetrieved(){
        FileLoader f=new FileLoader();
        assertNotNull(f);
        List<String> paths=f.getImagesPaths();
        assertNotNull(paths);
        assertFalse(paths.isEmpty());
        ImageContainer iC = new ImageContainer(paths.get(0));
        assertFalse(iC.getPath() == paths.get(0));
        assertNotNull(iC.getImage());
        RecyclerView rView=activityTestRule.getActivity().recyclerImages;
        for (int childCount = rView.getChildCount(), i = 0; i < childCount; ++i) {
            AdapterImages.ViewHolderImages holder = (AdapterImages.ViewHolderImages) rView.getChildViewHolder(rView.getChildAt(i));
            assertNotNull(holder.photo.getDrawable());

        }

    }
}
