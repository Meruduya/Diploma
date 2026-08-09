package ru.iteco.fmhandroid.ui.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import io.qameta.allure.kotlin.Allure
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.Waiters

class AboutPage {

    private val versionTitle: Matcher<View> =
        allOf(withId(R.id.about_version_title_text_view), isDisplayed())
    private val versionValue: Matcher<View> =
        allOf(withId(R.id.about_version_value_text_view), isDisplayed())
    private val privacyPolicyLink: Matcher<View> =
        allOf(withId(R.id.about_privacy_policy_value_text_view), isDisplayed())
    private val termsOfUseLink: Matcher<View> =
        allOf(withId(R.id.about_terms_of_use_value_text_view), isDisplayed())
    private val companyInfo: Matcher<View> =
        allOf(withId(R.id.about_company_info_label_text_view), isDisplayed())
    private val backButton: Matcher<View> =
        allOf(withId(R.id.about_back_image_button), isDisplayed())

    fun waitForAboutScreen(): AboutPage {
        Allure.step("Дождаться загрузки экрана информации о приложении") {
            Waiters.waitForDisplayedView(versionTitle, 15000)
        }
        return this
    }

    fun checkAboutScreenIsDisplayed(): AboutPage {
        Allure.step("Проверить отображение версии приложения") {
            onView(versionTitle).check(matches(isDisplayed()))
            onView(versionValue).check(matches(isDisplayed()))
        }
        return this
    }

    fun checkLinksAreDisplayed(): AboutPage {
        Allure.step("Проверить отображение ссылок на документы") {
            onView(privacyPolicyLink).check(matches(isDisplayed()))
            onView(termsOfUseLink).check(matches(isDisplayed()))
        }
        return this
    }

    fun checkCompanyInfoIsDisplayed(): AboutPage {
        Allure.step("Проверить отображение информации о компании") {
            onView(companyInfo).check(matches(isDisplayed()))
        }
        return this
    }

    fun clickBackButton(): AboutPage {
        Allure.step("Нажать кнопку возврата") {
            onView(backButton).perform(click())
        }
        return this
    }
}