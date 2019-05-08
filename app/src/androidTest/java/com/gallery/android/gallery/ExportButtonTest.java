
package com.gallery.android.gallery;


        import android.Manifest;

        import android.support.test.rule.ActivityTestRule;
        import android.support.test.rule.GrantPermissionRule;
        import android.support.v7.widget.RecyclerView;


        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.rules.RuleChain;
        import org.junit.rules.TestRule;

        import static android.support.test.espresso.Espresso.onView;
        import static android.support.test.espresso.assertion.ViewAssertions.matches;
        import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
        import static android.support.test.espresso.matcher.ViewMatchers.withId;
        import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;


public class ExportButtonTest {


    private ActivityTestRule<MainActivity> activityTestRule;

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("test1.png");
                    TestHelper.createFile("test2.png");
                }
            });

    @Test
    public void testClick() throws Throwable, InterruptedException {
        RecyclerView recycler_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        AdapterImages adapter_images = (AdapterImages) recycler_view.getAdapter();
        for (int i = 0; i < adapter_images.getItemCount(); i++ ) {
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
            onView(withId(R.id.fullscreen_image_view)).check(matches(isDisplayed()));
        }
    }

}