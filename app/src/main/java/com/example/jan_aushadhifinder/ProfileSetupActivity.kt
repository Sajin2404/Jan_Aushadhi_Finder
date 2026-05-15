package com.example.jan_aushadhifinder

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ProfileSetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        val saveBtn = findViewById<Button>(R.id.saveBtn)

        saveBtn.setOnClickListener {
            Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }
}