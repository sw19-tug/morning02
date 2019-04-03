package com.gallery.android.gallery;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;

import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;

public class SelectorTest {



    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkSelectedProperty() throws NoSuchFieldException {


        MainActivity.class.getField("selection_mode");


    }





}
