package com.example.smart_food_planner.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smart_food_planner.R
import com.example.smart_food_planner.ui.auth.RegisterFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load RegisterFragment first
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, RegisterFragment())
            .commit()
    }
}