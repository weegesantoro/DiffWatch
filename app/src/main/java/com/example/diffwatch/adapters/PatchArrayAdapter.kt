package com.example.diffwatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diffwatch.R
import com.example.diffwatch.data.models.compare.PatchMatrix

class PatchArrayAdapter(): RecyclerView.Adapter<PatchArrayAdapter.ViewHolder>() {

    var data = listOf<PatchMatrix>()
        set(value){
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.file_diff_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.leftNumber.text = item.codeNumberLeft.toString()
        holder.patchDataLeft.text = item.patchTextLeft

        holder.rightNumber.text = item.codeNumberRight.toString()
        holder.patchDataRight.text = item.patchTextLeft
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val leftNumber: TextView = itemView.findViewById(R.id.number_left)
        val patchDataLeft: TextView = itemView.findViewById(R.id.patch_data_left)
        val rightNumber: TextView = itemView.findViewById(R.id.number_far_right)
        val patchDataRight: TextView = itemView.findViewById(R.id.patch_data_right)
    }


}