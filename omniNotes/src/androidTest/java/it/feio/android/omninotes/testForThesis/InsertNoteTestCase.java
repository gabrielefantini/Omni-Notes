package it.feio.android.omninotes.testForThesis;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.feio.android.omninotes.MainActivity;
import it.feio.android.omninotes.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InsertNoteTestCase {
    private static final String NOTE_TITLE = "Titolo di prova";
    private static final String NOTE_CONTENT = "Contenuto di prova";


    @Test
    public void insertNewNote(){
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

        //click on fab button
        onView(withId(R.id.fab_expand_menu_button)).perform(click());
        onView(withId(R.id.fab_note)).perform(click());
        //check if navigation on another fragment has happened
        onView(withId(R.id.detail_root)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_tile_card)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_content_card)).check(matches(isDisplayed()));
        //inserting new title
        onView(withId(R.id.detail_title)).perform(typeText(NOTE_TITLE));
        onView(withId(R.id.detail_content)).perform(typeText(NOTE_CONTENT));

        pressBack();
        pressBack();

        onView(withText(NOTE_TITLE)).check(matches(isDisplayed()));
        onView(withText(NOTE_CONTENT)).check(matches(isDisplayed()));
    }

    @Test
    public void deleteNote(){
        //  SETUP
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        SystemClock.sleep(500);
        onView(withText(NOTE_TITLE)).check(doesNotExist());

    }

}
