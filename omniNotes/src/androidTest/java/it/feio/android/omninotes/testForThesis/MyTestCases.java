package it.feio.android.omninotes.testForThesis;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.scrollTo;
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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

import android.os.SystemClock;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

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

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
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
    public void testInfo(){
        /*=======================
           1-Open Drawer
           2-Verify the UI
           3-Go to Settings
           4-Go to Info
           5-Verify the UI
           6-3 time press back
         ========================*/

        //  SETUP
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());
        //Click on Archive on the drawer menu
        onView(withId(R.id.settings_view)).check(matches(isDisplayed()));
        onView(withId(R.id.settings_view)).perform(click());

        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(allOf(withId(android.R.id.title), withText(R.string.settings_screen_data)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(allOf(withId(android.R.id.title), withText(R.string.settings_screen_interface))).check(
                matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(4));
        onView(allOf(withId(android.R.id.title), withText(R.string.settings_screen_navigation))).check(
                matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(5));
        onView(allOf(withId(android.R.id.title), withText(R.string.settings_screen_behaviors))).check(
                matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(6));
        onView(allOf(withId(android.R.id.title), withText(R.string.settings_screen_notifications)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(7));
        onView(allOf(withId(android.R.id.title), withText(R.string.settings_screen_privacy)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(9));
        onView(allOf(withId(android.R.id.title), withText(R.string.settings_beta)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(11));
        onView(allOf(withId(android.R.id.title), withText(R.string.online_manual)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(12));
        onView(allOf(withId(android.R.id.title), withText(R.string.settings_tour_show_again)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(14));
        onView(allOf(withId(android.R.id.title), withText(R.string.settings_changelog)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(15));
        onView(allOf(withId(android.R.id.title), withText(R.string.settings_statistics)))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(16));
        onView(allOf(withId(android.R.id.title), withText(R.string.info)))
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withId(R.id.webview)).check(matches(isDisplayed()));

        pressBack();
        pressBack();
        pressBack();
    }

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

        insert(NOTE_TITLE, NOTE_CONTENT);

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

    @Test
    public void testSearchNotes(){
        /*=======================
           1-Insert 3 new notes
           2-Search for one title of the note
           3-Verify that the note searched is the only one that has been displayed
         ========================*/

        //  SETUP
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);

        insert("X", NOTE_CONTENT);
        insert("Y", NOTE_CONTENT);
        insert("Z", NOTE_CONTENT);

        onView(withId(R.id.menu_search)).perform(click());
        onView(withId(R.id.search_src_text))
                .perform(typeText("X"))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER));

        onView(allOf(withText("X"),withId(R.id.note_title))).check(matches(isDisplayed()));
        onView(allOf(withText("Y"),withId(R.id.note_title))).check(doesNotExist());
        onView(allOf(withText("Z"),withId(R.id.note_title))).check(doesNotExist());


    }


    private void insert(String title, String body){
        //Click on fab button
        onView(withId(R.id.fab_expand_menu_button)).perform(click());
        onView(withId(R.id.fab_note)).perform(click());
        //Check if navigation on another fragment has happened
        onView(withId(R.id.detail_root)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_tile_card)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_content_card)).check(matches(isDisplayed()));
        //Inserting new title
        onView(withId(R.id.detail_title)).perform(typeText(title));
        onView(withId(R.id.detail_content)).perform(typeText(body));

        pressBack();
        pressBack();
    }
}
