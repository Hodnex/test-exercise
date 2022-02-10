package com.hodnex.testphotoplan.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Query("SELECT * FROM image_table")
    fun getAllImages(): Flow<List<Image>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: Image)

    @Update
    suspend fun update(image: Image)

    @Query("DELETE FROM image_table WHERE isSelected = 1")
    suspend fun deleteMarkedImages()
}