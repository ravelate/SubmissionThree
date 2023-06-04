package com.fianku.submissionthree.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private var check: Boolean = false

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavDao = db.noteDao()
    }
    fun getAllNotes(): LiveData<List<Favorite>> = mFavDao.getAllNotes()
    fun insert(fav: Favorite): Boolean {
        fav.login?.let { check = mFavDao.exist(it) }
        if (check){
            return false
        }else {
            executorService.execute { mFavDao.insert(fav) }
            return true
        }
    }
    fun delete(fav: Favorite) {
        executorService.execute { mFavDao.delete(fav) }
    }
}