package ru.iteco.fmhandroid.ui.data

object TestData {
    const val VALID_LOGIN = "login2"
    const val VALID_PASSWORD = "password2"
    const val INVALID_PASSWORD = "wrongpassword"

    const val CATEGORY_BIRTHDAY = "День рождения"
    const val CATEGORY_ANNOUNCEMENT = "Объявление"

    fun uniqueTitle(): String = "Autotest news ${System.currentTimeMillis()}"

    fun uniqueDescription(): String = "Autotest description ${System.currentTimeMillis()}"
}