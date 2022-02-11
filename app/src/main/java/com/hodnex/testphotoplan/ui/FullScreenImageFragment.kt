package com.hodnex.testphotoplan.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hodnex.testphotoplan.R
import com.hodnex.testphotoplan.databinding.FragmentFullScreenImageBinding
import com.hodnex.testphotoplan.viewmodel.FullScreenImageViewModel
import com.igreenwood.loupe.Loupe
import com.igreenwood.loupe.extensions.createLoupe
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenImageFragment : Fragment(R.layout.fragment_full_screen_image) {

    private val viewModel: FullScreenImageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFullScreenImageBinding.bind(view)

        view.setBackgroundColor(Color.TRANSPARENT)

        binding.apply {
            Picasso.get().load(viewModel.imageUri).into(imageViewFullScreen)
            createLoupe(imageViewFullScreen, relativeLayoutFullScreen) { // imageView is your ImageView
                onViewTranslateListener = object : Loupe.OnViewTranslateListener {

                    override fun onStart(view: ImageView) {}

                    override fun onViewTranslate(view: ImageView, amount: Float) {}

                    override fun onRestore(view: ImageView) {}

                    override fun onDismiss(view: ImageView) {
                        findNavController().popBackStack()
                    }
                }

            }
        }
    }
}