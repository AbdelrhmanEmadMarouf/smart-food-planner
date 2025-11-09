package com.example.smart_food_planner.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.smart_food_planner.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.itemIconTintList = null

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, home())
            .commit()

        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener { it->
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.nav_home -> {
                    fragment = home()
                }
                R.id.nav_search -> {
                    fragment = Search()
                }
                R.id.nav_calender -> {
                    fragment = Calender()
                    bottomNav.visibility = View.GONE
                }
                R.id.nav_ai -> {
                    fragment = AI()
                    bottomNav.visibility = View.GONE
                }
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment!!)
                .commit()
            return@setOnItemSelectedListener true
        }



    }

    @Suppress("DEPRECATION")
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }



}