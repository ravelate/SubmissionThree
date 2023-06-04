package com.fianku.submissionthree.helper

import androidx.recyclerview.widget.DiffUtil
import com.fianku.submissionthree.database.Favorite

class FavoriteDiffCallback(private val mOldNoteList: List<Favorite>, private val mNewNoteList: List<Favorite>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldNoteList.size
    }
    override fun getNewListSize(): Int {
        return mNewNoteList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldNoteList[oldItemPosition].id == mNewNoteList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldNoteList[oldItemPosition]
        val newEmployee = mNewNoteList[newItemPosition]
        return oldEmployee.login == newEmployee.login && oldEmployee.url == newEmployee.url && oldEmployee.avatarUrl == newEmployee.avatarUrl
    }
}