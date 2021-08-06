package com.example.diffwatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diffwatch.R
import com.example.diffwatch.data.models.commits.required.CommitsResponseItem

class CommitsAdapter(val clickListener: CommitListListener): RecyclerView.Adapter<CommitsAdapter.ViewHolder>() {

    var data = listOf<CommitsResponseItem>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.commits_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]



        holder.commitTitle.text = item.commit?.message
        holder.committerName.text = item.author?.login
        holder.commitDate.text = item.commit?.committer?.date

        holder.itemView.setOnClickListener {
            clickListener.onClickItem(item)
        }

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val commitTitle: TextView = itemView.findViewById(R.id.commit_title)
        val committerName: TextView = itemView.findViewById(R.id.committer_name)
        val commitDate: TextView = itemView.findViewById(R.id.commit_date)

    }

}

class CommitListListener(val clickListener: (commitsResponseItem: CommitsResponseItem) -> Unit) {
    fun onClickItem(commitsResponseItem: CommitsResponseItem) = clickListener(commitsResponseItem)
}