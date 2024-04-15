package com.dol.assessment.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dol.assessment.R
import com.dol.assessment.data.model.PostsResponse

class RecyclerViewAdapter internal constructor(
    context: Context?,
    private val mData: PostsResponse
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
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
        holder.tv_id.text = data.id.toString()
        holder.tv_userId.text = data.userId.toString()
        holder.tv_title.text = data.title
        holder.tv_body.text = data.body
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tv_id: TextView
        var tv_userId: TextView
        var tv_title: TextView
        var tv_body: TextView

        init {
            tv_id = itemView.findViewById(R.id.tv_id)
            tv_userId = itemView.findViewById(R.id.tv_uid)
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_body = itemView.findViewById(R.id.tv_body)
        }
    }
}