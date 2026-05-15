package com.example.jan_aushadhifinder

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchActivity : AppCompatActivity() {

    private lateinit var adapter: MedicineAdapter

    private lateinit var medicineList:
            List<Medicine>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        val recyclerView =
            findViewById<RecyclerView>(
                R.id.recyclerView
            )

        val searchInput =
            findViewById<EditText>(
                R.id.searchInput
            )

        val searchBtn =
            findViewById<ImageButton>(
                R.id.searchBtn
            )

        // LOAD DATABASE
        medicineList = loadMedicines()

        // EMPTY ADAPTER
        adapter =
            MedicineAdapter(emptyList())

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        // SEARCH BUTTON CLICK
        searchBtn.setOnClickListener {

            val query =
                searchInput.text
                    .toString()
                    .trim()
                    .lowercase()

            // FILTER RESULTS
            val filteredList =
                medicineList.filter {

                    (it.brandName ?: "")
                        .lowercase()
                        .contains(query)

                            ||

                            (it.genericName ?: "")
                                .lowercase()
                                .contains(query)
                }

            // SHOW RESULTS
            if (filteredList.isNotEmpty()) {

                adapter.updateList(
                    filteredList
                )

            } else {

                adapter.updateList(
                    emptyList()
                )

                Toast.makeText(
                    this,
                    "Medicine Not Found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // LOAD JSON DATABASE
    private fun loadMedicines():
            List<Medicine> {

        val jsonString =
            assets.open(
                "medicine_database.json"
            )
                .bufferedReader()
                .use {
                    it.readText()
                }

        val type =
            object :
                TypeToken<List<Medicine>>() {}.type

        return Gson().fromJson(
            jsonString,
            type
        )
    }
}