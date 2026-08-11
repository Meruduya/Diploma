package ru.iteco.fmhandroid.ui.data

import android.graphics.Bitmap
import androidx.test.core.app.takeScreenshot
import io.qameta.allure.kotlin.Allure
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.ByteArrayOutputStream


class ScreenshotRule : TestWatcher() {

    override fun failed(e: Throwable?, description: Description?) {
        try {
            val screenshot: Bitmap = takeScreenshot()
            val stream = ByteArrayOutputStream()
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, stream)

            Allure.attachment(
                name = "Снимок экрана при падении теста",
                content = stream.toByteArray().inputStream(),
                type = "image/png",
                fileExtension = ".png"
            )
        } catch (error: Exception) {
        }
    }
}