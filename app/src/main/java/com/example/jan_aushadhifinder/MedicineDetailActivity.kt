package com.example.jan_aushadhifinder

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MedicineDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_medicine_detail)

        // VIEWS
        val brandName =
            findViewById<TextView>(
                R.id.brandName
            )

        val genericName =
            findViewById<TextView>(
                R.id.genericName
            )

        val category =
            findViewById<TextView>(
                R.id.category
            )

        val brandPrice =
            findViewById<TextView>(
                R.id.brandPrice
            )

        val genericPrice =
            findViewById<TextView>(
                R.id.genericPrice
            )

        val savings =
            findViewById<TextView>(
                R.id.savings
            )

        val medicineDescription =
            findViewById<TextView>(
                R.id.medicineDescription
            )

        // GET DATA FROM ADAPTER
        val brand =
            intent.getStringExtra(
                "brandName"
            ) ?: ""

        val generic =
            intent.getStringExtra(
                "genericName"
            ) ?: ""

        val medicineCategory =
            intent.getStringExtra(
                "category"
            ) ?: ""

        val brandedPrice =
            intent.getDoubleExtra(
                "brandPrice",
                0.0
            )

        val genericMedicinePrice =
            intent.getDoubleExtra(
                "genericPrice",
                0.0
            )

        // CALCULATE SAVINGS
        val save =
            brandedPrice -
                    genericMedicinePrice

        // SET VALUES
        brandName.text =
            brand

        genericName.text =
            generic

        category.text =
            "Category: $medicineCategory"

        brandPrice.text =
            "Branded Price: ₹$brandedPrice"

        genericPrice.text =
            "Generic Price: ₹$genericMedicinePrice"

        savings.text =
            "You Save ₹$save"

        // DYNAMIC DESCRIPTION
        medicineDescription.text =

            "$generic is a generic alternative " +
                    "for $brand. " +

                    "It belongs to the " +
                    "$medicineCategory category. " +

                    "This medicine provides " +
                    "similar medical benefits " +
                    "while helping users reduce " +
                    "medicine expenses through " +
                    "Jan Aushadhi generic medicines."
    }
}