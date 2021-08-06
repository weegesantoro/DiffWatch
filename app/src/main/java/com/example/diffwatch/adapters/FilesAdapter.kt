package com.example.diffwatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.diffwatch.R
import com.example.diffwatch.data.models.files.required.CommitFile
import com.example.diffwatch.databinding.FileDiffListItemBinding
import com.example.diffwatch.ui.pulls.PatchMatrixVM


class FilesAdapter(): RecyclerView.Adapter<FilesAdapter.ViewHolder>() {

    var items = listOf<PatchMatrixVM>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FileDiffListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        println("updateList --- items.SIZE ${items.size}")
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        println("updateList --- $position")
    }

    inner class ViewHolder(private val binding: FileDiffListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PatchMatrixVM) {
            binding.vm = item
            binding.executePendingBindings()
            println("updateList --- binding item.commitFile.filename = ${item.commitFile.filename}")

        }

    }

    fun updateList(filesList: List<CommitFile>){
        println("updateList --- filesList.size = ${filesList.size}")

        val mutableItems = mutableListOf<PatchMatrixVM>()

        filesList.forEach { file ->
            file.patchMatrixList?.forEach{ patchMatrix ->
                mutableItems.add(PatchMatrixVM(file, patchMatrix))
            }
        }

        items = mutableItems
        println("updateList --- items.size = ${items.size}")
        notifyDataSetChanged()
    }

}


