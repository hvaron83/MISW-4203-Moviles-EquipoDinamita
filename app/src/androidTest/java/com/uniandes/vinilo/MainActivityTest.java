package com.uniandes.vinilo;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.Matchers.allOf;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.uniandes.vinilo.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testListaVisible() {
        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()));
    }

    @Test
    public void testSelectItem_isDetailVisible() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.albumsRv)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCountItems() {
        Matcher<View> m = allOf(withId(R.id.albumsRv),
                isDisplayed());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int n = getRecyclerViewCount(m);
        Assert.assertEquals(true, n > 0);
        return;
    }

    @Test
    public void testAddTracktoAlbum() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Ver el detalle del primer album
        onView(withId(R.id.albumsRv)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Nasvegar a pantalla asociar tracks
        onView(withId(R.id.addTrackButton)).perform(click());
        //Llenar la informacion del track
        onView(
                allOf(
                        withId(R.id.etName),
                        isDisplayed()
                )
        ).perform(replaceText("cancion"), closeSoftKeyboard());

        onView(
                allOf(
                        withId(R.id.etDuration),
                        isDisplayed()
                )
        ).perform(replaceText("03:03"), closeSoftKeyboard());
        onView(withId(R.id.action_save)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddAlbum() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Nasvegar a pantalla crear album
        onView(withId(R.id.fab)).perform(click());
        //Llenar la informacion del track
        onView(
                allOf(
                        withId(R.id.etName),
                        isDisplayed()
                )
        ).perform(replaceText("album"), closeSoftKeyboard());

        onView(
                allOf(
                        withId(R.id.etReleaseDate),
                        isDisplayed()
                )
        ).perform(replaceText("1984-04-27"), closeSoftKeyboard());

        onView(
                allOf(
                        withId(R.id.etDescription),
                        isDisplayed()
                )
        ).perform(replaceText("Album test"), closeSoftKeyboard());

        onView(
                allOf(
                        withId(R.id.etPhotoUrl),
                        isDisplayed()
                )
        ).perform(replaceText("https://i.pinimg.com/170x/4b/00/da/4b00dae36c4984c807ab211cd956c2c8.jpg"), closeSoftKeyboard());

        onView(withId(R.id.action_save)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int getRecyclerViewCount(Matcher matcher) {
        final int[] num = new int[1];
        onView(allOf(matcher, isEnabled())).check(matches(new TypeSafeMatcher<View>() {
            RecyclerView recyclerView = null;
            @Override
            public boolean matchesSafely(View view) {
                recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                Log.d("Booking Recycler Cnt", adapter.getItemCount() + "");
                num[0] = adapter.getItemCount();
                return true;
            }
            @Override
            public void describeTo(Description description) {
            }
        }));
        return num[0];
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }
            @Override protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

}
