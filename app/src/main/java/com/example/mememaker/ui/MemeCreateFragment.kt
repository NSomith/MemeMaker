package com.example.mememaker.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mememaker.R
import com.example.mememaker.adapter.ColorPallet
import com.example.mememaker.customsviews.PaintView
import com.example.mememaker.databinding.FragmentMemeCreateBinding
import com.example.mememaker.utils.alertDialogShow
import java.io.IOException
import java.util.*

class MemeCreateFragment : Fragment(R.layout.fragment_meme_create),
    ColorPallet.OnNoteColorAdapterClick {
    lateinit var binding: FragmentMemeCreateBinding
    lateinit var paintview: PaintView
    private val args: MemeCreateFragmentArgs by navArgs()
    private lateinit var adaptercolorpallet: ColorPallet


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMemeCreateBinding.bind(view)
        paintview = requireActivity().findViewById(R.id.paintViewCanvas)
        paintview.init(args.resource)
        initrv()

        binding.ivSaveDraw.setOnClickListener {
            val signature = screenShot(binding.llPaintViewContainer)
            savePhotoToInternalStorage(UUID.randomUUID().toString(), signature!!)
            Toast.makeText(requireContext(), "saved", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        binding.ivClearDraw.setOnClickListener {
            alertDialogShow(requireContext()) {
                paintview.clear()
            }.show()
        }

        binding.ivDrawPen.setOnClickListener {
            binding.cvBrushSize.visibility = View.VISIBLE
        }
        binding.brushSize1.setOnClickListener {
            binding.cvBrushSize.visibility = View.GONE
            paintview.brushSize(5)
        }
        binding.brushSize2.setOnClickListener {
            binding.cvBrushSize.visibility = View.GONE
            paintview.brushSize(10)
        }
        binding.brushSize3.setOnClickListener {
            binding.cvBrushSize.visibility = View.GONE
            paintview.brushSize(30)
        }
        binding.brushSize4.setOnClickListener {
            binding.cvBrushSize.visibility = View.GONE
            paintview.brushSize(40)
        }

    }

    private fun screenShot(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Boolean {
        return try {
            requireActivity().openFileOutput("$filename.jpg", Context.MODE_PRIVATE).use { stream ->
                if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun initrv() {
        val colorList = listOf(
            Color.GREEN,
            Color.RED,
            Color.rgb(200, 100, 120),
            Color.rgb(100, 200, 0),
            Color.LTGRAY,
            Color.CYAN,
            Color.BLACK,
            Color.rgb(10, 130, 200),
            Color.rgb(200, 200, 20),
            Color.rgb(200, 0, 200)
        )
        adaptercolorpallet = ColorPallet(colorList, this)
        binding.rvColor.apply {
            adapter = adaptercolorpallet
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }
    }

    override fun onClick(colorRes: Int) {
        val drawable =
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_baseline_draw_24)
        drawable?.let {
            val wrapperDrawable = DrawableCompat.wrap(it).apply {
                setTint(colorRes)
            }
            binding.ivDrawPen.background = wrapperDrawable
        }
        paintview.pen(colorRes)
    }
}