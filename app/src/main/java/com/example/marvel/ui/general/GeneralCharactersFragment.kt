package com.example.marvel.ui.general

import com.example.marvel.R
import com.example.marvel.ui.base.BaseFragment
import com.example.marvel.ui.general.adapter.GeneralCharactersAdapter

class GeneralCharactersFragment : BaseFragment(R.layout.fragment_general_characters),
    GeneralCharactersAdapter.Action {

    /*private val viewModel by viewModel<MainScreenViewModel>()
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
    }*/
    override fun onclick(id: Int) {
        TODO("Not yet implemented")
    }
}