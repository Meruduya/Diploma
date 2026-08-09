package ru.iteco.fmhandroid.ui.pages

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.Waiters
import ru.iteco.fmhandroid.ui.data.clickChildViewWithId

class ControlPanelPage {

    private val addNewsButton: Matcher<View> =
        allOf(withId(R.id.add_news_image_view), isDisplayed())
    private val newsRecyclerView: Matcher<View> =
        allOf(withId(R.id.news_list_recycler_view), isDisplayed())
    private val sortButton: Matcher<View> =
        allOf(withId(R.id.sort_news_material_button), isDisplayed())
    private val filterButton: Matcher<View> =
        allOf(withId(R.id.filter_news_material_button), isDisplayed())

    fun waitForControlPanel(): ControlPanelPage {
        Waiters.waitForDisplayedView(addNewsButton, 15000)
        return this
    }

    fun checkControlPanelIsDisplayed(): ControlPanelPage {
        onView(addNewsButton).check(matches(isDisplayed()))
        return this
    }

    fun clickAddNewsButton(): ControlPanelPage {
        onView(addNewsButton).perform(click())
        return this
    }

    fun clickSortButton(): ControlPanelPage {
        onView(sortButton).perform(click())
        return this
    }

    fun openFilter(): ControlPanelPage {
        onView(filterButton).perform(click())
        return this
    }

    /**
     * Прокручивает список до новости с указанным заголовком.
     * Тесты работают только с новостями, созданными в рамках прогона,
     * поэтому поиск выполняется по уникальному заголовку.
     */
    fun scrollToNews(title: String): ControlPanelPage {
        onView(newsRecyclerView).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText(title))
            )
        )
        return this
    }

    fun checkNewsIsDisplayed(title: String): ControlPanelPage {
        scrollToNews(title)
        onView(withText(title)).check(matches(isDisplayed()))
        return this
    }

    fun clickEditOnNews(title: String): ControlPanelPage {
        onView(newsRecyclerView).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(title)),
                clickChildViewWithId(R.id.edit_news_item_image_view)
            )
        )
        return this
    }

    fun clickDeleteOnNews(title: String): ControlPanelPage {
        onView(newsRecyclerView).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(title)),
                clickChildViewWithId(R.id.delete_news_item_image_view)
            )
        )
        return this
    }

    fun confirmDeletion(): ControlPanelPage {
        onView(withText("OK")).perform(click())
        return this
    }

    fun cancelDeletion(): ControlPanelPage {
        onView(withText("CANCEL")).perform(click())
        return this
    }
}