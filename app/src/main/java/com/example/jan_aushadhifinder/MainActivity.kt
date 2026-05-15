package com.example.jan_aushadhifinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    // FIREBASE AUTH
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // LOGIN SCREEN
        setContentView(R.layout.activity_main)

        // FIREBASE
        auth = FirebaseAuth.getInstance()

        // VIEWS
        val email =
            findViewById<EditText>(R.id.email)

        val password =
            findViewById<EditText>(R.id.password)

        val loginBtn =
            findViewById<Button>(R.id.loginBtn)

        val guestBtn =
            findViewById<Button>(R.id.guestBtn)

        val signupText =
            findViewById<TextView>(R.id.signupText)

        val forgotPasswordText =
            findViewById<TextView>(R.id.forgotPasswordText)

        // LOGIN BUTTON
        loginBtn.setOnClickListener {

            val emailValue =
                email.text.toString().trim()

            val passwordValue =
                password.text.toString().trim()

            // EMPTY CHECK
            if (
                emailValue.isEmpty() ||
                passwordValue.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please enter all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                // FIREBASE LOGIN
                auth.signInWithEmailAndPassword(
                    emailValue,
                    passwordValue
                ).addOnCompleteListener {

                    if (it.isSuccessful) {

                        Toast.makeText(
                            this,
                            "Login Successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        // OPEN DASHBOARD
                        startActivity(
                            Intent(
                                this,
                                DashboardActivity::class.java
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

        // CONTINUE AS GUEST
        guestBtn.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    DashboardActivity::class.java
                )
            )
        }

        // OPEN SIGNUP
        signupText.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SignupActivity::class.java
                )
            )
        }

        // OPEN FORGOT PASSWORD
        forgotPasswordText.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ForgotPasswordActivity::class.java
                )
            )
        }
    }
}