package com.example.findmyclassmates;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.findmyclassmates.activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ProfileTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void editProfile() throws InterruptedException {
        // Type Login Info, then press button
        onView(withId(R.id.emailEditText))
                .perform(typeText("lb@usc.edu"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText("123123"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        // Swipe to profile page
        Thread.sleep(1000);
        onView(ViewMatchers.isRoot()).perform(swipeLeft());
        Thread.sleep(1000);
        onView(ViewMatchers.isRoot()).perform(swipeLeft());
        Thread.sleep(1000);

        // change first name
        onView(withId(R.id.textViewFirstName)).perform(click());
        onView(withId(R.id.editTextFirstName)).perform(replaceText("newFirstName"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.editTextFirstName)).perform(replaceText("Lola"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);

        // change last name
        onView(withId(R.id.textViewLastName)).perform(click());
        onView(withId(R.id.editTextLastName)).perform(replaceText("newLastName"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.editTextLastName)).perform(replaceText("Bunny"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);

        // change student id
        onView(withId(R.id.textViewStudentID)).perform(click());
//        onView(withId(R.id.editTextStudentID)).perform(replaceText("5432112345"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextStudentID)).perform(replaceText("1234554321"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);

        // change status
        onView(withId(R.id.textViewStatus)).perform(click());
        onView(withId(R.id.editTextStatus)).perform(replaceText("Faculty"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.editTextStatus)).perform(replaceText("Undergraduate Student"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);

        // press save
        onView(withId(R.id.buttonSave)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.editTextFirstName)).check(matches(withText("newFirstName")));
//        onView(withId(R.id.editTextFirstName)).check(matches(withText("Lola")));

        // run other version to reset profile changes
        Thread.sleep(1000);
    }
}