package ru.iteco.fmhandroid.ui.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.qameta.allure.kotlin.Allure
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import ru.iteco.fmhandroid.R
import ru.iteco.fmhandroid.ui.data.Waiters

class NewsPage {

    private val newsRecyclerView: Matcher<View> =
        allOf(withId(R.id.news_list_recycler_view), isDisplayed())
    private val sortButton: Matcher<View> =
        allOf(withId(R.id.sort_news_material_button), isDisplayed())
    private val filterButton: Matcher<View> =
        allOf(withId(R.id.filter_news_material_button), isDisplayed())
    private val controlPanelButton: Matcher<View> =
        allOf(withId(R.id.edit_news_material_button), isDisplayed())
    private val expandButton: Matcher<View> =
        allOf(withId(R.id.expand_material_button), isDisplayed())
    private val emptyListText: Matcher<View> =
        allOf(withId(R.id.empty_news_list_text_view), isDisplayed())
    private val refreshButton: Matcher<View> =
        allOf(withId(R.id.news_retry_material_button), isDisplayed())

    fun waitForNewsList(): NewsPage {
        Allure.step("Дождаться загрузки списка новостей") {
            Waiters.waitForDisplayedView(newsRecyclerView, 20000)
        }
        return this
    }

    fun checkNewsListIsDisplayed(): NewsPage {
        Allure.step("Проверить отображение списка новостей") {
            onView(newsRecyclerView).check(matches(isDisplayed()))
        }
        return this
    }

    fun clickSortButton(): NewsPage {
        Allure.step("Нажать кнопку сортировки новостей") {
            onView(sortButton).perform(click())
        }
        return this
    }

    fun openFilter(): NewsPage {
        Allure.step("Открыть экран фильтрации новостей") {
            onView(filterButton).perform(click())
        }
        return this
    }

    fun openControlPanel(): NewsPage {
        Allure.step("Открыть панель управления новостями") {
            onView(controlPanelButton).perform(click())
        }
        return this
    }

    fun clickExpandButton(): NewsPage {
        Allure.step("Свернуть или развернуть секцию новостей") {
            onView(expandButton).perform(click())
        }
        return this
    }

    fun checkEmptyStateIsDisplayed(): NewsPage {
        Allure.step("Проверить отображение пустого состояния экрана") {
            onView(emptyListText).check(matches(isDisplayed()))
        }
        return this
    }

    fun clickRefreshButton(): NewsPage {
        Allure.step("Нажать кнопку обновления списка") {
            onView(refreshButton).perform(click())
        }
        return this
    }

    fun checkNewsIsDisplayed(title: String): NewsPage {
        Allure.step("Проверить наличие новости в списке: $title") {
            onView(allOf(withText(title), isDisplayed())).check(matches(isDisplayed()))
        }
        return this
    }
}