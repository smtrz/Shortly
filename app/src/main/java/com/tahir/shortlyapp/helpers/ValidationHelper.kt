package com.tahir.shortlyapp.helpers

import android.util.Patterns
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


object ValidationHelper {
    /**
     * This is used to check the given URL is valid or not.
     * @param url
     * @return true if url is valid, false otherwise.
     */
     fun isValidUrl(url: String): Boolean {
        val p: Pattern = Patterns.WEB_URL
        val m: Matcher = p.matcher(url.lowercase(Locale.getDefault()))
        return m.matches()
    }
}