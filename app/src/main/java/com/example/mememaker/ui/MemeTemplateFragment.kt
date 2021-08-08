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
import com.example.mememaker.R
import com.example.mememaker.databinding.FragmentMemeTemplateBinding
import com.theartofdev.edmodo.cropper.CropImage

class MemeTemplateFragment:Fragment(R.layout.fragment_meme_template) {

    private lateinit var binding: FragmentMemeTemplateBinding
    private lateinit var imagepickerlauncher:ActivityResultLauncher<Any?>
//    private lateinit var recylerview:

    private val imagepickercontract = object : ActivityResultContract<Any?, Uri?>(){
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

        binding.fabComposeNewMeme.setOnClickListener {
            imagepickerlauncher.launch(null)
        }

        imagepickerlauncher = registerForActivityResult(imagepickercontract){
            if(it==null){
                Toast.makeText(requireContext(), "no image selected", Toast.LENGTH_SHORT).show()
            }else{
                Log.d("somi",it.toString())
//                findNavController().navigate(MemeTemplateFragmentDirections.actionMemeTemplateFragmentToMemeCreateByTextFragment(
//                    -1,it.toString()
//                ))
            }
        }
    }
}
