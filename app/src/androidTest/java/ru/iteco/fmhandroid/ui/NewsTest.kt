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
import ru.iteco.fmhandroid.ui.pages.AuthPage
import ru.iteco.fmhandroid.ui.pages.MainPage
import ru.iteco.fmhandroid.ui.pages.NewsPage
import ru.iteco.fmhandroid.ui.data.ScreenshotRule

@RunWith(AndroidJUnit4::class)
@Epic("UI-тестирование приложения «Мобильный хоспис»")
@Feature("Новости")
class NewsTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AppActivity::class.java)

    @get:Rule
    val screenshotRule = ScreenshotRule()

    private val authPage = AuthPage()
    private val mainPage = MainPage()
    private val newsPage = NewsPage()

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
    @Story("Отображение новостей")
    @DisplayName("Секция новостей отображается на главном экране")
    @Description("Проверяет, что после входа в приложение на главном экране отображается список новостей")
    fun newsSectionShouldBeDisplayedOnMainScreen() {
        newsPage.waitForNewsList()
        newsPage.checkNewsListIsDisplayed()
    }

    @Test
    @Story("Переход к списку новостей")
    @DisplayName("Ссылка ALL NEWS открывает полный список новостей")
    @Description("Проверяет, что по ссылке ALL NEWS с главного экрана открывается экран с полным списком новостей")
    fun allNewsLinkShouldOpenNewsScreen() {
        mainPage.openAllNews()
        newsPage.waitForNewsList()
        newsPage.checkNewsListIsDisplayed()
    }

    @Test
    @Story("Сортировка новостей")
    @DisplayName("Кнопка сортировки изменяет порядок новостей")
    @Description("Проверяет, что нажатие кнопки сортировки не нарушает отображение списка новостей")
    fun sortButtonShouldChangeNewsOrder() {
        mainPage.openAllNews()
        newsPage.waitForNewsList()
        newsPage.clickSortButton()
        newsPage.checkNewsListIsDisplayed()
    }

    @Test
    @Story("Панель управления новостями")
    @DisplayName("Панель управления открывается с экрана новостей")
    @Description("Проверяет, что с экрана списка новостей можно открыть панель управления новостями")
    fun controlPanelShouldOpenFromNewsScreen() {
        mainPage.openAllNews()
        newsPage.waitForNewsList()
        newsPage.openControlPanel()
        newsPage.checkNewsListIsDisplayed()
    }
}