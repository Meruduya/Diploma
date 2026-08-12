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
import ru.iteco.fmhandroid.ui.pages.AuthPage
import ru.iteco.fmhandroid.ui.pages.ControlPanelPage
import ru.iteco.fmhandroid.ui.pages.CreateEditNewsPage
import ru.iteco.fmhandroid.ui.pages.MainPage
import ru.iteco.fmhandroid.ui.pages.NewsPage

@RunWith(AndroidJUnit4::class)
@Epic("UI-тестирование приложения «Мобильный хоспис»")
@Feature("Создание новости")
class CreateNewsTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AppActivity::class.java)

    @get:Rule
    val screenshotRule = ScreenshotRule()

    private val authPage = AuthPage()
    private val mainPage = MainPage()
    private val newsPage = NewsPage()
    private val controlPanelPage = ControlPanelPage()
    private val createEditNewsPage = CreateEditNewsPage()

    @Before
    fun openControlPanel() {
        activityRule.scenario.recreate()
        mainPage.waitForAnyStartScreen()
        authPage.loginIfNeeded(TestData.VALID_LOGIN, TestData.VALID_PASSWORD)
        mainPage.waitForMainScreen()
        mainPage.openAllNews()
        newsPage.waitForNewsList()
        newsPage.openControlPanel()
        controlPanelPage.waitForControlPanel()
    }

    @Test
    @Story("Создание новости")
    @DisplayName("Создание новости с корректными данными")
    @Description("Проверяет, что новость с заполненными обязательными полями создаётся и отображается в списке новостей")
    fun createNewsWithValidDataShouldSucceed() {
        val title = TestData.uniqueTitle()

        controlPanelPage.clickAddNewsButton()
        createEditNewsPage.waitForForm()
        createEditNewsPage.selectCategory(TestData.CATEGORY_ANNOUNCEMENT)
        createEditNewsPage.enterTitle(title)
        createEditNewsPage.selectCurrentDate()
        createEditNewsPage.selectCurrentTime()
        createEditNewsPage.enterDescription(TestData.uniqueDescription())
        createEditNewsPage.clickSaveButton()

        controlPanelPage.waitForControlPanel()

        // Созданная новость отображается на экране News среди активных новостей
        mainPage.openMainMenu()
        mainPage.selectNewsMenuItem()
        newsPage.waitForNewsList()
        newsPage.checkNewsIsDisplayed(title)
    }

    @Test
    @Story("Валидация формы создания новости")
    @DisplayName("Отказ в создании новости с незаполненными полями")
    @Description("Проверяет, что при попытке сохранить пустую форму новость не создаётся и форма остаётся открытой")
    fun createNewsWithEmptyFieldsShouldFail() {
        controlPanelPage.clickAddNewsButton()
        createEditNewsPage.waitForForm()
        createEditNewsPage.clickSaveButton()
        createEditNewsPage.checkFormIsStillOpen()
    }

    @Test
    @Story("Отмена создания новости")
    @DisplayName("Отмена создания новости не создаёт запись")
    @Description("Проверяет, что при отмене создания новости запись не создаётся и выполняется возврат в панель управления")
    fun cancelNewsCreationShouldNotCreateNews() {
        val title = TestData.uniqueTitle()

        controlPanelPage.clickAddNewsButton()
        createEditNewsPage.waitForForm()
        createEditNewsPage.enterTitle(title)
        createEditNewsPage.clickCancelButton()
        createEditNewsPage.confirmCancellation()

        controlPanelPage.waitForControlPanel()
        controlPanelPage.checkControlPanelIsDisplayed()
        controlPanelPage.checkNewsIsNotDisplayed(title)
    }
}