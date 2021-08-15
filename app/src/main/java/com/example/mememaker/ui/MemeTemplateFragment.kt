package com.example.mememaker.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mememaker.R
import com.example.mememaker.adapter.TemplateAdapter
import com.example.mememaker.databinding.FragmentMemeTemplateBinding
import com.example.mememaker.utils.TemplateList.TEMPLATE_LIST
import com.theartofdev.edmodo.cropper.CropImage
import  com.example.mememaker.ui.DialogueOptions

class MemeTemplateFragment : Fragment(R.layout.fragment_meme_template),
    TemplateAdapter.OnAdapterTemplateListener {

    private lateinit var binding: FragmentMemeTemplateBinding
    private lateinit var imagepickerlauncher: ActivityResultLauncher<Any?>
    private lateinit var recylerview: TemplateAdapter

    private val imagepickercontract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity().getIntent(requireContext())
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMemeTemplateBinding.bind(view)
        setrv()
        binding.fabComposeNewMeme.setOnClickListener {
            imagepickerlauncher.launch(null)
        }

        imagepickerlauncher = registerForActivityResult(imagepickercontract) {
            if (it == null) {
                Toast.makeText(requireContext(), "no image selected", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(
                    MemeTemplateFragmentDirections.actionMemeTemplateFragmentToMemeByTextFragment(
                        -1,
                        it.toString()
                    )
                )
            }
        }
    }

    private fun setrv() {
        recylerview = TemplateAdapter(TEMPLATE_LIST, this)
        binding.rvMemeTemplate.apply {
            adapter = recylerview
            addOnScrollListener(onScrollListener)
        }
    }

    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 10 && binding.fabComposeNewMeme.isExtended) {
                binding.fabComposeNewMeme.shrink()
            }
            if (dy < -10 && !binding.fabComposeNewMeme.isExtended) {
                binding.fabComposeNewMeme.extend()
            }
            if (!recyclerView.canScrollVertically(-1)) {// Scroll down is +1, up is -1
                binding.fabComposeNewMeme.extend()
            }
        }
    }

    override fun OnAdapterClick(resource: Int) {
        DialogueOptions().getInstance(resource).show(childFragmentManager, "dialog")
    }
}
