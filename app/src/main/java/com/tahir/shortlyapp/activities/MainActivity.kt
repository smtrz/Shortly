package com.tahir.shortlyapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tahir.shortlyapp.R
import com.tahir.shortlyapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialization for data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //  setContentView(R.layout.activity_main)

    }
}