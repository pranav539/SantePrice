package com.example.safejobs

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.database.FirebaseDatabase

class PostJobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_job)

        // Toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Input fields
        val title = findViewById<EditText>(R.id.jobTitleEditText)
        val salary = findViewById<EditText>(R.id.salaryEditText)
        val description = findViewById<EditText>(R.id.descriptionEditText)
        val company = findViewById<EditText>(R.id.companyEditText)

        // Submit button
        val submitButton = findViewById<Button>(R.id.btnSubmitJob)

        // Exit button
        val exitButton = findViewById<Button>(R.id.exitButton)

        // Exit app
        exitButton.setOnClickListener {
            finishAffinity()
        }

        // Submit job
        submitButton.setOnClickListener {

            val jobTitle = title.text.toString().trim()
            val jobSalary = salary.text.toString().toIntOrNull()
            val jobDesc = description.text.toString().trim()
            val jobCompany = company.text.toString().trim()

            // Validation
            if (jobTitle.isEmpty() ||
                jobSalary == null ||
                jobDesc.isEmpty() ||
                jobCompany.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Fraud detection
            val status = detectFraud(
                jobTitle,
                jobSalary,
                jobDesc,
                jobCompany
            )

            // Block suspicious jobs
            if (status.startsWith("⚠️")) {

                Toast.makeText(
                    this,
                    status,
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            // Firebase Database
            val database = FirebaseDatabase.getInstance(
                "https://safejobs-3f6e3-default-rtdb.asia-southeast1.firebasedatabase.app"
            )

            val ref = database.getReference("jobs")

            // Generate job ID
            val jobId = ref.push().key

            if (jobId == null) {

                Toast.makeText(
                    this,
                    "Failed to generate Job ID",
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            // Job data
            val jobData = mapOf(
                "title" to jobTitle,
                "salary" to jobSalary,
                "description" to jobDesc,
                "company" to jobCompany,
                "status" to status
            )

            // Upload to Firebase
            ref.child(jobId).setValue(jobData)

                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Job submitted successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Clear fields
                    title.text.clear()
                    salary.text.clear()
                    description.text.clear()
                    company.text.clear()
                }

                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Failed: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    // Toolbar back button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // Fraud detection logic
    private fun detectFraud(
        title: String,
        salary: Int,
        desc: String,
        company: String
    ): String {

        val text =
            (title + " " + desc + " " + company).lowercase()

        val suspiciousWords = listOf(
            "quick money",
            "guaranteed income",
            "easy cash",
            "work from home easy money",
            "invest now",
            "crypto profit",
            "registration fee"
        )

        if (salary > 10000000) {
            return "⚠️ Suspicious: Salary too high"
        }

        if (desc.length < 20) {
            return "⚠️ Suspicious: Description too short"
        }

        if (company.lowercase().contains("unknown")) {
            return "⚠️ Suspicious: Unverified company"
        }

        for (word in suspiciousWords) {

            if (text.contains(word)) {

                return "⚠️ Suspicious: Scam-like content detected"
            }
        }

        return "✅ Safe Job"
    }
}