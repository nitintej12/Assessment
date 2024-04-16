package com.dol.assessment.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dol.assessment.R
import com.dol.assessment.data.model.Post
import com.dol.assessment.data.model.PostsResponse

class RecyclerViewAdapter internal constructor(
    context: Context?,
    private val mData: PostsResponse
) :
    PagingDataAdapter<Post, RecyclerViewAdapter.ViewHolder>(PostComparator()) {
    private val mInflater: LayoutInflater

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mData.get(position)
        holder.tvId.text = data.id.toString()
        holder.tvUserId.text = data.userId.toString()
        holder.tvTitle.text = data.title
        holder.tvBody.text = data.body
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvId: TextView
        var tvUserId: TextView
        var tvTitle: TextView
        var tvBody: TextView

        init {
            tvId = itemView.findViewById(R.id.tv_id)
            tvUserId = itemView.findViewById(R.id.tv_uid)
            tvTitle = itemView.findViewById(R.id.tv_title)
            tvBody = itemView.findViewById(R.id.tv_body)
        }
    }
}


class PostComparator : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}