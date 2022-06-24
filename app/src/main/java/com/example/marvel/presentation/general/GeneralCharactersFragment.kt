package com.example.marvel.presentation.general

import Results
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.marvel.R
import com.example.marvel.databinding.FragmentGeneralCharactersBinding
import com.example.marvel.domain.models.Character
import com.example.marvel.presentation.BaseViewModel
import com.example.marvel.presentation.MainActivity
import com.example.marvel.presentation.detail.CharacterDetailFragment
import com.example.marvel.presentation.general.adapter.GeneralCharactersAdapter
import com.example.marvel.viewBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class GeneralCharactersFragment : Fragment(R.layout.fragment_general_characters),
    GeneralCharactersAdapter.Action {

    private val viewModel by sharedViewModel<GeneralCharactersViewModel>()
    private val binding by viewBinding<FragmentGeneralCharactersBinding>()
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
                /*BaseViewModel.LoadingState.Status.RUNNING -> {
                    showLoading(true)
                }
                BaseViewModel.LoadingState.Status.SUCCESS -> {
                    viewModel.data.observe(viewLifecycleOwner, {
                        adapter.items = it.toMutableList()
                    })
                    binding.generalCharactersList.adapter = adapter
                    showLoading(false)
                }
                BaseViewModel.LoadingState.Status.FAILED -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_LONG)
                        .show()
                }*/
                CharactersState.Empty -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), getString(R.string.empty), Toast.LENGTH_LONG)
                        .show()
                }
                CharactersState.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_LONG)
                        .show()
                }
                is CharactersState.Loaded -> {
                    adapter.items = it.data
                    binding.generalCharactersList.adapter = adapter
                    showLoading(false)
                }
                CharactersState.Loading -> showLoading(true)
            }
        }
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.generalCharactersLoading.loadingContainer.visibility = VISIBLE
        } else {
            binding.generalCharactersLoading.loadingContainer.visibility = GONE
        }

    }

    override fun onclick(character: Character) {
        //instead call the service again,we store the clicked character in liveData
       // viewModel.currentCharacter.postValue(character)
        (activity as MainActivity).replaceFragment(CharacterDetailFragment(), true)
    }
}