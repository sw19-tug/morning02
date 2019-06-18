package com.gallery.android.gallery;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

import java.nio.ByteBuffer;
import java.util.Arrays;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;


@RunWith(AndroidJUnit4.class)
public class RotateImageJTest {
    private ActivityTestRule<MainActivity> activityTestRule;

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule =new IntentsTestRule<MainActivity>(MainActivity.class) {
            });

    @Test
    public void testButtonVisible() throws InterruptedException {

        onView(withId(R.id.idImage)).perform(click());
        Thread.sleep(100);
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Thread.sleep(100);
        onView(withText("Rotate")).check(matches(isDisplayed()));
    }

    @Test
    public  void rotationTestInternal() throws Throwable,InterruptedException
    {
        RecyclerView rView=activityTestRule.getActivity().recyclerImages;

        if(rView.getAdapter().getItemCount() == 0)
            return;

        Bitmap myBitmap = ((AdapterImages)(rView.getAdapter())).getListImages().get(0).getImage();
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotated = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
                myBitmap.getHeight(), matrix, true);

        onView(withId(R.id.idImage)).perform(click());
        Thread.sleep(200);
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Thread.sleep(100);
        onView(withText("Rotate")).perform(click());
        Thread.sleep(200);

        Bitmap newBitmap = ((AdapterImages)(rView.getAdapter())).getListImages().get(0).getImage();
        
        ByteBuffer rotatedBuffer = ByteBuffer.allocate(rotated.getHeight() * rotated.getRowBytes());
        rotated.copyPixelsToBuffer(rotatedBuffer);

        ByteBuffer newBuffer = ByteBuffer.allocate(newBitmap.getHeight() * newBitmap.getRowBytes());
        newBitmap.copyPixelsToBuffer(newBuffer);

        assertTrue(Arrays.equals(rotatedBuffer.array(), newBuffer.array()));
    }

    @Test
    public  void rotationTestExternal() throws Throwable,InterruptedException
    {
        RecyclerView rView=activityTestRule.getActivity().recyclerImages;

        if(rView.getAdapter().getItemCount() == 0)
            return;
        String path = ((AdapterImages)(rView.getAdapter())).getListImages().get(0).getPath();
        Bitmap myBitmap = BitmapFactory.decodeFile(path);

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotated = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),
                myBitmap.getHeight(), matrix, true);

        onView(withId(R.id.idImage)).perform(click());
        Thread.sleep(200);
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        Thread.sleep(100);
        onView(withText("Rotate")).perform(click());
        Thread.sleep(200);

        Bitmap newBitmap = BitmapFactory.decodeFile(path);

        ByteBuffer rotatedBuffer = ByteBuffer.allocate(rotated.getHeight() * rotated.getRowBytes());
        rotated.copyPixelsToBuffer(rotatedBuffer);

        ByteBuffer newBuffer = ByteBuffer.allocate(newBitmap.getHeight() * newBitmap.getRowBytes());
        newBitmap.copyPixelsToBuffer(newBuffer);

        assertTrue(Arrays.equals(rotatedBuffer.array(), newBuffer.array()));
    }

}
