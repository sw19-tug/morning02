package com.gallery.android.gallery;

import android.Manifest;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;

public class ExportButtonTest {

    String files[] = {"test1.png", "test2.png", "test3.png"};

    private ActivityTestRule<MainActivity> activityTestRule;

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {

                    for (int i = 0; i < files.length; i++)
                    {
                        TestHelper.createFile(files[i]);
                    }
                }
            });

    @Test
    public void testClick() throws Throwable, InterruptedException {
        RecyclerView recycler_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        AdapterImages adapter_images = (AdapterImages) recycler_view.getAdapter();

        for (int i = 0; i < adapter_images.getListImages().size() && i<15; i++ ) {

            onView(withId(R.id.RecyclerId)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(i, TestHelper.clickChildViewWithId(R.id.ImageLayout)));
            pressBack();
        }
    }

    @Test
    public void exportAllImages() throws Throwable, InterruptedException {

        RecyclerView recycler_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        final AdapterImages adapter_images = (AdapterImages) recycler_view.getAdapter();
        Integer n=adapter_images.getItemCount();

        for (int i = 0; i <3; i++ ) {
            String path = ((AdapterImages)(recycler_view.getAdapter())).getListImages().get(i).getPath();
            onView(withId(R.id.RecyclerId)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(i, TestHelper.clickChildViewWithId(R.id.ImageLayout)));
            Thread.sleep(100);
            checkExportImage(i, adapter_images.getListImages().get(i).getSize(),path);
            pressBack();
        }

    }

    public void checkExportImage(int number, long size, String path) throws Throwable {

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Export")).perform(click());
        Thread.sleep(200);
        String export_phase = "_export.zip";
        File f = new File(path + export_phase);
        assertTrue((f.exists() && f.isFile()));
        assertTrue(((int)f.length()) <= size);
    }
}