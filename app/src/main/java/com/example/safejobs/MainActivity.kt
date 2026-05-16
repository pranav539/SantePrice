package com.example.safejobs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Open Login screen
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}