package com.example.findmyclassmates;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.findmyclassmates.activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);
    @Test
    public void signup_fail_email() throws InterruptedException {

        onView(withId(R.id.signinButton)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.firstNameEditText)).perform(ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.uscEmailEditText)).perform(typeText("invalidemail"),ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.signupButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.signup_tv_result)).check(matches(withText("false")));
    }

    @Test
    public void signup_fail_id() throws InterruptedException {

        onView(withId(R.id.signinButton)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.firstNameEditText)).perform(ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.uscEmailEditText)).perform(typeText("test@usc.edu"),ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.studentIdEditText)).perform(typeText("123"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.signupButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.signup_tv_result)).check(matches(withText("false")));
    }

    @Test
    public void signup_fail_password() throws InterruptedException {

        onView(withId(R.id.signinButton)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.firstNameEditText)).perform(ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.uscEmailEditText)).perform(typeText("test@usc.edu"),ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.studentIdEditText)).perform(typeText("1234512345"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.passwordEditText)).perform(typeText("matchingpassword"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.reenterPasswordEditText)).perform(typeText("notmatchingpassword"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.signupButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.signup_tv_result)).check(matches(withText("false")));
    }

    @Test
    public void signup_fail_usc_email() throws InterruptedException {

        onView(withId(R.id.signinButton)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.firstNameEditText)).perform(ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.uscEmailEditText)).perform(typeText("test@gmail.edu"),ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.studentIdEditText)).perform(typeText("1234512345"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.passwordEditText)).perform(typeText("matchingpassword"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.reenterPasswordEditText)).perform(typeText("matchingpassword"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.signupButton)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.signup_tv_result)).check(matches(withText("false")));
    }

}