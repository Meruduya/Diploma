package ru.iteco.fmhandroid.ui.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.Waiters
import androidx.test.espresso.matcher.RootMatchers

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
        Waiters.waitForDisplayedView(saveButton, 15000)
        return this
    }

    fun checkValidationErrorIsDisplayed(): CreateEditNewsPage {
        onView(saveButton).check(matches(isDisplayed()))
        onView(cancelButton).check(matches(isDisplayed()))
        return this
    }

    fun selectCategory(category: String): CreateEditNewsPage {
        onView(categoryField).perform(click())

        onView(withText(category))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())
        return this
    }

    fun enterTitle(title: String): CreateEditNewsPage {
        onView(titleField).perform(replaceText(title), closeSoftKeyboard())
        return this
    }

    fun enterDescription(description: String): CreateEditNewsPage {
        onView(descriptionField).perform(replaceText(description), closeSoftKeyboard())
        return this
    }

    fun selectCurrentDate(): CreateEditNewsPage {
        onView(publishDateField).perform(click())
        onView(withText("OK")).perform(click())
        return this
    }

    fun selectCurrentTime(): CreateEditNewsPage {
        onView(publishTimeField).perform(click())
        onView(withText("OK")).perform(click())
        return this
    }

    fun clickSaveButton(): CreateEditNewsPage {
        onView(saveButton).perform(click())
        return this
    }

    fun clickCancelButton(): CreateEditNewsPage {
        onView(cancelButton).perform(click())
        return this
    }

    fun checkValidationMessageIsDisplayed(): CreateEditNewsPage {
        onView(withText("Fill empty fields")).check(matches(isDisplayed()))
        return this
    }

    fun confirmCancellation(): CreateEditNewsPage {
        onView(withText("OK")).perform(click())
        return this
    }
}