package com.example.santepriceindex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var btnExit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
        val btnCalculator =
            findViewById<Button>(R.id.btnCalculator)

        val btnPriceWatch =
            findViewById<Button>(R.id.btnPriceWatch)

        val btnPriceBoard =
            findViewById<Button>(R.id.btnPriceBoard)

        val btnTrends =
            findViewById<Button>(R.id.btnTrends)


        btnCalculator.setOnClickListener {

            val intent =
                Intent(this, CalculatorActivity::class.java)

            startActivity(intent)
        }

        btnPriceWatch.setOnClickListener {

            val intent =
                Intent(this, PriceWatchActivity::class.java)

            startActivity(intent)
        }

        btnPriceBoard.setOnClickListener {
            val intent = Intent(this, DigitalBoardActivity::class.java)
            startActivity(intent)

        }

        btnTrends.setOnClickListener {

            val intent =
                Intent(this, TrendsActivity::class.java)

            startActivity(intent)
        }

        btnExit = findViewById(R.id.btnExit)
    }
}