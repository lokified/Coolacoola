package com.loki.coolacoola.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loki.coolacoola.R

class InstructionAdapter (
    val instructions: Array<String>
        ) : RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>() {


    inner class InstructionViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val instruction : TextView = itemView.findViewById(R.id.instruct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        return InstructionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.instruction_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        holder.instruction.text = "- " + instructions[position]
    }

    override fun getItemCount(): Int {
        return instructions.size
    }
}