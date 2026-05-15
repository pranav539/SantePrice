package com.example.santepriceindex

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.database.*

class DigitalBoardActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var databaseRef: DatabaseReference
    private lateinit var priceList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_digital_board)

        // Toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Digital Price Board"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // ListView
        listView = findViewById(R.id.listViewBoard)

        priceList = ArrayList()

        // Firebase reference
        databaseRef = FirebaseDatabase
            .getInstance("https://santepriceindex-f0c3d-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("prices")

        loadPrices()
    }

    private fun loadPrices() {

        databaseRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                priceList.clear()

                if (!snapshot.exists()) {

                    Toast.makeText(
                        this@DigitalBoardActivity,
                        "No price data found",
                        Toast.LENGTH_SHORT
                    ).show()

                    return
                }

                for (vegSnapshot in snapshot.children) {

                    val name = vegSnapshot.key
                    val price = vegSnapshot.getValue(Int::class.java)

                    if (name != null && price != null) {

                        priceList.add(
                            "${name.uppercase()}        ₹$price/kg"
                        )
                    }
                }

                val adapter = object : ArrayAdapter<String>(
                    this@DigitalBoardActivity,
                    android.R.layout.simple_list_item_1,
                    priceList
                ) {

                    override fun getView(
                        position: Int,
                        convertView: android.view.View?,
                        parent: android.view.ViewGroup
                    ): android.view.View {

                        val view = super.getView(position, convertView, parent)

                        val textView =
                            view.findViewById<android.widget.TextView>(android.R.id.text1)

                        textView.setTextColor(android.graphics.Color.GREEN)
                        textView.textSize = 26f

                        return view
                    }
                }

                listView.adapter = adapter

                listView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(
                    this@DigitalBoardActivity,
                    "Failed to load prices",
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