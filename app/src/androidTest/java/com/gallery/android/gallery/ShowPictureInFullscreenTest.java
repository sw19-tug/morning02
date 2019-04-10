package com.gallery.android.gallery;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class ShowPictureInFullscreenTest {


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRecylerViewVisible() throws Exception {
        // clicks on a picture and checks if it is opened
        onView(withId(R.id.RecyclerId)).check(matches(isDisplayed()));
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.gallery.android.gallery", appContext.getPackageName());
    }

    @Test
    public void testClick() throws Throwable, InterruptedException {

       //String path_check = "";

        RecyclerView res = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        AdapterImages ad2 = (AdapterImages) res.getAdapter();

        for (int i = 0; i < ad2.getItemCount(); i++ ) {


            runOnUiThread(new MyRunnable(res, i) {

                public void run() {

                    AdapterImages ad1 = (AdapterImages) this.resycler_view.getAdapter();

                    //path_check = ad.getListImages().get(i).getPath();
                    this.resycler_view.findViewHolderForAdapterPosition(adapter_position).itemView.performClick();


                    try {
                        Thread.sleep(100);



                    } catch (InterruptedException ex1) {
                        return;
                    }

                }
            });



            onView(withId(R.id.fullscreen_image_view)).check(matches(isDisplayed()));




        }



    }

}
