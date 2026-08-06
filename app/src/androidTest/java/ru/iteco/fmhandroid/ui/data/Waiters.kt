package ru.iteco.fmhandroid.ui.data

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher

object Waiters {

    /**
     * Ожидает появления элемента в иерархии в течение заданного времени.
     * Используется вместо Thread.sleep для синхронизации с асинхронной
     * загрузкой экранов приложения.
     */
    fun waitForView(viewMatcher: Matcher<View>, timeoutMillis: Long = 10000) {
        onView(isRoot()).perform(waitForMatch(viewMatcher, timeoutMillis))
    }

    private fun waitForMatch(viewMatcher: Matcher<View>, timeoutMillis: Long): ViewAction {
        return object : ViewAction {

            override fun getConstraints(): Matcher<View> = isRoot()

            override fun getDescription(): String =
                "Ожидание появления элемента в течение $timeoutMillis мс"

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadUntilIdle()
                val endTime = System.currentTimeMillis() + timeoutMillis

                do {
                    for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                        if (viewMatcher.matches(child)) {
                            return
                        }
                    }
                    uiController.loopMainThreadForAtLeast(100)
                } while (System.currentTimeMillis() < endTime)

                throw NoMatchingViewException.Builder()
                    .withViewMatcher(viewMatcher)
                    .withRootView(view)
                    .build()
            }
        }
    }
}