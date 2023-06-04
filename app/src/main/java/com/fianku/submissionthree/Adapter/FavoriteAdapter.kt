package com.fianku.submissionthree.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fianku.submissionthree.R
import com.fianku.submissionthree.database.Favorite
import com.fianku.submissionthree.databinding.ItemUsersBinding
import com.fianku.submissionthree.helper.FavoriteDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.NoteViewHolder>() {
    private val listNotes = ArrayList<Favorite>()
    fun setListNotes(listNotes: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }
    private lateinit var onUnfollowClickCallback: OnUnfollowClickCallback
    private lateinit var onItemsClickCallback: OnItemsClickCallback

    fun setUnfollowItemClickCallback(onItemClickCallback: OnUnfollowClickCallback) {
        this.onUnfollowClickCallback = onItemClickCallback
    }
    interface OnUnfollowClickCallback {
        fun onUnfollowClicked(data: Favorite)
    }

    fun setOnItemsClickCallback(onItemsClickCallback: OnItemsClickCallback) {
        this.onItemsClickCallback = onItemsClickCallback
    }
    interface OnItemsClickCallback {
        fun onItemsClicked(data: Favorite)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            binding.btnFollow.text = parent.context.getString(R.string.unfollow)
        return NoteViewHolder(binding)
    }
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
        holder.itemView.setOnClickListener { onItemsClickCallback.onItemsClicked(listNotes[holder.adapterPosition]) }
        holder.binding.btnFollow.setOnClickListener { onUnfollowClickCallback.onUnfollowClicked(listNotes[holder.adapterPosition]) }
    }
    override fun getItemCount(): Int {
        return listNotes.size
    }
    inner class NoteViewHolder(val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Favorite) {
            with(binding) {
                tvItemName.text = note.login
                tvUserReal.text = note.url
                Glide.with(imgItemPhoto.context)
                    .load("${note.avatarUrl}")
                    .circleCrop()
                    .into(imgItemPhoto)
            }
        }
    }
}