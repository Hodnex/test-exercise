package com.hodnex.testphotoplan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
data class Image(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUri: String,
    val isSelected: Boolean = false
)