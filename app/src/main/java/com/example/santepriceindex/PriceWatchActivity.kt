package com.example.santepriceindex

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import android.widget.Button
import android.widget.ImageButton
class PriceWatchActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var databaseRef: DatabaseReference
    private lateinit var priceList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_watch)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Today's Mandi Prices"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        listView = findViewById(R.id.listViewPrices)
        priceList = ArrayList()

        // Firebase reference
        databaseRef = FirebaseDatabase.getInstance("https://santepriceindex-f0c3d-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("prices")

        loadPrices()
    }

    private fun loadPrices() {

        databaseRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                priceList.clear()

                if (!snapshot.exists()) {
                    Toast.makeText(
                        this@PriceWatchActivity,
                        "No vegetable data found",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                for (vegSnapshot in snapshot.children) {

                    val name = vegSnapshot.key
                    val price = vegSnapshot.getValue(Int::class.java)

                    if (name != null && price != null) {
                        priceList.add("$name → ₹$price /kg")
                    }
                }

                val adapter = ArrayAdapter(
                    this@PriceWatchActivity,
                    android.R.layout.simple_list_item_1,
                    priceList
                )

                listView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@PriceWatchActivity,
                    "Failed to load data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}