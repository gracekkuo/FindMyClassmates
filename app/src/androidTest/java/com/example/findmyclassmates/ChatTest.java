package com.example.findmyclassmates;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
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
public class ChatTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void chat_success() throws InterruptedException {
        // Type Login Info, then press button
        onView(withId(R.id.emailEditText))
                .perform(typeText("lb@usc.edu"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordEditText))
                .perform(typeText("123123"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        // Swipe to chat page
        Thread.sleep(1000);
        onView(ViewMatchers.isRoot()).perform(swipeLeft());
        Thread.sleep(1000);

        // find chatter
        onView(withText("Annie")).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.edit_message_text)).perform(typeText("chat tab test message"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.send_messsage_btn)).perform(click());
        Thread.sleep(2000);

        pressBack();
        Thread.sleep(1000);
    }

}