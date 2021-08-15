package com.example.mememaker.ui.internalStrage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mememaker.R
import com.example.mememaker.adapter.PhotoStorageAdapter
import com.example.mememaker.data.InternalStoragePhoto
import com.example.mememaker.databinding.FragmentPhotoSaveInternalstorageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException

class PhotoSaveInternalStroage : Fragment(R.layout.fragment_photo_save_internalstorage),
    PhotoStorageAdapter.InternalStorageAdapterClick {
    lateinit var photoStorageAdapter: PhotoStorageAdapter
    lateinit var binding: FragmentPhotoSaveInternalstorageBinding

    override fun onShareClick(fileName: String, bitmap: Bitmap) {
        val path: String = MediaStore.Images.Media.insertImage(
            requireActivity().contentResolver,
            bitmap,
            "Image I want to share",
            null
        )
        val uri = Uri.parse(path)
        try {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.type = "image/jpeg"
            requireContext().startActivity(Intent.createChooser(shareIntent, "Share"))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onDeleteClick(fileName: String) {
        val isDeleted = deletePhotoFromInternalStorage(fileName)
        loadPhotosFromInternalStorageIntoRecyclerView()
        if (isDeleted) {
            Toast.makeText(requireContext(), "Photo deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deletePhotoFromInternalStorage(fileName: String): Boolean {
        return try {
            requireActivity().deleteFile(fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoSaveInternalstorageBinding.bind(view)
        photoStorageAdapter = PhotoStorageAdapter(this)
        setupInternalStorageRecyclerView()
        loadPhotosFromInternalStorageIntoRecyclerView()
    }

    private fun loadPhotosFromInternalStorageIntoRecyclerView() {
        lifecycleScope.launch {
            val photos = loadphotosfrominternalstorage()
            photoStorageAdapter.submitList(photos)
        }
    }

    private suspend fun loadphotosfrominternalstorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val files = requireContext().filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
                val bytes = it.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bitmap)
            } ?: listOf()
        }
    }

    private fun setupInternalStorageRecyclerView() = binding.rvInternalStoragePhotos.apply {
        adapter = photoStorageAdapter
        layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
    }


}