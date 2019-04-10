package com.gallery.android.gallery;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SelectorTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUpClass() {
        TestHelper.createFile("test1.png");
        TestHelper.createFile("test2.png");
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        TestHelper.deleteFile("test1.png");
        TestHelper.deleteFile("test2.png");
    }

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
                    sleep(100);

                } catch (InterruptedException ex1) {
                    return;
                }
            }
        });
        assertTrue(activityTestRule.getActivity().selection_mode);
    }

    @Test
    public void checkSelectedItemList() throws Throwable {
        checkLongPressSelection();
        MainActivity.class.getField("selection_list");

        Field selection_field = MainActivity.class.getField("selection_list");
        List<ImageContainer> img_container_list = (List<ImageContainer>)selection_field.get(activityTestRule.getActivity());

        ImageContainer image_container = img_container_list.get(0);

        RecyclerView res = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        AdapterImages adapter = (AdapterImages)res.getAdapter();

        assertTrue(image_container.getPath().equals(adapter.getListImages().get(0).getPath()));

    }

    @Test
    public void checkAddedToSelection() throws Throwable {
        checkLongPressSelection();

        RecyclerView rec_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);

        runOnUiThread(new MyRunnable(rec_view, 1) {
            public void run() {
                this.resycler_view.findViewHolderForAdapterPosition(adapter_position).itemView.performClick();
                try {
                    sleep(100);

                } catch (InterruptedException ex1) {
                    return;
                }
            }
        });

        Field selection_field = MainActivity.class.getField("selection_list");
        List<ImageContainer> img_container_list = (List<ImageContainer>)selection_field.get(activityTestRule.getActivity());

        ImageContainer image_container = img_container_list.get(1);

        RecyclerView res = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        AdapterImages adapter = (AdapterImages)res.getAdapter();

        assertTrue(image_container.getPath().equals(adapter.getListImages().get(1).getPath()));

    }

    @Test
    public void checkBackButton() throws Throwable {
        checkSelectedItemList();
        pressBack();
        assert(activityTestRule.getActivity().selection_list.size() == 0);
    }

    @Test
    public void testDeselection() throws Throwable {
        checkAddedToSelection();

        RecyclerView rec_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);

        Field selection_field = MainActivity.class.getField("selection_list");
        List<ImageContainer> img_container_list = (List<ImageContainer>)selection_field.get(activityTestRule.getActivity());

        ImageContainer image_container = img_container_list.get(1);

        runOnUiThread(new MyRunnable(rec_view, 1) {
            public void run() {
                this.resycler_view.findViewHolderForAdapterPosition(adapter_position).itemView.performClick();
                try {
                    sleep(100);

                } catch (InterruptedException ex1) {
                    return;
                }
            }
        });

        selection_field = MainActivity.class.getField("selection_list");
        img_container_list = (List<ImageContainer>)selection_field.get(activityTestRule.getActivity());

        assertFalse(img_container_list.contains(image_container));
    }

    @Test
    public void testDoubleLongClickSelection() throws Throwable {
        RecyclerView rec_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);

        runOnUiThread(new MyRunnable(rec_view, 1) {
            public void run() {
                this.resycler_view.findViewHolderForAdapterPosition(0).itemView.performLongClick();
                try {
                    sleep(100);
                    this.resycler_view.findViewHolderForAdapterPosition(0).itemView.performLongClick();
                    sleep(100);
                } catch (InterruptedException ex1) {
                    fail();
                    return;
                }
            }
        });

        Field selection_field = MainActivity.class.getField("selection_list");
        List<ImageContainer> img_container_list = (List<ImageContainer>)selection_field.get(activityTestRule.getActivity());

        assertTrue(img_container_list.size() == 1);
    }

    @Test
    public void checkSelectionModeDisabled() throws Throwable{
        RecyclerView rec_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        runOnUiThread(new MyRunnable(rec_view, 1) {
            public void run() {
                this.resycler_view.findViewHolderForAdapterPosition(adapter_position).itemView.performLongClick();
                try {
                    sleep(100);
                    this.resycler_view.findViewHolderForAdapterPosition(adapter_position).itemView.performClick();

                } catch (InterruptedException ex1) {
                    fail();
                    return;
                }
            }
        });
        assertFalse(activityTestRule.getActivity().selection_mode);
    }

    @Test
    public void checkIfRecyclerHasRelativeLayout() {
        RecyclerView rec_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        RelativeLayout layout = (RelativeLayout) rec_view.findViewHolderForAdapterPosition(0).itemView;
        assertTrue(layout.getResources().getResourceEntryName(layout.getId()).equals("ImageLayout"));
    }


    @Test
    public void checkIfRelativeLayoutHasShape() {
        RecyclerView rec_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        RelativeLayout layout = (RelativeLayout) rec_view.findViewHolderForAdapterPosition(0).itemView;
        View view = layout.getChildAt(1);
        assertTrue(layout.getResources().getResourceEntryName(view.getId()).equals("SelectedIcon"));
    }

    @Test
    public void checkIfSelectedIconVisible() throws Throwable {
        checkLongPressSelection();
        RecyclerView rec_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        RelativeLayout layout = (RelativeLayout) rec_view.findViewHolderForAdapterPosition(0).itemView;
        ImageView icon = layout.findViewById(R.id.SelectedIcon);
        assertTrue(icon.getVisibility() == View.VISIBLE);
    }
}
