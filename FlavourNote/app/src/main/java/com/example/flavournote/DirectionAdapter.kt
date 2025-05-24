package com.example.flavournote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DirectionAdapter(private val directions: List<Direction>) :
    RecyclerView.Adapter<DirectionAdapter.DirectionViewHolder>() {

    class DirectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val directionText: TextView = itemView.findViewById(R.id.direction_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.direction_item, parent, false)
        return DirectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DirectionViewHolder, position: Int) {
        val direction = directions[position]
        holder.directionText.text = "${position + 1}. ${direction.step}"
    }

    override fun getItemCount(): Int = directions.size
}
