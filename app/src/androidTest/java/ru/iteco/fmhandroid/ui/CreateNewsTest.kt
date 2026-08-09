package ru.iteco.fmhandroid.ui

import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.anyOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.TestData
import ru.iteco.fmhandroid.ui.data.Waiters
import ru.iteco.fmhandroid.ui.pages.AuthPage
import ru.iteco.fmhandroid.ui.pages.ControlPanelPage
import ru.iteco.fmhandroid.ui.pages.CreateEditNewsPage
import ru.iteco.fmhandroid.ui.pages.MainPage
import ru.iteco.fmhandroid.ui.pages.NewsPage

@RunWith(AndroidJUnit4::class)
class CreateNewsTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AppActivity::class.java)

    private val authPage = AuthPage()
    private val mainPage = MainPage()
    private val newsPage = NewsPage()
    private val controlPanelPage = ControlPanelPage()
    private val createEditNewsPage = CreateEditNewsPage()

    @Before
    fun openControlPanel() {
        // Дожидаемся окончания заставки приложения
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
        mainPage.openAllNews()
        newsPage.waitForNewsList()
        newsPage.openControlPanel()
        controlPanelPage.waitForControlPanel()
    }

    @Test
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
        controlPanelPage.checkNewsIsDisplayed(title)

        // Удаляем созданную новость, чтобы не засорять общие тестовые данные
        controlPanelPage.clickDeleteOnNews(title)
        controlPanelPage.confirmDeletion()
    }

    @Test
    fun createNewsWithEmptyFieldsShouldFail() {
        controlPanelPage.clickAddNewsButton()
        createEditNewsPage.waitForForm()
        createEditNewsPage.clickSaveButton()
        createEditNewsPage.checkValidationErrorIsDisplayed()
    }

    @Test
    fun cancelNewsCreationShouldNotCreateNews() {
        val title = TestData.uniqueTitle()

        controlPanelPage.clickAddNewsButton()
        createEditNewsPage.waitForForm()
        createEditNewsPage.enterTitle(title)
        createEditNewsPage.clickCancelButton()
        createEditNewsPage.confirmCancellation()

        controlPanelPage.waitForControlPanel()
        controlPanelPage.checkControlPanelIsDisplayed()
    }
}