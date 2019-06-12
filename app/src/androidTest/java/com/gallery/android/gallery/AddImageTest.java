package com.gallery.android.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.util.Log;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.io.File;
import java.util.Collection;

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
import static android.support.test.runner.lifecycle.Stage.CREATED;
import static android.support.test.runner.lifecycle.Stage.PAUSED;
import static android.support.test.runner.lifecycle.Stage.PRE_ON_CREATE;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static android.support.test.runner.lifecycle.Stage.STARTED;
import static android.support.test.runner.lifecycle.Stage.STOPPED;
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
            .around(activityTestRule)
            //.around(addImageRule = new ActivityTestRule<AddImageActivity>(AddImageActivity.class))
                    .around(activityTestRule);






    @Test
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
        Intent intent=new Intent();
        activityTestRule.launchActivity(intent);
        /*Intent intent=new Intent();
        activityTestRule.launchActivity(intent);*/
        assertNotNull(activityTestRule.getActivity().findViewById(R.id.addImage));




    }


    Activity currentActivity = null;

    public Activity getActivityInstance(){
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection<Activity> resumedActivities =
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                Log.e("holahola",String.valueOf(resumedActivities.size()));
                boolean res=false;
                for(Activity a:resumedActivities){
                    System.out.println(a.getTitle());
                    currentActivity=a;
                    Log.e("holahola",String.valueOf(a.getTitle()));
                }
            }
        });

        return currentActivity;
    }
}
