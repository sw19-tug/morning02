package com.gallery.android.gallery;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.io.File;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AddImageTest {
    private ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private ActivityTestRule<AddImageActivity> addImageRule=new ActivityTestRule<AddImageActivity>(AddImageActivity.class);

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule);
    /*
    The test below is not executed because when the addImageRule starts the camera is started and we
    found impossible to close the camera for the next tests so the application got stuck.
     */
    public void cameraStarted() {
        Intent intent2=new Intent();
        activityTestRule.launchActivity(intent2);
        Intent intent=new Intent();
        addImageRule.launchActivity(intent);
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        String r=addImageRule.getActivity().savebitmap(bitmap);
        addImageRule.finishActivity();
        String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File file = new File(extStorageDirectory, "QR_" + r + ".png");
        assertTrue(file.exists());

    }

    @Test
    public void imagebuttonExists() {
        assertNotNull(onView(withId(R.id.addImage)).check(matches(isDisplayed())));
    }
}
