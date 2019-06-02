package com.gallery.android.gallery;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasImeAction;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AddImageTest {
    private ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private ActivityTestRule<AddImageActivity> addImageRule;


    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE))
            //.around(activityTestRule);
            .around(addImageRule = new ActivityTestRule<AddImageActivity>(AddImageActivity.class) {
        protected void beforeActivityLaunched() {

        }
    })
            .around(activityTestRule);






    @Test
    public void cameraStarted() {
        Intent intent2=new Intent();
        activityTestRule.launchActivity(intent2);
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        String r=addImageRule.getActivity().savebitmap(bitmap);
        String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File file = new File(extStorageDirectory, "QR_" + r + ".png");
        assertTrue(file.exists());

    }

    @Test
    public void imagebuttonExists() {
        Intent intent=new Intent();
        activityTestRule.launchActivity(intent);
        /*Intent intent=new Intent();
        activityTestRule.launchActivity(intent);*/
        assertNotNull(activityTestRule.getActivity().findViewById(R.id.addImage));
        activityTestRule.finishActivity();



    }
}
