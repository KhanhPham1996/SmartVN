package com.example.smartvn.activity;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.smartvn.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule activityRule = new ActivityScenarioRule(MainActivity.class);

    @Test
    public void test_LeftmenuOpenCorrectActivity() {
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open());
        onView(withText(R.string.smart_phone)).perform(click());
        onView(withId(R.id.activity_list_product)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()));

    }
}