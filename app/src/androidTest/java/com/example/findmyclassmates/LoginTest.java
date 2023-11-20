package com.example.findmyclassmates;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.slowSwipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsRule;

import android.content.Context;
import android.view.View;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import com.example.findmyclassmates.activities.LoginActivity;
import com.example.findmyclassmates.activities.MainActivity;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);
    @Test
    public void login_success() throws InterruptedException {
        // Type Login Info, then press button
        onView(withId(R.id.emailEditText))
                .perform(typeText("lb@usc.edu"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText("123123"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        Thread.sleep(1000);
        onView(ViewMatchers.isRoot()).perform(swipeLeft());
        Thread.sleep(1000);
        onView(ViewMatchers.isRoot()).perform(swipeLeft());
        Thread.sleep(1000);
        // Check that the user logged into Lola Bunny
        onView(withId(R.id.textViewFirstName)).check(matches(withText("Lola")));
    }

    @Test
    public void login_fail_password() throws InterruptedException {
        onView(withId(R.id.emailEditText))
                .perform(typeText("lb@usc.edu"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText("12312"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        Thread.sleep(1000);
        onView(withId(R.id.tv_result)).check(matches(withText("false")));
    }
    @Test
    public void login_fail_email() throws InterruptedException {
        onView(withId(R.id.emailEditText))
                .perform(typeText("lb@usc.ed"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText("123123"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        Thread.sleep(1000);
        onView(withId(R.id.tv_result)).check(matches(withText("false")));
    }

}