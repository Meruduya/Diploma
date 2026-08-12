package ru.iteco.fmhandroid.ui.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.qameta.allure.kotlin.Allure
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.Waiters

class ControlPanelPage {

    private val addNewsButton: Matcher<View> =
        allOf(withId(R.id.add_news_image_view), isDisplayed())

    fun waitForControlPanel(): ControlPanelPage {
        Allure.step("Дождаться загрузки панели управления новостями") {
            Waiters.waitForDisplayedView(addNewsButton, 15000)
        }
        return this
    }

    fun checkControlPanelIsDisplayed(): ControlPanelPage {
        Allure.step("Проверить отображение панели управления") {
            onView(addNewsButton).check(matches(isDisplayed()))
        }
        return this
    }

    fun clickAddNewsButton(): ControlPanelPage {
        Allure.step("Нажать кнопку создания новости") {
            onView(addNewsButton).perform(click())
        }
        return this
    }

    fun checkNewsIsDisplayed(title: String): ControlPanelPage {
        Allure.step("Проверить наличие новости в списке: $title") {
            onView(allOf(withText(title), isDisplayed())).check(matches(isDisplayed()))
        }
        return this
    }

    fun checkNewsIsNotDisplayed(title: String): ControlPanelPage {
        Allure.step("Проверить отсутствие новости в списке: $title") {
            onView(allOf(withText(title), isDisplayed())).check(doesNotExist())
        }
        return this
    }

    fun clickDeleteOnNews(title: String): ControlPanelPage {
        Allure.step("Нажать кнопку удаления новости: $title") {
            onView(
                allOf(
                    withId(R.id.delete_news_item_image_view),
                    hasSibling(withText(title)),
                    isDisplayed()
                )
            ).perform(click())
        }
        return this
    }

    fun confirmDeletion(): ControlPanelPage {
        Allure.step("Подтвердить удаление новости") {
            onView(withText(R.string.fragment_positive_button)).perform(click())
        }
        return this
    }
}