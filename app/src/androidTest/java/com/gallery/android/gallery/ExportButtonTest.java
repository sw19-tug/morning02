
package com.gallery.android.gallery;


        import android.Manifest;

        import android.os.Environment;
        import android.support.test.rule.ActivityTestRule;
        import android.support.test.rule.GrantPermissionRule;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;


        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.rules.RuleChain;
        import org.junit.rules.TestRule;

        import java.io.File;
        import java.util.logging.Logger;

        import static android.support.test.InstrumentationRegistry.getInstrumentation;
        import static android.support.test.espresso.Espresso.onView;
        import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
        import static android.support.test.espresso.action.ViewActions.click;
        import static android.support.test.espresso.assertion.ViewAssertions.matches;
        import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
        import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
        import static android.support.test.espresso.matcher.ViewMatchers.withId;
        import static android.support.test.espresso.matcher.ViewMatchers.withText;
        import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
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

            runOnUiThread(new MyRunnable(recycler_view, i) {
                public void run() {

                    this.resycler_view.findViewHolderForAdapterPosition(adapter_position).itemView.performClick();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex1) {
                        return;
                    }
                }
            });
            System.out.println("this yes");

            onView(withId(R.id.fullscreen_image_view)).check(matches(isDisplayed()));
        }
    }



    @Test
    public void exportAllImages() throws Throwable, InterruptedException {
        Log.e("this","export");



        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Sort by date descending")).perform(click());


        RecyclerView recycler_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        final AdapterImages adapter_images = (AdapterImages) recycler_view.getAdapter();
        Integer n=adapter_images.getItemCount();

        for (int i = 0; i <3; i++ ) {

            Log.e("export mira i=",Integer.toString(i));
            runOnUiThread(new MyRunnable(recycler_view, i) {
                public void run() {
                    this.resycler_view.findViewHolderForAdapterPosition(adapter_position).itemView.performClick();
                    try {
                        Thread.sleep(100);
                    } catch (Throwable ex1) {
                        return;
                    }
                }
            });


            checkExportImage(i, adapter_images.getListImages().get(i).getSize());
        }

    }


    public void checkExportImage(int number, long size) throws Throwable {

        onView(withId(R.id.popupMenu)).perform(click());


        onView(withText("Export")).perform(click());

        String export_phase = "_export.zip";


        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() +"/"+ files[number] + export_phase);

        assertTrue((f.exists() && f.isFile()));
        assertTrue(((int)f.length()) <= size);


    }



}