package it.feio.android.omninotes;


import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatImageView;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryLifecycleTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private final String CATEGORY_NAME = "Cat_1488909966110";

    @Test
    public void test01AddNewCategory() {

        ViewInteraction viewInteraction = onView(
                allOf(withId(R.id.fab_expand_menu_button),
                        withParent(withId(R.id.fab)),
                        isDisplayed()));
        viewInteraction.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_note),
                        withParent(withId(R.id.fab)),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_category), withContentDescription("Category"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction mDButton = onView(
                allOf(withId(R.id.buttonDefaultPositive), withText("Add category"), isDisplayed()));
        mDButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.category_title), isDisplayed()));
        appCompatEditText.perform(replaceText(CATEGORY_NAME), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.save), withText("Ok"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.detail_title),
                        withParent(allOf(withId(R.id.title_wrapper),
                                withParent(withId(R.id.detail_tile_card)))),
                        isDisplayed()));
        editText.perform(click());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.detail_title),
                        withParent(allOf(withId(R.id.title_wrapper),
                                withParent(withId(R.id.detail_tile_card)))),
                        isDisplayed()));
        editText2.perform(replaceText("Note with new category"), closeSoftKeyboard());

        ViewInteraction navigationUp = onView(
                allOf(withContentDescription("drawer open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        navigationUp.perform(click());

    }

    @Test
    public void test02CheckCategoryCreation() {

        ViewInteraction drawerToggle = onView(
                allOf(withContentDescription("drawer open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        drawerToggle.perform(click());

        ViewInteraction textView = onView(allOf(withId(R.id.title), withText(CATEGORY_NAME)));
        textView.check(matches(withText(CATEGORY_NAME)));

    }

    @Test
    public void test03CategoryColorChange() {

        ViewInteraction drawerToggle = onView(
                allOf(withContentDescription("drawer open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        drawerToggle.perform(click());

        ViewInteraction categoryView = onView(allOf(withId(R.id.title), withText(CATEGORY_NAME)));
        categoryView.perform(longClick());

        ViewInteraction imageView = onView(allOf(withId(R.id.color_chooser), isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction appCompatImageView = onView(allOf(withId(R.id.color_chooser), isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction mDButton2 = onView(
                allOf(withId(R.id.buttonDefaultNeutral), withText("Custom"),
                        withParent(allOf(withId(R.id.root),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        mDButton2.perform(click());

        ViewInteraction mDButton3 = onView(
                allOf(withId(R.id.buttonDefaultNeutral), withText("Presets"),
                        withParent(allOf(withId(R.id.root),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        mDButton3.perform(click());

        ViewInteraction circleView = onView(
                childAtPosition(
                        withId(R.id.grid),
                        18));
        circleView.perform(scrollTo(), click());

        ViewInteraction circleView2 = onView(
                childAtPosition(
                        withId(R.id.grid),
                        9));
        circleView2.perform(scrollTo(), click());

        ViewInteraction mDButton4 = onView(
                allOf(withId(R.id.buttonDefaultPositive), withText("Done"),
                        withParent(allOf(withId(R.id.root),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        mDButton4.perform(click());

        ViewInteraction appCompatImageViewColorChanged = onView(allOf(withId(R.id.color_chooser), isDisplayed()));
        appCompatImageViewColorChanged.check(matches(withBackgroundColor(Color.parseColor("#FF263238"))));

    }

    @Test
    public void test04CategoryDeletion() {

        ViewInteraction drawerToggle = onView(
                allOf(withContentDescription("drawer open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        drawerToggle.perform(click());

        ViewInteraction categoryView = onView(allOf(withId(R.id.title), withText(CATEGORY_NAME)));
        categoryView.perform(longClick());

        ViewInteraction deleteBtn = onView(
                allOf(withId(R.id.delete), withText("Delete"), isDisplayed()));
        deleteBtn.perform(click());

        ViewInteraction deleteConfirmBtn = onView(
                allOf(withId(R.id.buttonDefaultPositive), withText("Confirm"), isDisplayed()));
        deleteConfirmBtn.perform(click());

        // Waiting a little to ensure Eventbus post propagation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction categoryDeletedView = onView(allOf(withId(R.id.title), withText(CATEGORY_NAME)));
        categoryDeletedView.check(doesNotExist());

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Matcher<View> withBackgroundColor(final int backgroundColor) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                ColorFilter cf = new PorterDuffColorFilter(Color.parseColor("#FF263238"), PorterDuff.Mode.SRC_ATOP);
                ColorFilter cf1 = ((AppCompatImageView) view).getDrawable().getColorFilter();
                return cf.equals(cf1);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with background color from id: " + backgroundColor);
            }
        };
    }
}
