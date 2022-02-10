package com.hodnex.testphotoplan.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hodnex.testphotoplan.data.Image
import com.hodnex.testphotoplan.data.ImageDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val imageDao: ImageDao
) : ViewModel() {

    val images = imageDao.getAllImages().asLiveData()

    fun delete() = viewModelScope.launch {
        imageDao.deleteMarkedImages()
    }

    fun addImages(image: List<Uri>, context: Context) = viewModelScope.launch {
        for (uri in image) {
            context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            imageDao.insert(Image(imageUri = uri.toString()))
        }
    }

    fun onItemSelected(image: Image, selected: Boolean) = viewModelScope.launch {
        imageDao.update(image.copy(isSelected = selected))
    }
}