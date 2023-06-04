package com.fianku.submissionthree.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Favorite)
    @Delete
    fun delete(note: Favorite)
    @Query("SELECT * from favorite ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Favorite>>
    @Query("SELECT EXISTS (SELECT 1 FROM favorite where login = :login)")
    fun exist(login: String): Boolean
}