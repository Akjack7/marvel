package com.example.marvel.presentation.general

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.example.marvel.R
import com.example.marvel.data.Resource
import com.example.marvel.databinding.FragmentGeneralCharactersBinding
import com.example.marvel.presentation.base.BaseFragment
import com.example.marvel.presentation.detail.CharacterDetailFragment
import com.example.marvel.presentation.general.adapter.GeneralCharactersAdapter
import com.example.marvel.utils.SingleEvent
import com.example.marvel.utils.showToast
import com.example.marvel.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.task.data.error.DEFAULT_ERROR
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

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun addObserver() {
        viewModel.allCharactersState.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.DataError -> viewModel.showToastMessage(it.errorCode ?: DEFAULT_ERROR)
                is Resource.Loading -> showMainLoading(true)
                is Resource.Success -> {
                    adapter.items = it.data ?: emptyList()
                    binding.generalCharactersList.adapter = adapter
                    showMainLoading(false)
                }
            }
        }
        observeToast(viewModel.showToast)
    }

    override fun onclick(id: Int) {
        replaceFragment(CharacterDetailFragment.newInstance(id), true)
    }
}