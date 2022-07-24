package com.tahir.shortlyapp.helpers

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Toast

object UiHelper {
    /**
     * This is used to check the given URL is valid or not.
     * @param isDisplayed
     * @param progess
     * @param context sets visiblity of progressbar to true if isDisplayed is true else set
     * visiblity to false.
     */
    fun displayProgressBar(isDisplayed: Boolean, progess: View, context: Activity) {

        if (isDisplayed) {

            context
                .getWindow()
                .setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
        } else {
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

        progess.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
    /**
     * This is used to check the given URL is valid or not.
     * @param context
     * @param msg Show toast message.
     */
    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
    /**
     * This is used to check the given URL is valid or not.
     * @param context
     * @param msg Show toast message.
     */
    fun copyTextToClipboard(context: Context, data: String) {
        val textToCopy = data
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        showToast(context, "URl Copied to clipboard")
    }
}
