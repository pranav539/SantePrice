package com.example.safejobs

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.database.*

class ViewJobsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_jobs)

        // Toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // TextView for displaying jobs
        val textView = findViewById<TextView>(R.id.jobsTextView)

        // Exit button
        val exitButton = findViewById<Button>(R.id.exitButton)

        exitButton.setOnClickListener {
            finishAffinity()
        }

        // Firebase Database connection
        val database = FirebaseDatabase.getInstance(
            "https://safejobs-3f6e3-default-rtdb.asia-southeast1.firebasedatabase.app"
        )

        val ref = database.getReference("jobs")

        // Read jobs from Firebase
        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                // No jobs available
                if (!snapshot.exists()) {

                    textView.text = "No jobs posted yet"
                    return
                }

                val builder = StringBuilder()

                // Read each job
                for (jobSnapshot in snapshot.children) {

                    val title =
                        jobSnapshot.child("title")
                            .value.toString()

                    val company =
                        jobSnapshot.child("company")
                            .value.toString()

                    val salary =
                        jobSnapshot.child("salary")
                            .value.toString()

                    val status =
                        jobSnapshot.child("status")
                            .value.toString()

                    // Build display text
                    builder.append("Job Title: $title\n")
                    builder.append("Company: $company\n")
                    builder.append("Salary: ₹$salary\n")
                    builder.append("Status: $status\n")
                    builder.append("\n-------------------------\n\n")
                }

                // Show jobs
                textView.text = builder.toString()
            }

            override fun onCancelled(error: DatabaseError) {

                textView.text =
                    "Failed to load jobs: ${error.message}"
            }
        })
    }

    // Toolbar back button
    override fun onSupportNavigateUp(): Boolean {

        finish()
        return true
    }
}