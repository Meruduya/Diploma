package ru.iteco.fmhandroid.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.TestData
import ru.iteco.fmhandroid.ui.pages.AuthPage
import ru.iteco.fmhandroid.ui.data.Waiters

@RunWith(AndroidJUnit4::class)
class AuthTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AppActivity::class.java)

    private val authPage = AuthPage()

    @Before
    fun prepareAuthScreen() {

        Waiters.waitForView(withId(R.id.main_menu_image_button), 15000)
        try {
            onView(withId(R.id.authorization_image_button)).perform(click())
            onView(withText("Log out")).perform(click())
        } catch (e: Exception) {

        }

        authPage.waitForAuthScreen()
    }

    @Test
    fun authScreenIsDisplayedAfterLogout() {
        authPage.checkAuthScreenIsDisplayed()
    }

    @Test
    fun loginWithValidCredentialsShouldSucceed() {
        authPage.login(TestData.VALID_LOGIN, TestData.VALID_PASSWORD)
        Waiters.waitForView(withId(R.id.main_menu_image_button), 20000)
    }

    @Test
    fun loginWithInvalidPasswordShouldFail() {
        authPage.login(TestData.VALID_LOGIN, TestData.INVALID_PASSWORD)
        authPage.checkAuthScreenIsDisplayed()
    }
}