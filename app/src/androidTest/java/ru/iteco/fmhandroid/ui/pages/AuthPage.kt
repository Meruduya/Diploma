package ru.iteco.fmhandroid.ui.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.android.material.textfield.TextInputEditText
import io.qameta.allure.kotlin.Allure
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.Waiters

class AuthPage {

    private val loginField: Matcher<View> = allOf(
        isAssignableFrom(TextInputEditText::class.java),
        isDescendantOfA(withId(R.id.login_text_input_layout))
    )

    private val passwordField: Matcher<View> = allOf(
        isAssignableFrom(TextInputEditText::class.java),
        isDescendantOfA(withId(R.id.password_text_input_layout))
    )

    private val signInButton: Matcher<View> = withId(R.id.enter_button)

    fun waitForAuthScreen(): AuthPage {
        Allure.step("Дождаться загрузки экрана авторизации") {
            Waiters.waitForDisplayedView(signInButton, 20000)
        }
        return this
    }

    fun checkAuthScreenIsDisplayed(): AuthPage {
        Allure.step("Проверить отображение экрана авторизации") {
            onView(signInButton).check(matches(isDisplayed()))
        }
        return this
    }

    fun enterLogin(login: String): AuthPage {
        Allure.step("Ввести логин: $login") {
            onView(loginField).perform(replaceText(login), closeSoftKeyboard())
        }
        return this
    }

    fun enterPassword(password: String): AuthPage {
        Allure.step("Ввести пароль") {
            onView(passwordField).perform(replaceText(password), closeSoftKeyboard())
        }
        return this
    }

    fun clickSignInButton(): AuthPage {
        Allure.step("Нажать кнопку входа") {
            onView(signInButton).perform(click())
        }
        return this
    }

    fun login(login: String, password: String): AuthPage {
        Allure.step("Выполнить вход с логином $login") {
            enterLogin(login)
            enterPassword(password)
            clickSignInButton()
        }
        return this
    }

    fun loginIfNeeded(login: String, password: String): AuthPage {
        Allure.step("Выполнить вход, если открыт экран авторизации") {
            if (isOnAuthScreen()) {
                login(login, password)
            }
        }
        return this
    }

    fun checkStillOnAuthScreen(timeoutMillis: Long = 7000): AuthPage {
        Allure.step("Убедиться, что вход не выполнен и экран авторизации остался открытым") {
            val endTime = System.currentTimeMillis() + timeoutMillis
            while (System.currentTimeMillis() < endTime) {
                onView(signInButton).check(matches(isDisplayed()))
            }
        }
        return this
    }

    private fun isOnAuthScreen(): Boolean = try {
        onView(allOf(signInButton, isDisplayed())).check(matches(isDisplayed()))
        true
    } catch (e: NoMatchingViewException) {
        false
    }
}