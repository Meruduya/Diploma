package ru.iteco.fmhandroid.ui.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.qameta.allure.kotlin.Allure
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.Waiters

class MainPage {

    private val mainMenuButton: Matcher<View> = withId(R.id.main_menu_image_button)
    private val authorizationButton: Matcher<View> = withId(R.id.authorization_image_button)
    private val ourMissionButton: Matcher<View> = withId(R.id.our_mission_image_button)
    private val allNewsLink: Matcher<View> = allOf(withId(R.id.all_news_text_view), isDisplayed())

    fun waitForMainScreen(): MainPage {
        Allure.step("Дождаться загрузки главного экрана") {
            Waiters.waitForDisplayedView(mainMenuButton, 20000)
        }
        return this
    }

    fun checkMainScreenIsDisplayed(): MainPage {
        Allure.step("Проверить отображение главного экрана") {
            onView(mainMenuButton).check(matches(isDisplayed()))
        }
        return this
    }

    fun openAllNews(): MainPage {
        Allure.step("Перейти к полному списку новостей по ссылке ALL NEWS") {
            onView(allNewsLink).perform(click())
        }
        return this
    }

    fun openMainMenu(): MainPage {
        Allure.step("Открыть меню навигации") {
            onView(mainMenuButton).perform(click())
        }
        return this
    }

    fun selectMenuItem(itemTitle: String): MainPage {
        Allure.step("Выбрать пункт меню: $itemTitle") {
            onView(withText(itemTitle)).perform(click())
        }
        return this
    }

    fun openOurMission(): MainPage {
        Allure.step("Перейти в раздел цитат") {
            onView(ourMissionButton).perform(click())
        }
        return this
    }

    fun logOut(): MainPage {
        Allure.step("Выполнить выход из аккаунта") {
            onView(authorizationButton).perform(click())
            onView(withText("Log out")).perform(click())
        }
        return this
    }
}