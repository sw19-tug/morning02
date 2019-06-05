package com.gallery.android.gallery;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class BulkEditJTest {
    private ActivityTestRule<MainActivity> activityTestRule;

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule = new IntentsTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("testClick.png");
                }
            });

    @Test
    public void testRotateOptionInvisible() throws InterruptedException {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Thread.sleep(100);
        onView(withText("Rotate All")).check(doesNotExist());
    }

    @Test
    public void testDeleteOptionInvisible() throws InterruptedException {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Thread.sleep(100);
        onView(withText("Delete All")).check(doesNotExist());
    }

    @Test
    public void testShareOptionInvisible() throws InterruptedException {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Thread.sleep(100);
        onView(withText("Share All")).check(doesNotExist());
    }

    @Test
    public void testRotateOptionVisible() throws InterruptedException {

        RecyclerView resycler_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        if (resycler_view.getAdapter().getItemCount() < 2)
            return;

        onView(withId(R.id.RecyclerId)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, TestHelper.longClickChildViewWithId(R.id.ImageLayout)));
        onView(withId(R.id.RecyclerId)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, TestHelper.clickChildViewWithId(R.id.ImageLayout)));

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Thread.sleep(100);
        onView(withText("Rotate All")).check(matches(isDisplayed()));
    }

    @Test
    public void testDeleteOptionVisible() throws InterruptedException {

        RecyclerView resycler_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        if (resycler_view.getAdapter().getItemCount() < 2)
            return;

        onView(withId(R.id.RecyclerId)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, TestHelper.longClickChildViewWithId(R.id.ImageLayout)));
        onView(withId(R.id.RecyclerId)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, TestHelper.clickChildViewWithId(R.id.ImageLayout)));

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Thread.sleep(100);
        onView(withText("Delete All")).check(matches(isDisplayed()));
    }

    @Test
    public void testShareOptionVisible() throws InterruptedException {

        RecyclerView resycler_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        if (resycler_view.getAdapter().getItemCount() < 2)
            return;

        onView(withId(R.id.RecyclerId)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, TestHelper.longClickChildViewWithId(R.id.ImageLayout)));
        onView(withId(R.id.RecyclerId)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, TestHelper.clickChildViewWithId(R.id.ImageLayout)));

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Thread.sleep(100);
        onView(withText("Share All")).check(matches(isDisplayed()));
    }

    @Test
    public void testRotateFunctionality() throws InterruptedException {

        RecyclerView resycler_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        if (resycler_view.getAdapter().getItemCount() < 2)
            return;

        Bitmap oldBitmap1 = ((AdapterImages)(resycler_view.getAdapter())).getListImages().get(0).getImage();
        Matrix matrix1 = new Matrix();
        matrix1.postRotate(90);
        Bitmap rotated1 = Bitmap.createBitmap(oldBitmap1, 0, 0, oldBitmap1.getWidth(),
                oldBitmap1.getHeight(), matrix1, true);

        Bitmap oldBitmap2 = ((AdapterImages)(resycler_view.getAdapter())).getListImages().get(1).getImage();
        Matrix matrix2 = new Matrix();
        matrix2.postRotate(90);
        Bitmap rotated2 = Bitmap.createBitmap(oldBitmap2, 0, 0, oldBitmap2.getWidth(),
                oldBitmap2.getHeight(), matrix2, true);

        onView(withId(R.id.RecyclerId)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, TestHelper.longClickChildViewWithId(R.id.ImageLayout)));
        onView(withId(R.id.RecyclerId)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, TestHelper.clickChildViewWithId(R.id.ImageLayout)));

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Thread.sleep(100);
        onView(withText("Rotate All")).perform(click());

        Bitmap newBitmap1 = ((AdapterImages)(resycler_view.getAdapter())).getListImages().get(0).getImage();
        Bitmap newBitmap2 = ((AdapterImages)(resycler_view.getAdapter())).getListImages().get(1).getImage();

        ByteBuffer oldBitmap1Buffer = ByteBuffer.allocate(rotated1.getHeight() * rotated1.getRowBytes());
        rotated1.copyPixelsToBuffer(oldBitmap1Buffer);

        ByteBuffer oldBitmap2Buffer = ByteBuffer.allocate(rotated2.getHeight() * rotated2.getRowBytes());
        rotated2.copyPixelsToBuffer(oldBitmap2Buffer);

        ByteBuffer newBitmap1Buffer = ByteBuffer.allocate(newBitmap1.getHeight() * newBitmap1.getRowBytes());
        newBitmap1.copyPixelsToBuffer(newBitmap1Buffer);

        ByteBuffer newBitmap2Buffer = ByteBuffer.allocate(newBitmap2.getHeight() * newBitmap2.getRowBytes());
        newBitmap2.copyPixelsToBuffer(newBitmap2Buffer);

        assertTrue(Arrays.equals(oldBitmap1Buffer.array(), newBitmap1Buffer.array()));
        assertTrue(Arrays.equals(oldBitmap2Buffer.array(), newBitmap2Buffer.array()));
    }

    @Test
    public void testDeleteFunctionality() throws InterruptedException {

        RecyclerView resycler_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        if (resycler_view.getAdapter().getItemCount() < 2)
            return;

        String path1 = ((AdapterImages)(resycler_view.getAdapter())).getListImages().get(0).getPath();
        String path2 = ((AdapterImages)(resycler_view.getAdapter())).getListImages().get(1).getPath();

        onView(withId(R.id.RecyclerId)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, TestHelper.longClickChildViewWithId(R.id.ImageLayout)));
        onView(withId(R.id.RecyclerId)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, TestHelper.clickChildViewWithId(R.id.ImageLayout)));

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Thread.sleep(100);
        onView(withText("Delete All")).perform(click());

        File file1 = new File(path1);
        assertTrue(file1.exists());
        File file2 = new File(path1);
        assertFalse(file2.exists());
    }

}
