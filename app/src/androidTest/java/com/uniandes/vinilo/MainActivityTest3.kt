package com.uniandes.vinilo


import android.util.Log
import android.view.View
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso

import androidx.test.espresso.Espresso.onView
import org.hamcrest.TypeSafeMatcher
import org.junit.runner.Description
import java.util.regex.Matcher


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest3 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest3() {

        Thread.sleep(3000)

        /*val imageView = onData(
            allOf(
                withId(R.id.imgPhoto), withContentDescription("Imagen del album"),
                withParent(withParent(withId(R.id.recyclerView))),
                isDisplayed()
            )
        ).atPosition(0)//.inAdapterView(withId(R.id.recyclerView)).atPosition(0)

        imageView.check(matches(isDisplayed()))*/

        val imageView2 = onView(
            allOf(
                withId(R.id.imgPhoto),
                withContentDescription("Imagen del album"),
                not(isDescendantOfA(withId(R.id.recyclerView))),
                withParent(withParent(withId(R.id.recyclerView))),
                isDisplayed()
            )
        )

        /*val imageView2 = onData(
            allOf(
                withId(R.id.imgPhoto),
                isDisplayed()
            )
        ).atPosition(0)*/

        imageView2.check(matches(isDisplayed()))
        //imageView.check()

        /*val imageView2 = onView(
            allOf(
                withContentDescription("Poeta del pueblo")
            )
        )*/

        //imageView2.check(matches(isDisplayed()))


        /*imageView.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.tvName), withText("Buscando Am�rica"),
                withParent(withParent(withId(R.id.recyclerView))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Buscando Am�rica")))

        val textView2 = onView(
            allOf(
                withId(R.id.tvDescription),
                withText("Buscando Am�rica es el primer �lbum de la banda de Rub�n Blades y Seis del Solar lanzado en 1984. La producci�n, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983."),
                withParent(withParent(withId(R.id.recyclerView))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Buscando Am�rica es el primer �lbum de la banda de Rub�n Blades y Seis del Solar lanzado en 1984. La producci�n, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.")))*/
    }
}
