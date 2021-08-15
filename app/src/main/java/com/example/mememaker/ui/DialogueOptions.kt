package com.example.mememaker.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.mememaker.R
import com.example.mememaker.databinding.DialogOptionsBinding

class DialogueOptions : DialogFragment(R.layout.dialog_options) {

    lateinit var binding: DialogOptionsBinding


    fun getInstance(resource: Int): DialogueOptions {
        val dialog = DialogueOptions()
        val bundle = Bundle()
        bundle.putInt("resource", resource)
        dialog.arguments = bundle
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogOptionsBinding.bind(view)
        val resource = arguments?.getInt("resource")

        binding.ivCreateMemeByAddinText.setOnClickListener {
            dialog?.dismiss()
            findNavController().navigate(
                MemeTemplateFragmentDirections.actionMemeTemplateFragmentToMemeByTextFragment(
                    resource!!, null
                )
            )
        }

        binding.ivCreateMemeByDrawing.setOnClickListener {
            dialog?.dismiss()
            findNavController().navigate(
                MemeTemplateFragmentDirections.actionMemeTemplateFragmentToMemeCreateFragment(
                    resource!!
                )
            )
        }
    }
}