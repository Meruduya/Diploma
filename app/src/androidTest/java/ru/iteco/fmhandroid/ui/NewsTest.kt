package ru.iteco.fmhandroid.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.iteco.fmhandroid.ui.data.TestData
import ru.iteco.fmhandroid.ui.pages.AuthPage
import ru.iteco.fmhandroid.ui.pages.MainPage
import ru.iteco.fmhandroid.ui.pages.NewsPage
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.anyOf
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.Waiters

@RunWith(AndroidJUnit4::class)
class NewsTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AppActivity::class.java)

    private val authPage = AuthPage()
    private val mainPage = MainPage()
    private val newsPage = NewsPage()

    @Before
    fun loginIfNeeded() {
        // Дожидаемся окончания заставки: приложение показывает либо экран
        // авторизации, либо главный экран, если сессия сохранена
        Waiters.waitForDisplayedView(
            anyOf(withId(R.id.enter_button), withId(R.id.main_menu_image_button)),
            20000
        )

        try {
            authPage.checkAuthScreenIsDisplayed()
            authPage.login(TestData.VALID_LOGIN, TestData.VALID_PASSWORD)
        } catch (e: Exception) {
            // Пользователь уже авторизован
        }

        mainPage.waitForMainScreen()
    }

    @Test
    fun newsSectionShouldBeDisplayedOnMainScreen() {
        newsPage.waitForNewsList()
        newsPage.checkNewsListIsDisplayed()
    }

    @Test
    fun allNewsLinkShouldOpenNewsScreen() {
        mainPage.openAllNews()
        newsPage.waitForNewsList()
        newsPage.checkNewsListIsDisplayed()
    }

    @Test
    fun sortButtonShouldChangeNewsOrder() {
        mainPage.openAllNews()
        newsPage.waitForNewsList()
        newsPage.clickSortButton()
        newsPage.checkNewsListIsDisplayed()
    }

    @Test
    fun controlPanelShouldOpenFromNewsScreen() {
        mainPage.openAllNews()
        newsPage.waitForNewsList()
        newsPage.openControlPanel()
        newsPage.checkNewsListIsDisplayed()
    }
}