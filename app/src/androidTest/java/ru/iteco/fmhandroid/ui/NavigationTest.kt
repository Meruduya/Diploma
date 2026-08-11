package ru.iteco.fmhandroid.ui

import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import io.qameta.allure.kotlin.junit4.DisplayName
import org.hamcrest.Matchers.anyOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.TestData
import ru.iteco.fmhandroid.ui.data.Waiters
import ru.iteco.fmhandroid.ui.pages.AboutPage
import ru.iteco.fmhandroid.ui.pages.AuthPage
import ru.iteco.fmhandroid.ui.pages.MainPage
import ru.iteco.fmhandroid.ui.pages.NewsPage
import ru.iteco.fmhandroid.ui.pages.OurMissionPage
import ru.iteco.fmhandroid.ui.data.ScreenshotRule

@RunWith(AndroidJUnit4::class)
@Epic("UI-тестирование приложения «Мобильный хоспис»")
@Feature("Навигация и информационные экраны")
class NavigationTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AppActivity::class.java)

    @get:Rule
    val screenshotRule = ScreenshotRule()
    private val authPage = AuthPage()
    private val mainPage = MainPage()
    private val newsPage = NewsPage()
    private val aboutPage = AboutPage()
    private val ourMissionPage = OurMissionPage()

    @Before
    fun loginIfNeeded() {
        Waiters.waitForDisplayedView(
            anyOf(withId(R.id.enter_button), withId(R.id.main_menu_image_button)),
            20000
        )

        try {
            authPage.checkAuthScreenIsDisplayed()
            authPage.login(TestData.VALID_LOGIN, TestData.VALID_PASSWORD)
        } catch (e: Exception) {
        }

        mainPage.waitForMainScreen()
    }

    @Test
    @Story("Переход на экран About")
    @DisplayName("Экран About открывается с главного экрана")
    @Description("Проверяет, что через меню навигации с главного экрана открывается экран информации о приложении")
    fun aboutScreenShouldBeOpenedFromMainScreen() {
        mainPage.openMainMenu()
        mainPage.selectMenuItem("About")
        aboutPage.waitForAboutScreen()
        aboutPage.checkAboutScreenIsDisplayed()
    }

    @Test
    @Story("Содержимое экрана About")
    @DisplayName("Экран About содержит версию, ссылки и информацию о компании")
    @Description("Проверяет состав экрана информации о приложении: версия, ссылки на документы и данные о компании")
    fun aboutScreenShouldContainVersionAndLinks() {
        mainPage.openMainMenu()
        mainPage.selectMenuItem("About")
        aboutPage.waitForAboutScreen()
        aboutPage.checkLinksAreDisplayed()
        aboutPage.checkCompanyInfoIsDisplayed()
    }

    @Test
    @Story("Возврат с экрана About")
    @DisplayName("Кнопка возврата закрывает экран About")
    @Description("Проверяет, что по кнопке возврата с экрана About выполняется переход на главный экран")
    fun backButtonShouldReturnFromAboutScreen() {
        mainPage.openMainMenu()
        mainPage.selectMenuItem("About")
        aboutPage.waitForAboutScreen()
        aboutPage.clickBackButton()
        mainPage.waitForMainScreen()
        mainPage.checkMainScreenIsDisplayed()
    }

    @Test
    @Story("Раздел цитат")
    @DisplayName("Экран цитат открывается по иконке в шапке приложения")
    @Description("Проверяет, что по иконке в шапке приложения открывается экран с тематическими цитатами")
    fun ourMissionScreenShouldBeOpenedFromAppBar() {
        mainPage.openOurMission()
        ourMissionPage.waitForOurMissionScreen()
        ourMissionPage.checkOurMissionScreenIsDisplayed()
    }

    @Test
    @Story("Переход в раздел новостей")
    @DisplayName("Раздел News открывается из меню навигации")
    @Description("Проверяет, что через меню навигации открывается экран со списком новостей")
    fun newsScreenShouldBeOpenedFromMainMenu() {
        mainPage.openMainMenu()
        mainPage.selectMenuItem("News")
        newsPage.waitForNewsList()
        newsPage.checkNewsListIsDisplayed()
    }

    @Test
    @Story("Переход на экран About")
    @DisplayName("Экран About открывается с экрана новостей")
    @Description("Проверяет доступность раздела About с экрана News. Тест выявляет дефект BUG-11: пункт меню отключён на этом экране, переход не выполняется. Падение теста ожидаемо до исправления дефекта")
    fun aboutScreenShouldBeOpenedFromNewsScreen() {
        mainPage.openAllNews()
        newsPage.waitForNewsList()
        mainPage.openMainMenu()
        mainPage.selectMenuItem("About")
        aboutPage.waitForAboutScreen()
        aboutPage.checkAboutScreenIsDisplayed()
    }
}