package com.teumteum.teumteum.presentation.signup.character

import android.os.Bundle
import android.view.View
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment
    : BindingFragment<FragmentCharacterBinding>(R.layout.fragment_character) {

    private lateinit var adapter: CharacterListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCharacterView()
    }

    private fun initCharacterView() {
        adapter = CharacterListAdapter {
            binding.ivCharacter.setImageResource(it.second)
            binding.ivCharacter.imageTintList = null
        }

        binding.rvCharacter.adapter = adapter
    }

    companion object {
    }
}