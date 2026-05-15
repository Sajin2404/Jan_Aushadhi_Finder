package com.example.jan_aushadhifinder

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Dashboard Layout
        setContentView(R.layout.activity_dashboard)

        // 🔍 SEARCH CARD
        val searchCard =
            findViewById<LinearLayout>(R.id.findMedicine)

        // 🗺️ MAP CARD
        val mapCard =
            findViewById<LinearLayout>(R.id.nearbyStores)

        // 👤 PROFILE CARD
        val profileCard =
            findViewById<LinearLayout>(R.id.schemes)

        // 🔍 OPEN SEARCH SCREEN
        searchCard.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SearchActivity::class.java
                )
            )
        }

        // 🗺️ OPEN MAP SCREEN
        mapCard.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    MapActivity::class.java
                )
            )
        }

        // 👤 OPEN PROFILE SCREEN
        profileCard.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ProfileActivity::class.java
                )
            )
        }
    }
}