package com.teumteum.teumteum.presentation.signup.character

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentCharacterBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterFragment
    : BindingFragment<FragmentCharacterBinding>(R.layout.fragment_character) {

    private lateinit var adapter: CharacterListAdapter
    private val viewModel by activityViewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        initAdapter()
        checkValidInput()
    }

    private fun initAdapter() {
        adapter = CharacterListAdapter {
            binding.ivCharacter.imageTintList = null
            viewModel.updateCharacterId(it)
            (activity as SignUpActivity).activateNextButton()
        }
        binding.rvCharacter.adapter = adapter
    }

    private fun checkValidInput() {
        lifecycleScope.launch {
            viewModel.characterId.collect { characterId ->
                if (characterId in 0..11)
                {
                    adapter.getCharacterResource(characterId)
                        ?.let {
                            binding.ivCharacter.setImageResource(it)
                            binding.ivCharacter.imageTintList = null
                        }
                    (activity as SignUpActivity).activateNextButton()
                }
                else
                {
                    (activity as SignUpActivity).disableNextButton()
                }
            }
        }
    }

    companion object {
    }
}