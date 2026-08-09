package ru.iteco.fmhandroid.ui.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.qameta.allure.kotlin.Allure
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.Waiters

class CreateEditNewsPage {

    private val categoryField: Matcher<View> =
        withId(R.id.news_item_category_text_auto_complete_text_view)
    private val titleField: Matcher<View> = withId(R.id.news_item_title_text_input_edit_text)
    private val publishDateField: Matcher<View> =
        withId(R.id.news_item_publish_date_text_input_edit_text)
    private val publishTimeField: Matcher<View> =
        withId(R.id.news_item_publish_time_text_input_edit_text)
    private val descriptionField: Matcher<View> =
        withId(R.id.news_item_description_text_input_edit_text)
    private val activeSwitch: Matcher<View> = withId(R.id.switcher)
    private val saveButton: Matcher<View> = allOf(withId(R.id.save_button), isDisplayed())
    private val cancelButton: Matcher<View> = allOf(withId(R.id.cancel_button), isDisplayed())

    fun waitForForm(): CreateEditNewsPage {
        Allure.step("Дождаться загрузки формы новости") {
            Waiters.waitForDisplayedView(saveButton, 15000)
        }
        return this
    }

    fun checkFormIsDisplayed(): CreateEditNewsPage {
        Allure.step("Проверить отображение формы новости") {
            onView(saveButton).check(matches(isDisplayed()))
        }
        return this
    }

    fun selectCategory(category: String): CreateEditNewsPage {
        Allure.step("Выбрать категорию: $category") {
            onView(categoryField).perform(click())
            // Выпадающий список категорий отображается в отдельном popup-окне
            onView(withText(category))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click())
        }
        return this
    }

    fun enterTitle(title: String): CreateEditNewsPage {
        Allure.step("Ввести заголовок новости: $title") {
            onView(titleField).perform(replaceText(title), closeSoftKeyboard())
        }
        return this
    }

    fun enterDescription(description: String): CreateEditNewsPage {
        Allure.step("Ввести описание новости") {
            onView(descriptionField).perform(replaceText(description), closeSoftKeyboard())
        }
        return this
    }

    fun selectCurrentDate(): CreateEditNewsPage {
        Allure.step("Выбрать текущую дату публикации") {
            onView(publishDateField).perform(click())
            onView(withText("OK")).perform(click())
        }
        return this
    }

    fun selectCurrentTime(): CreateEditNewsPage {
        Allure.step("Выбрать текущее время публикации") {
            onView(publishTimeField).perform(click())
            onView(withText("OK")).perform(click())
        }
        return this
    }

    fun clickSaveButton(): CreateEditNewsPage {
        Allure.step("Нажать кнопку сохранения") {
            onView(saveButton).perform(click())
        }
        return this
    }

    fun clickCancelButton(): CreateEditNewsPage {
        Allure.step("Нажать кнопку отмены") {
            onView(cancelButton).perform(click())
        }
        return this
    }


    fun confirmCancellation(): CreateEditNewsPage {
        Allure.step("Подтвердить отмену изменений") {
            onView(withText("OK")).perform(click())
        }
        return this
    }


    fun checkValidationErrorIsDisplayed(): CreateEditNewsPage {
        Allure.step("Проверить, что новость не создана и форма осталась открытой") {
            onView(saveButton).check(matches(isDisplayed()))
            onView(cancelButton).check(matches(isDisplayed()))
        }
        return this
    }
}