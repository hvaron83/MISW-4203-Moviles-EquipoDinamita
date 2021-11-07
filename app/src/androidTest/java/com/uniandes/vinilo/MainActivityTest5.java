package com.uniandes.vinilo;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.Matchers.allOf;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.uniandes.vinilo.ui.album.OnClickListener;
import com.uniandes.vinilo.ui.album.viewModel.AlbumViewModel;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest5 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testListaVisible() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testSelectItem_isDetailVisible() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //onView(withId(R.id.tvName)).check(matches((withText("Buscando"))));

    }

    @Test
    public void mainActivityTest6() {
        /*Matcher<View> m = atPosition(0, withText("Poeta del pueblo"));
        ViewInteraction itemView = onView(m);
        itemView.perform();

        onView(withId(R.id.recyclerView))
                .check(matches(m));*/
        return;
    }


    @Test
    public void mainActivityTest5() {

        Matcher<View> m = allOf(withId(R.id.recyclerView),
                        /*withContentDescription("Imagen del album"),
                        withParent(withParent(withId(R.id.recyclerView))),*/
                isDisplayed());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int n = getRecyclerViewCount(m);

        //Assert.assertEquals(6, Long.parseLong(n));
        //Assert.assertTrue(new Long(n) > 0l);

        Assert.assertEquals(true, n > 0);

        //ViewInteraction imageView = onView(m);

        //Matcher<View> m = imageView;


        return;
        //imageView.check(matches(isDisplayed()));
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