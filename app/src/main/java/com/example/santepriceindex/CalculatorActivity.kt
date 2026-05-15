package com.example.santepriceindex

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Price Calculator"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val etMandiPrice = findViewById<EditText>(R.id.etMandiPrice)
        val etTransport = findViewById<EditText>(R.id.etTransport)
        val etWaste = findViewById<EditText>(R.id.etWaste)
        val etProfit = findViewById<EditText>(R.id.etProfit)

        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnCalculate.setOnClickListener {

            val mandiPrice =
                etMandiPrice.text.toString().toDoubleOrNull() ?: 0.0

            val transport =
                etTransport.text.toString().toDoubleOrNull() ?: 0.0

            val waste =
                etWaste.text.toString().toDoubleOrNull() ?: 0.0

            val profitPercent =
                etProfit.text.toString().toDoubleOrNull() ?: 0.0

            val totalCost =
                mandiPrice + transport + waste

            val profit =
                totalCost * (profitPercent / 100)

            val recommendedPrice =
                totalCost + profit

            tvResult.text =
                "Recommended Price: ₹%.2f/kg"
                    .format(recommendedPrice)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}