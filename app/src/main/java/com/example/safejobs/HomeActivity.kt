package com.example.safejobs
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.Toast
import android.content.Intent
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Toast.makeText(this, "HOME OPENED", Toast.LENGTH_LONG).show()
        val viewJobsButton = findViewById<Button>(R.id.viewJobsButton)
        val postJobButton = findViewById<Button>(R.id.btnOpenPostJob)

        viewJobsButton.setOnClickListener {
            Toast.makeText(this, "View Jobs clicked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ViewJobsActivity::class.java))
        }

        postJobButton.setOnClickListener {
            Toast.makeText(this, "Post Job clicked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, PostJobActivity::class.java))
        }
    }
}