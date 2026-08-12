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
import ru.iteco.fmhandroid.ui.pages.OurMissionPage

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
    fun prepareMainScreen() {
        activityRule.scenario.recreate()
        mainPage.waitForAnyStartScreen()
        authPage.loginIfNeeded(TestData.VALID_LOGIN, TestData.VALID_PASSWORD)
        mainPage.waitForMainScreen()
    }

    @Test
    @Story("Переход на экран About")
    @DisplayName("Экран About открывается с главного экрана")
    @Description("Проверяет, что через меню навигации с главного экрана открывается экран информации о приложении")
    fun aboutScreenShouldBeOpenedFromMainScreen() {
        mainPage.openMainMenu()
        mainPage.selectAboutMenuItem()
        aboutPage.waitForAboutScreen()
        aboutPage.checkAboutScreenIsDisplayed()
    }

    @Test
    @Story("Содержимое экрана About")
    @DisplayName("Экран About содержит версию, ссылки и информацию о компании")
    @Description("Проверяет состав экрана информации о приложении: версия, ссылки на документы и данные о компании")
    fun aboutScreenShouldContainVersionAndLinks() {
        mainPage.openMainMenu()
        mainPage.selectAboutMenuItem()
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
        mainPage.selectAboutMenuItem()
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
        mainPage.selectNewsMenuItem()
        newsPage.waitForNewsList()
        newsPage.checkNewsListIsDisplayed()
    }
}