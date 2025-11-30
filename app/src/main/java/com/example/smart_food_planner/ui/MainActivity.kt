package com.example.smart_food_planner.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.smart_food_planner.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.itemIconTintList = null

        if (supportFragmentManager.findFragmentByTag("home") == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, home(), "home")
                .commit()
        }

        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener { item ->

            bottomNav.menu.findItem(R.id.nav_home).setIcon(R.drawable.ic_bn_home)
            bottomNav.menu.findItem(R.id.nav_search).setIcon(R.drawable.ic_bn_search)
            bottomNav.menu.findItem(R.id.nav_calender).setIcon(R.drawable.ic_bn_calendar)
            bottomNav.menu.findItem(R.id.nav_ai).setIcon(R.drawable.ic_bn_fav)

            val transaction = supportFragmentManager.beginTransaction()

            supportFragmentManager.fragments.forEach { transaction.hide(it) }

            val tag = when (item.itemId) {
                R.id.nav_home -> {
                    item.setIcon(R.drawable.ic_home_selected)
                    "home"
                }

                R.id.nav_search -> {
                    item.setIcon(R.drawable.ic_search_selected)
                    "search"
                }

                R.id.nav_calender -> {
                    item.setIcon(R.drawable.ic_calender_selected)
                    "calendar"
                }

                R.id.nav_ai -> {
                    item.setIcon(R.drawable.ic_favorite_selected)
                    "favorite"
                }

                else -> "home"
            }

            var fragment = supportFragmentManager.findFragmentByTag(tag)

            if (fragment == null) {
                fragment = when (tag) {
                    "home" -> home()
                    "search" -> Search()
                    "calendar" -> Calender()
                    "favorite" -> Favorite_Fragmnet()
                    else -> home()
                }
                transaction.add(R.id.container, fragment, tag)
            } else {
                transaction.show(fragment)
            }

            transaction.commitAllowingStateLoss()

            true
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

    fun setBottomNavVisible(visible: Boolean) {
        if (visible && bottomNav.visibility != View.VISIBLE) {
            bottomNav.animate().alpha(1f).setDuration(180).withStartAction {
                bottomNav.visibility = View.VISIBLE
            }
        } else if (!visible && bottomNav.visibility == View.VISIBLE) {
            bottomNav.animate().alpha(0f).setDuration(180).withEndAction {
                bottomNav.visibility = View.GONE
            }
        }
    }
}