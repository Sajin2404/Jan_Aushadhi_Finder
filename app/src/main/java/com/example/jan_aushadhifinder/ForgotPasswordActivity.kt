package com.example.jan_aushadhifinder

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val input = findViewById<EditText>(R.id.emailPhone)
        val resetBtn = findViewById<Button>(R.id.resetBtn)
        val backToLogin = findViewById<TextView>(R.id.backToLogin)

        resetBtn.setOnClickListener {
            if (input.text.isEmpty()) {
                Toast.makeText(this, "Enter email or phone", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Reset link sent!", Toast.LENGTH_SHORT).show()
            }
        }

        backToLogin.setOnClickListener {
            finish()
        }
    }
}