package com.gallery.android.gallery;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
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

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(album_overview_rule = new ActivityTestRule<AlbumOverviewActivity>(AlbumOverviewActivity.class) {
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("test1.png");
                    TestHelper.createFile("test2.png");
                }

            });


    @AfterClass
    public static void tearDownClass() {
        TestHelper.deleteFile("test1.png");
        TestHelper.deleteFile("test2.png");
    }
    @Test
    public void buttonExists(){
        Intent intent=new Intent();
        activityTestRule.launchActivity(intent);
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
}
