package com.example.jan_aushadhifinder

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        logoutBtn.setOnClickListener {
            finish() // go back to login
        }
    }
}