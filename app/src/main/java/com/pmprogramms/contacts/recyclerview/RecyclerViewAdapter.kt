package com.pmprogramms.contacts.recyclerview

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pmprogramms.contacts.R
import com.pmprogramms.contacts.model.Contact


class RecyclerViewAdapter(
    private val arrayList: List<Contact>
) :
    RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.contact_item, parent, false)

        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.nameTV.text = arrayList[position].name
        holder.numberTV.text = arrayList[position].number
        if (arrayList[position].imageUri != null) holder.photoIV.setImageURI(Uri.parse(arrayList[position].imageUri))
        else holder.photoIV.setImageResource(R.drawable.ic_baseline_person_24)

        holder.container.setOnClickListener {
//            val intent = Intent(holder.container.context, ContactDetailsActivity::class.java)
//            holder.container.context.startActivity(intent)
        }

        holder.container.setOnLongClickListener {
            // menu contact/send msg
            false
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var container: RelativeLayout = itemView.findViewById(R.id.item_container)
    var photoIV: ImageView = itemView.findViewById(R.id.photo)
    var nameTV: TextView = itemView.findViewById(R.id.name)
    var numberTV: TextView = itemView.findViewById(R.id.number)
}