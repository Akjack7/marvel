package com.example.marvel.presentation.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.databinding.FragmentCharacterDetailBinding
import com.example.marvel.domain.models.Character
import com.example.marvel.presentation.BaseFragment
import com.example.marvel.viewBinding
import org.koin.android.viewmodel.ext.android.viewModel


class CharacterDetailFragment : BaseFragment(R.layout.fragment_character_detail) {

    private val viewModel by viewModel<CharacterDetailViewModel>()
    private val binding by viewBinding(FragmentCharacterDetailBinding::bind)

    companion object {
        const val CHARACTER_ID = "character_id"

        @JvmStatic
        fun newInstance(id: Int) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(CHARACTER_ID, id)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(CHARACTER_ID) ?: 0
        loadData(id)
    }

    private fun loadData(id: Int) {
        viewModel.getCharacter(id)
        viewModel.characterState.observe(viewLifecycleOwner) {
            when (it) {
                CharacterState.Error -> Toast.makeText(
                    requireContext(),
                    getString(R.string.error),
                    Toast.LENGTH_LONG
                )
                    .show().also { showMainLoading(false) }
                CharacterState.Loading -> showMainLoading(true)
                is CharacterState.Loaded -> setData(it.data).also { showMainLoading(false) }
            }
        }
    }

    private fun setData(character: Character) {
        with(binding) {
            characterDetailName.text = character.name
            character.description.let { description ->
                characterDetailDescriptionText.text =
                    description.ifEmpty { getString(R.string.no_description) }
            }
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

    private fun setFavoriteIcon(isFavorite : Boolean): Drawable? {
      return ContextCompat.getDrawable(
          requireContext(),
          if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_no_favorite
      )
    }
}