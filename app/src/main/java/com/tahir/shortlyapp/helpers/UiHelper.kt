package com.tahir.shortlyapp.helpers

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Toast

object UiHelper {
    fun displayProgressBar(isDisplayed: Boolean, progess: View, context: Activity) {

        if (isDisplayed) {

            context.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            );
        } else {
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        }

        progess.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
    fun showToast(context:Context,msg:String){
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()

    }

}