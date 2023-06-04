package com.fianku.submissionthree.ui.fav

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fianku.submissionthree.database.Favorite
import com.fianku.submissionthree.database.FavoriteRepository

class FavoriteViewModel (application: Application) : ViewModel() {
    private val mFavRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllFav(): LiveData<List<Favorite>> = mFavRepository.getAllNotes()
    fun delete(fav: Favorite) {
        mFavRepository.delete(fav)
    }
}