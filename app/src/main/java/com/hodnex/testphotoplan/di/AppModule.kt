package com.hodnex.testphotoplan.di

import android.app.Application
import androidx.room.Room
import com.hodnex.testphotoplan.data.ImageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, ImageDatabase::class.java, "image_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideImageDao(db: ImageDatabase) = db.imageDao()
}