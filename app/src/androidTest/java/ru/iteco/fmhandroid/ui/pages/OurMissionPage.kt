package ru.iteco.fmhandroid.ui.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import io.qameta.allure.kotlin.Allure
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.Waiters

class OurMissionPage {

    private val screenTitle: Matcher<View> =
        allOf(withId(R.id.our_mission_title_text_view), isDisplayed())
    private val quotesRecyclerView: Matcher<View> =
        allOf(withId(R.id.our_mission_item_list_recycler_view), isDisplayed())

    fun waitForOurMissionScreen(): OurMissionPage {
        Allure.step("Дождаться загрузки экрана цитат") {
            Waiters.waitForDisplayedView(quotesRecyclerView, 15000)
        }
        return this
    }

    fun checkOurMissionScreenIsDisplayed(): OurMissionPage {
        Allure.step("Проверить отображение списка цитат") {
            onView(screenTitle).check(matches(isDisplayed()))
            onView(quotesRecyclerView).check(matches(isDisplayed()))
        }
        return this
    }
}