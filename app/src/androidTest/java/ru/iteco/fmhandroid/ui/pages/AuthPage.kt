package ru.iteco.fmhandroid.ui.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.android.material.textfield.TextInputEditText
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

    fun checkAuthScreenIsDisplayed(): AuthPage {
        onView(signInButton).check(matches(isDisplayed()))
        return this
    }

    fun enterLogin(login: String): AuthPage {
        onView(loginField).perform(replaceText(login), closeSoftKeyboard())
        return this
    }

    fun enterPassword(password: String): AuthPage {
        onView(passwordField).perform(replaceText(password), closeSoftKeyboard())
        return this
    }

    fun clickSignInButton(): AuthPage {
        onView(signInButton).perform(click())
        return this
    }

    fun login(login: String, password: String): AuthPage {
        enterLogin(login)
        enterPassword(password)
        clickSignInButton()
        return this
    }

    fun waitForAuthScreen(): AuthPage {
        Waiters.waitForView(signInButton)
        return this
    }
}