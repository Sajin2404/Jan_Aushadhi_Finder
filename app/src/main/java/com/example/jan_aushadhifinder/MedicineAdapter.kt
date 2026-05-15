package com.example.jan_aushadhifinder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicineAdapter(
    private var medicineList: List<Medicine>
) : RecyclerView.Adapter<MedicineAdapter.ViewHolder>() {

    // VIEW HOLDER
    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val brandName: TextView =
            itemView.findViewById(R.id.brandName)

        val genericName: TextView =
            itemView.findViewById(R.id.genericName)

        val brandPrice: TextView =
            itemView.findViewById(R.id.brandPrice)

        val genericPrice: TextView =
            itemView.findViewById(R.id.genericPrice)

        val savings: TextView =
            itemView.findViewById(R.id.savings)

        val category: TextView =
            itemView.findViewById(R.id.category)

        val detailsBtn: Button =
            itemView.findViewById(R.id.detailsBtn)
    }

    // CREATE VIEW
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.medicine_item,
                    parent,
                    false
                )

        return ViewHolder(view)
    }

    // ITEM COUNT
    override fun getItemCount(): Int {
        return medicineList.size
    }

    // BIND DATA
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val medicine =
            medicineList[position]

        // BRAND NAME
        holder.brandName.text =
            medicine.brandName

        // GENERIC NAME
        holder.genericName.text =
            medicine.genericName

        // CATEGORY
        holder.category.text =
            "Category: ${medicine.category}"

        // BRAND PRICE
        holder.brandPrice.text =
            "Branded Price: ₹${medicine.brandPrice}"

        // GENERIC PRICE
        holder.genericPrice.text =
            "Generic Price: ₹${medicine.genericPrice}"

        // SAVINGS
        val save =
            medicine.brandPrice -
                    medicine.genericPrice

        holder.savings.text =
            "You Save ₹$save"

        // OPEN DETAILS
        holder.detailsBtn.setOnClickListener {

            val intent =
                Intent(
                    holder.itemView.context,
                    MedicineDetailActivity::class.java
                )

            // PASS DATA
            intent.putExtra(
                "brandName",
                medicine.brandName
            )

            intent.putExtra(
                "genericName",
                medicine.genericName
            )

            intent.putExtra(
                "brandPrice",
                medicine.brandPrice
            )

            intent.putExtra(
                "genericPrice",
                medicine.genericPrice
            )

            intent.putExtra(
                "category",
                medicine.category
            )

            holder.itemView.context
                .startActivity(intent)
        }
    }

    // UPDATE FILTERED LIST
    fun updateList(
        newList: List<Medicine>
    ) {

        medicineList = newList

        notifyDataSetChanged()
    }
}