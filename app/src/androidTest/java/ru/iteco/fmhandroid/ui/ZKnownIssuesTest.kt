package ru.iteco.fmhandroid.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.iteco.fmhandroid.ui.data.ScreenshotRule
import ru.iteco.fmhandroid.ui.data.TestData
import ru.iteco.fmhandroid.ui.pages.AboutPage
import ru.iteco.fmhandroid.ui.pages.AuthPage
import ru.iteco.fmhandroid.ui.pages.MainPage
import ru.iteco.fmhandroid.ui.pages.NewsPage

@RunWith(AndroidJUnit4::class)
@Epic("UI-тестирование приложения «Мобильный хоспис»")
@Feature("Выявленные дефекты")
class ZKnownIssuesTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AppActivity::class.java)

    @get:Rule
    val screenshotRule = ScreenshotRule()

    private val authPage = AuthPage()
    private val mainPage = MainPage()
    private val newsPage = NewsPage()
    private val aboutPage = AboutPage()

    @Before
    fun prepareMainScreen() {
        activityRule.scenario.recreate()
        mainPage.waitForAnyStartScreen()
        authPage.loginIfNeeded(TestData.VALID_LOGIN, TestData.VALID_PASSWORD)
        mainPage.waitForMainScreen()
    }

    @Test
    @Story("Навигация")
    @DisplayName("BUG-11: экран About недоступен с экрана новостей")
    @Description("Проверяет доступность раздела About с экрана News. Тест выявляет дефект: пункт меню отключён на этом экране, переход не выполняется. Падение теста ожидаемо до исправления дефекта")
    fun aboutScreenShouldBeOpenedFromNewsScreen() {
        mainPage.openAllNews()
        newsPage.waitForNewsList()
        mainPage.openMainMenu()
        mainPage.selectAboutMenuItem()
        aboutPage.checkAboutScreenIsDisplayed()
    }
}