
package com.gallery.android.gallery;


        import android.Manifest;

        import android.support.test.rule.ActivityTestRule;
        import android.support.test.rule.GrantPermissionRule;


        import org.junit.Rule;
        import org.junit.rules.RuleChain;
        import org.junit.rules.TestRule;


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

}