package com.example.findmyclassmates;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.findmyclassmates.activities.LoginActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CoursesTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void register() throws InterruptedException {
        // Type Login Info, then press button
        onView(withId(R.id.emailEditText))
                .perform(typeText("lb@usc.edu"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText("123123"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        Thread.sleep(1000);

        //registers for DANC 120
        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(2, MyViewAction.clickChildViewWithId(R.id.expandButton))).perform(click());
        Thread.sleep(1000);
//        onView(allOf(withId(R.id.course1)))

        onView(withId(R.id.buttonRegister)).perform(click());
        Thread.sleep(1000);

    }

    @Test
    public void drop() throws InterruptedException {
        // Type Login Info, then press button
        onView(withId(R.id.emailEditText))
                .perform(typeText("lb@usc.edu"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText("123123"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        Thread.sleep(1000);


        // drops DANC 120
        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(2, MyViewAction.clickChildViewWithId(R.id.expandButton))).perform(click());
        Thread.sleep(1000);
//        onView(allOf(withId(R.id.course1)))

        onView(withId(R.id.buttonRegister)).perform(click());
        Thread.sleep(1000);

    }

    @Test
    public void roster() throws InterruptedException {
        // Type Login Info, then press button
        onView(withId(R.id.emailEditText))
                .perform(typeText("lb@usc.edu"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText("123123"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        Thread.sleep(1000);

        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.expandButton))).perform(click());
        Thread.sleep(1000);
//        onView(allOf(withId(R.id.course1)))

//        onView(withId(R.id.buttonRegister)).perform(click());
        onView(withId(R.id.buttonRoster)).perform(click());
        Thread.sleep(1000);

        onView(withText("Annie Zhang\t\tannie@usc.edu")).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.buttonChat)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.edit_message_text)).perform(typeText("test message"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.send_messsage_btn)).perform(click());
        Thread.sleep(1000);

    }

    @Test
    public void review() throws InterruptedException {
        // Type Login Info, then press button
        onView(withId(R.id.emailEditText))
                .perform(typeText("lb@usc.edu"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText("123123"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        Thread.sleep(1000);

        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.expandButton))).perform(click());
        Thread.sleep(1000);
//        onView(allOf(withId(R.id.course1)))

//        onView(withId(R.id.buttonRegister)).perform(click());
        onView(withId(R.id.buttonReviews)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.leaveReview)).perform(click());

        onView(withId(R.id.response1)).perform(typeText("moderate workload"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.response2)).perform(typeText("3"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.response3)).perform(typeText("sometimes, there are popup quizzes"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.response4)).perform(typeText("you have three late days"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.response5)).perform(typeText("there is a final project"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.submitReview)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.reviewsListView)).perform(swipeUp());
        Thread.sleep(3000);
    }

    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }
}