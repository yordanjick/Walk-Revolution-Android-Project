package com.example.cse110_project;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;
import com.example.cse110_project.fitness.FitnessService;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
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
import static org.hamcrest.Matchers.startsWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateRouteEspressoTest {

    private static final String TEST_HEIGHT = "60";
    private static final String TEST_NAME = "test route";
    private static final String TEST_START = "test start";
    private static final String TEST_INFO = "test info";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {

        UserData userObserver = new UserData(getApplicationContext());
        userObserver.clearUserData();
        
        RouteEntryDatabase.DEBUG_DATABASE = true;
        RouteEntryDatabase database = RouteEntryDatabase.getDatabase(getApplicationContext());
        RouteEntryDAO dao = database.getRouteEntryDAO();
        dao.clearRoutes();
        mActivityTestRule.getActivity().setFitnessService(new TestFitnessService(this.mActivityTestRule.getActivity()));

    }


    @Test
    public void createRouteEspressoTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.user_height),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText(TEST_HEIGHT), closeSoftKeyboard());

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

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.stop_button), withText("Stop Session"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        1),
                                8),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.name_field),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText2.perform(scrollTo(), replaceText(TEST_NAME), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.start_field),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatEditText3.perform(scrollTo(), replaceText(TEST_START), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.out_and_back_button), withText("Out-and-Back"),
                        childAtPosition(
                                allOf(withId(R.id.run_type),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                2)),
                                1)));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.flat_button), withText("Flat"),
                        childAtPosition(
                                allOf(withId(R.id.flat_hilly),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                0)));
        appCompatRadioButton2.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton3 = onView(
                allOf(withId(R.id.trail_button), withText("Trail"),
                        childAtPosition(
                                allOf(withId(R.id.route_type),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                4)),
                                1)));
        appCompatRadioButton3.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton4 = onView(
                allOf(withId(R.id.even_button), withText("Even"),
                        childAtPosition(
                                allOf(withId(R.id.surface_type),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                0)));
        appCompatRadioButton4.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton5 = onView(
                allOf(withId(R.id.moderate_button), withText("Moderate"),
                        childAtPosition(
                                allOf(withId(R.id.difficulty),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                6)),
                                1)));
        appCompatRadioButton5.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.favorite), withText("Favorite"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatMultiAutoCompleteTextView = onView(
                allOf(withId(R.id.note_field),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatMultiAutoCompleteTextView.perform(scrollTo(), replaceText(TEST_INFO), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.save_buttton), withText("Save Route"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                9)));
        appCompatButton4.perform(scrollTo(), click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.routes_button), withText("Routes"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        1),
                                4),
                        isDisplayed()));
        appCompatButton5.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button2 = onView(
                allOf(withText(startsWith(TEST_NAME)),
                        childAtPosition(
                                allOf(withId(R.id.routes_list_layout),
                                        childAtPosition(
                                                withClassName(is("androidx.core.widget.NestedScrollView")),
                                                0)),
                                0),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.name_text), withText(TEST_NAME),
                        childAtPosition(
                                allOf(withId(R.id.route_layout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                1),
                        isDisplayed()));
        textView.check(matches(withText(TEST_NAME)));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.start_text), withText(TEST_START),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText(TEST_START)));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.run_text), withText("Out-and-Back"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        5),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Out-and-Back")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.surface_text), withText("Flat"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        6),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("Flat")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.road_text), withText("Trail"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        7),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("Trail")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.condition_text), withText("Even Surface"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        8),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("Even Surface")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.difficulty_text), withText("Moderate"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        9),
                                1),
                        isDisplayed()));
        textView7.check(matches(withText("Moderate")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.favorite_text), withText("Yes"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        10),
                                1),
                        isDisplayed()));
        textView8.check(matches(withText("Yes")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.note_text), withText(TEST_INFO),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                12),
                        isDisplayed()));
        textView9.check(matches(withText(TEST_INFO)));
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
        public long getStepCount() {
            System.out.println(TAG + "updateStepCount");
            return 1337;
        }
    }
}
