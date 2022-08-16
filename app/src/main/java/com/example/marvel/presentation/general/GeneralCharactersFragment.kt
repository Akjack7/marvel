package com.example.marvel.presentation.general

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.marvel.R
import com.example.marvel.databinding.FragmentGeneralCharactersBinding
import com.example.marvel.presentation.base.BaseFragment
import com.example.marvel.presentation.detail.CharacterDetailFragment
import com.example.marvel.presentation.general.adapter.GeneralCharactersAdapter
import com.example.marvel.viewBinding
import org.koin.android.viewmodel.ext.android.viewModel

class GeneralCharactersFragment : BaseFragment(R.layout.fragment_general_characters),
    GeneralCharactersAdapter.Action {

    private val viewModel by viewModel<GeneralCharactersViewModel>()
    private val binding by viewBinding(FragmentGeneralCharactersBinding::bind)
    private val adapter: GeneralCharactersAdapter by lazy {
        GeneralCharactersAdapter(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObserver()
    }

    private fun addObserver() {
        viewModel.getCharacters()
        viewModel.allCharactersState.observe(viewLifecycleOwner) {
            when (it) {
                CharactersState.Empty -> Toast.makeText(
                    requireContext(),
                    getString(R.string.empty),
                    Toast.LENGTH_LONG
                ).show().also { showMainLoading(false) }

                CharactersState.Error -> Toast.makeText(
                    requireContext(),
                    getString(R.string.error),
                    Toast.LENGTH_LONG
                ).show().also { showMainLoading(false) }

                is CharactersState.Loaded -> {
                    adapter.items = it.data
                    binding.generalCharactersList.adapter = adapter
                    showMainLoading(false)
                }
                CharactersState.Loading -> showMainLoading(true)
            }
        }
    }

    override fun onclick(id: Int) {
        replaceFragment(CharacterDetailFragment.newInstance(id), true)
    }
}