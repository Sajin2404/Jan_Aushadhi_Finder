package com.example.jan_aushadhifinder

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val name = findViewById<EditText>(R.id.name)
        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phone)
        val password = findViewById<EditText>(R.id.password)
        val confirmPassword = findViewById<EditText>(R.id.confirmPassword)
        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val loginText = findViewById<TextView>(R.id.loginText)

        signupBtn.setOnClickListener {

            val nameVal = name.text.toString()
            val emailVal = email.text.toString()
            val phoneVal = phone.text.toString()
            val passVal = password.text.toString()
            val confirmVal = confirmPassword.text.toString()

            when {
                nameVal.isEmpty() || emailVal.isEmpty() ||
                        phoneVal.isEmpty() || passVal.isEmpty() -> {
                    Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                }

                !Patterns.EMAIL_ADDRESS.matcher(emailVal).matches() -> {
                    Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
                }

                phoneVal.length < 10 -> {
                    Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show()
                }

                passVal.length < 6 -> {
                    Toast.makeText(this, "Password must be 6+ characters", Toast.LENGTH_SHORT)
                        .show()
                }

                passVal != confirmVal -> {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }

                else -> {

                    val auth = com.google.firebase.auth.FirebaseAuth.getInstance()

                    auth.createUserWithEmailAndPassword(
                        emailVal,
                        passVal
                    ).addOnCompleteListener {

                        if (it.isSuccessful) {

                            Toast.makeText(
                                this,
                                "Account Created!",
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(
                                Intent(
                                    this,
                                    MainActivity::class.java
                                )
                            )

                            finish()

                        } else {

                            Toast.makeText(
                                this,
                                it.exception?.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
        loginText.setOnClickListener {
            finish()
        }
    }
}