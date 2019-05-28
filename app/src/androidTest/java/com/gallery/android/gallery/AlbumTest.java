package com.gallery.android.gallery;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.v7.widget.RecyclerView;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class AlbumTest {
    private ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private ActivityTestRule<AlbumOverviewActivity> album_overview_rule;
    private ActivityTestRule<MainActivity> album_images_activity;

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(album_overview_rule = new ActivityTestRule<AlbumOverviewActivity>(AlbumOverviewActivity.class) {
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("test.png");
                }
            })
            /*.around(album_images_activity = new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, MainActivity.class);
                    result.putExtra("path", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
                    result.putExtra("include_subfolders", false);
                    return result;
                }
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("test.png");
                }
            })*/
            .around(activityTestRule);



    @AfterClass
    public static void tearDownClass() {
        TestHelper.deleteFile("test.png");
    }
    @Test
    public void buttonExists(){
        /*Intent intent=new Intent();
        activityTestRule.launchActivity(intent);*/
        assertNotNull(activityTestRule.getActivity().findViewById(R.id.albums));
        // onView(withId(R.id.albums)).check(matches(isDisplayed()));
    }
    @Test
    public void albumExists(){
        // onView(withId(R.id.albums)).perform(click());

        RecyclerView albums = album_overview_rule.getActivity().findViewById(R.id.albumRecyclerId);
        boolean path_exists = false;

        for (int childCount = albums.getChildCount(), i = 0; i < childCount; ++i) {
            AdapterAlbums.ViewHolderAlbums holder = (AdapterAlbums.ViewHolderAlbums) albums.getChildViewHolder(albums.getChildAt(i));
            if (holder.path.getText().equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString())) {
                path_exists = true;
                break;
            }
        }

        assertTrue(path_exists);

    }

  /*  @Test
    public void PictureExistsInAlbum(){

        RecyclerView image_recycler = album_images_activity.getActivity().findViewById(R.id.RecyclerId);
        boolean image_exists = false;

        for (int childCount = image_recycler.getChildCount(), i = 0; i < childCount; ++i) {
            AdapterAlbums.ViewHolderAlbums holder = (AdapterAlbums.ViewHolderAlbums) image_recycler.getChildViewHolder(image_recycler.getChildAt(i));
            if (holder.path.getText().equals("test.png")) {
                image_exists = true;
                break;
            }
        }

        assertTrue(image_exists);

    }*/
}
