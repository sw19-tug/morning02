package com.gallery.android.gallery;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SelectorTest {


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkSelectedProperty() throws NoSuchFieldException {


        MainActivity.class.getField("selection_mode");


    }

    @Test
    public void checkLongPressSelection() throws Throwable {
        RecyclerView res = activityTestRule.getActivity().findViewById(R.id.RecyclerId);

        assertFalse(activityTestRule.getActivity().selection_mode);

        runOnUiThread(new MyRunnable(res, 0) {

            public void run() {

                this.resycler_view.findViewHolderForAdapterPosition(adapter_position).itemView.performLongClick();


                try {
                    Thread.sleep(100);

                } catch (InterruptedException ex1) {
                    return;
                }
            }
        });

        assertTrue(activityTestRule.getActivity().selection_mode);
    }
}
