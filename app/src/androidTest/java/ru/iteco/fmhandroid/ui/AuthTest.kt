package ru.iteco.fmhandroid.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
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
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.TestData
import ru.iteco.fmhandroid.ui.data.Waiters
import ru.iteco.fmhandroid.ui.pages.AuthPage
import org.hamcrest.Matchers.anyOf

@RunWith(AndroidJUnit4::class)
@Epic("UI-тестирование приложения «Мобильный хоспис»")
@Feature("Авторизация")
class AuthTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AppActivity::class.java)

    private val authPage = AuthPage()

    @Before
    fun prepareAuthScreen() {
        Waiters.waitForDisplayedView(
            anyOf(withId(R.id.enter_button), withId(R.id.main_menu_image_button)),
            20000
        )

        try {
            onView(withId(R.id.authorization_image_button)).perform(click())
            onView(withText("Log out")).perform(click())
        } catch (e: Exception) {
        }

        authPage.waitForAuthScreen()
    }

    @Test
    @Story("Отображение экрана авторизации")
    @DisplayName("Экран авторизации отображается после выхода из аккаунта")
    @Description("Проверяет, что после выхода из аккаунта открывается экран авторизации с доступной кнопкой входа")
    fun authScreenIsDisplayedAfterLogout() {
        authPage.checkAuthScreenIsDisplayed()
    }

    @Test
    @Story("Вход в приложение")
    @DisplayName("Успешный вход с корректными учётными данными")
    @Description("Проверяет, что при вводе корректных логина и пароля выполняется вход и открывается главный экран приложения")
    fun loginWithValidCredentialsShouldSucceed() {
        authPage.login(TestData.VALID_LOGIN, TestData.VALID_PASSWORD)
        Waiters.waitForDisplayedView(withId(R.id.main_menu_image_button), 20000)
    }

    @Test
    @Story("Вход в приложение")
    @DisplayName("Отказ во входе при неверном пароле")
    @Description("Проверяет, что при вводе неверного пароля вход не выполняется и пользователь остаётся на экране авторизации")
    fun loginWithInvalidPasswordShouldFail() {
        authPage.login(TestData.VALID_LOGIN, TestData.INVALID_PASSWORD)
        authPage.checkAuthScreenIsDisplayed()
    }
}