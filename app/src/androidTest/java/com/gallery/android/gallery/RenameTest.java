package com.gallery.android.gallery;

import android.Manifest;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.EditText;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class RenameTest {
    private ActivityTestRule<MainActivity> activityTestRule;

    @Rule
    public final TestRule chain = RuleChain
            .outerRule(GrantPermissionRule.grant(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(activityTestRule = new IntentsTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    TestHelper.createFile("test.png");
                }
            });

    @AfterClass
    public static void tearDownClass() {
        TestHelper.deleteFile("test.png");
    }

    @Test
    public void testRenameButtonIsShown(){
        RecyclerView rec_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        if (rec_view.getAdapter().getItemCount() == 0)
            return;
        
        onView(withId(R.id.idImage)).perform(click());
        onView(withId(R.id.popupMenu)).perform(click());
        onView(withText("Rename")).check(matches((isDisplayed())));
    }

    @Test
    public void testRenameButtonShowTextInput()throws Throwable, InterruptedException {
        RecyclerView rec_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);
        if (rec_view.getAdapter().getItemCount() == 0)
            return;

        onView(withId(R.id.idImage)).perform(click());
        onView(withId(R.id.popupMenu)).perform(click());
        onView(withText("Rename")).check(matches((isDisplayed())));
        onView(withText("Rename")).perform(click());
        Thread.sleep(200);
        onView(withText("New Name: ")).check(matches(isDisplayed()));
    }

    @Test
    public void testRenameInternal()throws Throwable, InterruptedException {
        RecyclerView rec_view = activityTestRule.getActivity().findViewById(R.id.RecyclerId);

        if (rec_view.getAdapter().getItemCount() == 0)
            return;

        String oldPath = ((AdapterImages)(rec_view.getAdapter())).getListImages().get(0).getPath();
        onView(withId(R.id.idImage)).perform(click());
        onView(withId(R.id.popupMenu)).perform(click());
        onView(withText("Rename")).check(matches((isDisplayed())));
        onView(withText("Rename")).perform(click());
        Thread.sleep(200);
        onView(isAssignableFrom(EditText.class)).perform(typeText("NewNameOfImage"), pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withText("yes")).perform(click());
        Thread.sleep(200);
        AdapterImages adapterImages = ((AdapterImages)rec_view.getAdapter());

        if(!adapterImages.getListImages().isEmpty())
        {
            String newName = adapterImages.getListImages().get(0).getFilename();
            String newPath = adapterImages.getListImages().get(0).getPath();
            assertTrue(newName.equals("NewNameOfImage"));
            assertTrue(!newPath.equals(oldPath));
            String compareName =  newPath.substring(newPath.lastIndexOf("/") + 1);
            compareName = compareName.substring(0, compareName.lastIndexOf("."));
            assertTrue(newName.equals(compareName));

        }
    }


}
