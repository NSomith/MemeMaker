package com.example.mememaker.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mememaker.R
import com.example.mememaker.databinding.FragmentMemeCreateBinding

class MemeCreateFragment:Fragment(R.layout.fragment_meme_create) {
    lateinit var binding:FragmentMemeCreateBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMemeCreateBinding.bind(view)
        
    }
}