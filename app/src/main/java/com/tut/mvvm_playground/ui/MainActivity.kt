package com.tut.mvvm_playground.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tut.mvvm_playground.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}