package com.gallery.android.gallery;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static com.gallery.android.gallery.AlbumOverviewActivity.recyclerAlbums;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class AlbumTest {
    private ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private ActivityTestRule<AlbumOverviewActivity> album_overview_rule;
    private ActivityTestRule<PictureSelectionActivity> pic_selection_rule;
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
            .around(pic_selection_rule = new ActivityTestRule<>(PictureSelectionActivity.class, true, false))
            .around(activityTestRule);

    @AfterClass
    public static void tearDownClass() {
        TestHelper.deleteFile("test.png");
    }
    @Test
    public void buttonExists(){
        assertNotNull(activityTestRule.getActivity().findViewById(R.id.albums));
    }
    @Test
    public void albumExists(){

        RecyclerView albums = album_overview_rule.getActivity().findViewById(R.id.albumRecyclerId);
        boolean path_exists = false;
        final ArrayList<Pair<String, Bitmap>> ListAlbums = ((AdapterAlbums)albums.getAdapter()).getListAlbums();

        for(Pair<String, Bitmap> album: ListAlbums){
            if (album.first.equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString())) {
                path_exists = true;
                break;
            }
        }

        assertTrue(path_exists);
    }

    @Test
    public void clickOnAlbumButton() {

        RecyclerView albums = album_overview_rule.getActivity().findViewById(R.id.albumRecyclerId);
        AdapterAlbums adapter = (AdapterAlbums) albums.getAdapter();

        for (int i = 0; i < adapter.getItemCount(); i++) {
            try {
                runOnUiThread(new MyRunnable(albums, i) {
                    public void run() {
                        try {
                            recyclerAlbums.findViewHolderForAdapterPosition(adapter_position).itemView.performClick();
                        } catch (NullPointerException ex1) {
                            ex1.printStackTrace();
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex1) {
                            return;
                        }
                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    @Test
    public void addAlbumButton()
    {
        assertNotNull(album_overview_rule.getActivity().findViewById(R.id.add_album));
    }

    @Test
    public void pictureSelectionTest() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra("path", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
        pic_selection_rule.launchActivity(intent);

        RecyclerView picture_recycler = pic_selection_rule.getActivity().findViewById(R.id.RecyclerId);
        AdapterImages adapter = (AdapterImages) picture_recycler.getAdapter();

        int item_count = adapter.getItemCount();

        for (int i = 0; i < item_count; i++) {
            try {
                runOnUiThread(new MyRunnable(picture_recycler, i) {
                    public void run() {
                        try {
                            recyclerAlbums.findViewHolderForAdapterPosition(adapter_position).itemView.performClick();
                        } catch (NullPointerException ex1) {
                            ex1.printStackTrace();
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex1) {
                            return;
                        }
                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        assert(item_count == pic_selection_rule.getActivity().selection_list.size());

    }
}
