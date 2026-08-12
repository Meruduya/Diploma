package ru.iteco.fmhandroid.ui.data

import android.graphics.Bitmap
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import io.qameta.allure.kotlin.Allure
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.ByteArrayOutputStream

class ScreenshotRule : TestWatcher() {

    override fun failed(e: Throwable?, description: Description?) {
        try {
            val screenshot: Bitmap =
                InstrumentationRegistry.getInstrumentation().uiAutomation.takeScreenshot()

            val stream = ByteArrayOutputStream()
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, stream)

            Allure.attachment(
                name = "Скриншот падения: ${description?.methodName ?: "unknown"}",
                content = stream.toByteArray().inputStream(),
                type = "image/png",
                fileExtension = ".png"
            )
        } catch (error: Exception) {
            Log.e("ScreenshotRule", "Не удалось сделать скриншот: ${error.message}")
        }
    }
}