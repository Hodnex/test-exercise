package com.hodnex.testphotoplan.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Image::class], version = 1)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao
}