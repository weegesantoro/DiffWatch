package com.example.diffwatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diffwatch.R
import com.example.diffwatch.data.models.pulls.required.PullsResponseItem

class PullsAdapter(val clickListener: PullsListListener): RecyclerView.Adapter<PullsAdapter.ViewHolder>() {

    var data = listOf<PullsResponseItem>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.pulls_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]


        holder.pullTitle.text = item.title
        holder.pullDescription.text = "#${item.number} by ${item.user?.login} at ${item.createdAt}"

        holder.itemView.setOnClickListener {
            clickListener.onClickItem(item)
        }

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val pullTitle: TextView = itemView.findViewById(R.id.pr_title)
        val pullDescription: TextView = itemView.findViewById(R.id.pr_number_extra)
    }

}

class PullsListListener(val clickListener: (pullRequest: PullsResponseItem) -> Unit) {
    fun onClickItem(pullRequest: PullsResponseItem) = clickListener(pullRequest)
}