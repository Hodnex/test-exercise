package com.hodnex.testphotoplan.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hodnex.testphotoplan.R
import com.hodnex.testphotoplan.adapter.ImageAdapter
import com.hodnex.testphotoplan.data.Image
import com.hodnex.testphotoplan.databinding.FragmentLocationBinding
import com.hodnex.testphotoplan.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationFragment : Fragment(R.layout.fragment_location), ImageAdapter.OnItemClickListener {

    private val viewModel: LocationViewModel by viewModels()
    private lateinit var binding: FragmentLocationBinding
    private lateinit var imageAdapter: ImageAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageAdapter = ImageAdapter(this)
        binding = FragmentLocationBinding.bind(view)
        val getImage = registerForActivityResult(
            ActivityResultContracts.OpenMultipleDocuments()
        ) {
            viewModel.addImages(it, requireContext())
        }

        val requirePermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    getImage.launch(arrayOf("image/*"))
                } else {
                    Snackbar.make(requireView(), "Don't have permission", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        binding.apply {
            buttonAddImage.setOnClickListener {
                requirePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            recyclerViewImages.apply {
                adapter = imageAdapter
                layoutManager = GridLayoutManager(requireContext(), 3)
            }
            buttonDeleteImage.setOnClickListener {
                buttonDeleteImage.visibility = View.INVISIBLE
                imageAdapter.deletable = false
                viewModel.delete()
                imageAdapter.notifyDataSetChanged()
            }
        }

        viewModel.images.observe(viewLifecycleOwner) {
            imageAdapter.submitList(it)
        }
    }

    override fun onItemLongClick() {
        binding.buttonDeleteImage.visibility = View.VISIBLE
        imageAdapter.deletable = true
        imageAdapter.notifyDataSetChanged()
    }

    override fun onSelectItemClick(image: Image, isSelected: Boolean) {
        viewModel.onItemSelected(image, isSelected)
    }
}