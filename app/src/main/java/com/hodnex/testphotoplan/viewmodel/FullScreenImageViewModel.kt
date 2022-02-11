package com.hodnex.testphotoplan.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hodnex.testphotoplan.data.ImageDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FullScreenImageViewModel @Inject constructor(
    private val state: SavedStateHandle
) : ViewModel() {
    val imageUri  = state.get<String>("image_uri")
}