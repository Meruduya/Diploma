package ru.iteco.fmhandroid.ui.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.qameta.allure.kotlin.Allure
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anyOf
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.Waiters

class MainPage {

    private val mainMenuButton: Matcher<View> = withId(R.id.main_menu_image_button)
    private val authorizationButton: Matcher<View> = withId(R.id.authorization_image_button)
    private val ourMissionButton: Matcher<View> = withId(R.id.our_mission_image_button)
    private val allNewsLink: Matcher<View> = allOf(withId(R.id.all_news_text_view), isDisplayed())
    private val enterButton: Matcher<View> = withId(R.id.enter_button)

    fun waitForMainScreen(): MainPage {
        Allure.step("Дождаться загрузки главного экрана") {
            Waiters.waitForDisplayedView(mainMenuButton, 20000)
        }
        return this
    }

    fun waitForAnyStartScreen(): MainPage {
        Allure.step("Дождаться загрузки приложения: главный экран или экран авторизации") {
            Waiters.waitForDisplayedView(anyOf(enterButton, mainMenuButton), 20000)
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

    fun logoutIfLoggedIn(): MainPage {
        Allure.step("Выйти из аккаунта, если вход выполнен") {
            if (isLoggedIn()) {
                logOut()
            }
        }
        return this
    }

    private fun isLoggedIn(): Boolean = try {
        onView(allOf(mainMenuButton, isDisplayed())).check(matches(isDisplayed()))
        true
    } catch (e: NoMatchingViewException) {
        false
    }
    fun selectNewsMenuItem(): MainPage {
        Allure.step("Выбрать пункт меню News") {
            onView(withText(R.string.news)).perform(click())
        }
        return this
    }
    fun selectAboutMenuItem(): MainPage {
        Allure.step("Выбрать пункт меню About") {
            onView(withText(R.string.about)).perform(click())
        }
        return this
    }
}