package com.tahir.shortlyapp

import androidx.core.util.PatternsCompat
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidationTests {

    // successfull test case for url validation.
    @Test
    fun sucessValidateUrl() {
        var url = "github.io/tahir-raza"
        assertTrue(PatternsCompat.WEB_URL.matcher(url).matches())
    }
}