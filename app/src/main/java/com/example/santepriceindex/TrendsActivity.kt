package com.example.santepriceindex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TrendsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trends)
        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Market Trends"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}