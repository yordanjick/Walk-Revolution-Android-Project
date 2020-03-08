package com.example.cse110_project;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HeightPromptExistTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void heightPromptExistTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.height_prompt), withText("Enter Height in Inches"),
                        childAtPosition(
                                allOf(withId(R.id.height_layout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Enter Height in Inches")));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.user_height),
                        childAtPosition(
                                allOf(withId(R.id.height_layout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("60"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.confirm_height), withText("Confirm"),
                        childAtPosition(
                                allOf(withId(R.id.height_layout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        new UserData(getApplicationContext()).clearUserData();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
