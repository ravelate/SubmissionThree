package com.fianku.submissionthree.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fianku.submissionthree.R
import com.fianku.submissionthree.Response.UsersResponseItem

class UsersAdapter(private val listUser: List<UsersResponseItem>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_users, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvItemName.text = listUser[position].login
        viewHolder.tvUserReal.text = listUser[position].htmlUrl
        Glide.with(viewHolder.itemView.context)
            .load("${listUser[position].avatarUrl}")
            .circleCrop()
            .into(viewHolder.imgItemPhoto)
        viewHolder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition]) }
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: UsersResponseItem)
    }
    override fun getItemCount() = listUser.size
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemName: TextView = view.findViewById(R.id.tvItemName)
        val tvUserReal: TextView = view.findViewById(R.id.tvUserReal)
        val imgItemPhoto: ImageView = view.findViewById(R.id.imgItemPhoto)
    }
}