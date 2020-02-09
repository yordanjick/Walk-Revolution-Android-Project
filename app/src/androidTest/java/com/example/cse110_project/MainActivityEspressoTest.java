package com.example.cse110_project;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.cse110_project.fitness.FitnessService;
import com.example.cse110_project.fitness.FitnessServiceFactory;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";
    private static final String TEST_HEIGHT = "60";

    @Rule
    public ActivityTestRule<LoginActivity> lActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void mainActivityEspressoTest() {
        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(MainActivity mainActivity) {
                return new TestFitnessService(mainActivity);
            }
        });

        lActivityTestRule.getActivity().setFitnessServiceKey(TEST_SERVICE);

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.start_button), withText("Start!"),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction prompt = onView(
                allOf(withId(R.id.height_prompt),
                        isDisplayed()));
        prompt.check(matches(withText("Enter Height in Inches")));

        ViewInteraction editText = onView(
                allOf(withId(R.id.user_height),
                        isDisplayed()));
        editText.perform(replaceText(TEST_HEIGHT));

        ViewInteraction confirmHeightButton = onView(
                allOf(withId(R.id.confirm_height), withText("Confirm"),
                        isDisplayed()));
        confirmHeightButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.steps_walked_text),
                        /*
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                         */
                        isDisplayed()));
        textView.check(matches(withText("Steps Walked Today:")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.steps_distance_travelled),
                        isDisplayed()));
        textView2.check(matches(withText("Distance Walked Today:")));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.routes_button), withText("Routes"),
                        isDisplayed()));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.add_routes_button), withText("Add New Route"),
                        isDisplayed()));

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.update_button), withText("Update Steps/Distance"),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.steps_walked),
                        isDisplayed()));
        textView3.check(matches(withText("1337")));


        ViewInteraction textView4 = onView(
                allOf(withId(R.id.dist_walked),
                        isDisplayed()));
        textView4.check(matches(withText(containsString("0.52"))));

    }
/*
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
 */

    private class TestFitnessService implements FitnessService {
        private static final String TAG = "[TestFitnessService]: ";
        private MainActivity mainActivity;

        public TestFitnessService(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public int getRequestCode() {
            return 0;
        }

        @Override
        public void setup() {
            System.out.println(TAG + "setup");
        }

        @Override
        public void updateStepCount() {
            System.out.println(TAG + "updateStepCount");
            mainActivity.setStepCount(1337);
        }
    }
}
