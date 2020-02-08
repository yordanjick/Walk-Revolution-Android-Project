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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddRouteTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addRouteTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.user_height),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("55"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.confirm_height), withText("Confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.add_routes_button), withText("Add New Route"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.name_field),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText2.perform(scrollTo(), replaceText("Home_to_work"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.start_field),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatEditText3.perform(scrollTo(), replaceText("Home"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.step_field),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatEditText4.perform(scrollTo(), replaceText("5980"), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.loop_button), withText("Loop"),
                        childAtPosition(
                                allOf(withId(R.id.run_type),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                0)));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.flat_button), withText("Flat"),
                        childAtPosition(
                                allOf(withId(R.id.flat_hilly),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                4)),
                                0)));
        appCompatRadioButton2.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton3 = onView(
                allOf(withId(R.id.streets_button), withText("Streets"),
                        childAtPosition(
                                allOf(withId(R.id.route_type),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                0)));
        appCompatRadioButton3.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton4 = onView(
                allOf(withId(R.id.even_button), withText("Even"),
                        childAtPosition(
                                allOf(withId(R.id.surface_type),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                6)),
                                0)));
        appCompatRadioButton4.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton5 = onView(
                allOf(withId(R.id.easy_button), withText("Easy"),
                        childAtPosition(
                                allOf(withId(R.id.difficulty),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                7)),
                                0)));
        appCompatRadioButton5.perform(scrollTo(), click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.save_buttton), withText("Save Route"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                9)));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction button = onView(
                allOf(withText("Home_to_work"),
                        childAtPosition(
                                allOf(withId(R.id.routes_list_layout),
                                        childAtPosition(
                                                withClassName(is("androidx.core.widget.NestedScrollView")),
                                                0)),
                                0),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.name_text), withText("Home_to_work"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Home_to_work")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.start_text), withText("Home"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Home")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.run_text), withText("loop"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        3),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("loop")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.surface_text), withText("flat"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        4),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("flat")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.road_text), withText("streets"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        5),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("streets")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.condition_text), withText("even surface"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        6),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("even surface")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.difficulty_text), withText("easy"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        7),
                                1),
                        isDisplayed()));
        textView7.check(matches(withText("easy")));
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
