package it.feio.android.omninotes.testForThesis;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;

import android.os.SystemClock;
import android.view.Gravity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.feio.android.omninotes.MainActivity;
import it.feio.android.omninotes.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MyTestCases {
    private static final String NOTE_TITLE = "Titolo di prova";
    private static final String NOTE_CONTENT = "Contenuto di prova";

    @Test
    public void testInsertNote(){
        /*=======================
           1-Click fab
           2-Verify the UI
           3-Insert Title
           4-Insert Content
           5-Go back
           6-Verify the UI
         ========================*/

        //  SETUP
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);

        //Click on fab button
        onView(withId(R.id.fab_expand_menu_button)).perform(click());
        onView(withId(R.id.fab_note)).perform(click());
        //Check if navigation on another fragment has happened
        onView(withId(R.id.detail_root)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_tile_card)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_content_card)).check(matches(isDisplayed()));
        //Inserting new title
        onView(withId(R.id.detail_title)).perform(typeText(NOTE_TITLE));
        onView(withId(R.id.detail_content)).perform(typeText(NOTE_CONTENT));

        pressBack();
        pressBack();

        onView(withText(NOTE_TITLE)).check(matches(isDisplayed()));
        onView(withText(NOTE_CONTENT)).check(matches(isDisplayed()));
    }

    @Test
    public void testArchiveNote(){
        /*=======================
           1-Swipe Left
           2-Click drawer
           3-Verify that archive section is appeared
           4-Click on Archive
           5-Verify that the note archived is displayed
         ========================*/

        //  SETUP
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);

        //Swipe left
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        //Open drawer two times to avoid animation of swipe left
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout))
                .check(matches(isOpen(Gravity.LEFT)))
                .perform(DrawerActions.close());
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
        //Click on Archive on the drawer menu
        onView(withText("Archive")).check(matches(isDisplayed()));
        onView(withText("Archive")).perform(click());
        //Check if actually the note has been archived
        onView(withText(NOTE_TITLE)).check(matches(isDisplayed()));
        onView(withText(NOTE_CONTENT)).check(matches(isDisplayed()));
    }

}
