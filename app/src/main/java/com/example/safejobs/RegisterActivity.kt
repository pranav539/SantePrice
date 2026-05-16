package com.example.safejobs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth   // ✅ ADD THIS

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth   // ✅ ADD THIS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()   // ✅ INIT FIREBASE

        val emailEditText = findViewById<EditText>(R.id.registerEmailEditText)
        val passwordEditText = findViewById<EditText>(R.id.registerPasswordEditText)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {

            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()

            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()

            } else if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()

            } else {

                // 🔥 REAL FIREBASE REGISTRATION
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()

                            // Go to Login screen
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()

                        } else {

                            Toast.makeText(
                                this,
                                "Registration Failed: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }
}