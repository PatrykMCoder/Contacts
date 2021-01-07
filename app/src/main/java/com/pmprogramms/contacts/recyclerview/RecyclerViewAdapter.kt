package com.pmprogramms.contacts.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pmprogramms.contacts.R

class RecyclerViewAdapter(var arrayList: ArrayList<ContactsObject>) : RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.contact_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.nameTV.text = arrayList[position].name
        holder.numberTV.text = arrayList[position].number

        Log.d("RECYCLER", arrayList[position].name)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameTV: TextView = itemView.findViewById(R.id.name)
    var numberTV: TextView = itemView.findViewById(R.id.number)
}