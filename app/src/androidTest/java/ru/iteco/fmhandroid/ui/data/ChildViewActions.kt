package ru.iteco.fmhandroid.ui.data

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Matcher

fun clickChildViewWithId(id: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isAssignableFrom(View::class.java)

        override fun getDescription(): String = "Нажатие на дочерний элемент с id $id"

        override fun perform(uiController: UiController, view: View) {
            val child = view.findViewById<View>(id)
            child?.performClick()
            uiController.loopMainThreadUntilIdle()
        }
    }
}