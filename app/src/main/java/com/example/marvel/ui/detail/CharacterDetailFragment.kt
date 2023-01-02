package com.example.marvel.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.data.Resource
import com.example.marvel.databinding.FragmentCharacterDetailBinding
import com.example.marvel.domain.models.Character
import com.example.marvel.ui.base.BaseFragment
import com.example.marvel.utils.SingleEvent
import com.example.marvel.utils.showToast
import com.example.marvel.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.task.data.error.DEFAULT_ERROR
import org.koin.android.viewmodel.ext.android.viewModel


class CharacterDetailFragment : BaseFragment(R.layout.fragment_character_detail) {

    private val viewModel by viewModel<CharacterDetailViewModel>()
    private val binding by viewBinding(FragmentCharacterDetailBinding::bind)

    companion object {
        const val CHARACTER_ID = "character_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(CHARACTER_ID) ?: 0
        loadData(id)
    }

    private fun loadData(id: Int) {
        viewModel.getCharacter(id)
        observeToast(viewModel.showToast)
        viewModel.characterState.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.DataError -> viewModel.showToastMessage(it.errorCode ?: DEFAULT_ERROR)
                    .also { showMainLoading(false) }
                is Resource.Loading -> showMainLoading(true)
                is Resource.Success -> it.data?.let { character ->
                    setData(character).also {
                        showMainLoading(
                            false
                        )
                    }
                }
            }
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun setData(character: Character) {
        with(binding) {
            characterDetailName.text = character.name
            characterDetailDescriptionText.text =
                character.description.ifEmpty { getString(R.string.no_description) }

            Glide
                .with(requireContext())
                .load(character.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.characterDetailImage)

            binding.favoriteImage.apply {
                setImageDrawable(setFavoriteIcon(character.isFavorite))
                setOnClickListener {
                    viewModel.changeFavoriteCharacter(character)
                }
            }
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean): Drawable? {
        return ContextCompat.getDrawable(
            requireContext(),
            if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_no_favorite
        )
    }
}