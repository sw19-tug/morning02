package com.gallery.android.gallery;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.CREATED;
import static android.support.test.runner.lifecycle.Stage.PAUSED;
import static android.support.test.runner.lifecycle.Stage.PRE_ON_CREATE;
import static android.support.test.runner.lifecycle.Stage.STARTED;
import static android.support.test.runner.lifecycle.Stage.STOPPED;

//Code was parially taken from https://code.tutsplus.com/tutorials/capture-and-crop-an-image-with-the-device-camera--mobile-11458

public class PictureCropTest {

    public IntentsTestRule<MainActivity> activityTestRule = new IntentsTestRule<>(MainActivity.class);
    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(new IntentsTestRule<MainActivity>(MainActivity.class) {

            });

    @Test
    public void testButtonsVisible() {
        onView(withId(R.id.idImage)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Crop")).check(matches(isDisplayed()));
    }

    Activity currentActivity = null;

    public void  getActivityInstance(){
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection <Activity> resumedActivities =
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(STOPPED);
                resumedActivities.addAll(
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(PAUSED));
                resumedActivities.addAll(
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(CREATED));
                resumedActivities.addAll(
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(STARTED));
                resumedActivities.addAll(
                        ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(PRE_ON_CREATE));
                for (Activity a:resumedActivities){
                    Log.e("sofi ",a.getClass().toString());
                }
            }
        });
    }
    public void getActivityInstance2(){
        Context ctx=activityTestRule.getActivity().getApplicationContext();

        ActivityManager m = (ActivityManager) ctx
                .getSystemService(ctx.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList = m.getRunningTasks(10);
        Iterator<ActivityManager.RunningTaskInfo> itr = runningTaskInfoList.iterator();
        while (itr.hasNext()) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) itr.next();
            int id = runningTaskInfo.id;
            CharSequence desc = runningTaskInfo.description;
            int numOfActivities = runningTaskInfo.numActivities;
            String topActivity = runningTaskInfo.topActivity
                    .getShortClassName();
            Log.e("foi ",topActivity );
        }
    }
}
