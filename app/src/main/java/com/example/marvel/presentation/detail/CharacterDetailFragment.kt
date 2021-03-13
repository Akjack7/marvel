package com.example.marvel.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.databinding.FragmentCharacterDetailBinding
import com.example.marvel.presentation.general.GeneralCharactersViewModel
import com.example.marvel.viewBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel


class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {

    private val viewModel by sharedViewModel<GeneralCharactersViewModel>()
    private val binding by viewBinding<FragmentCharacterDetailBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        viewModel.getCurrentCharacter().observe(viewLifecycleOwner, {
            with(binding) {
                characterDetailName.text = it.name
                it.description.let {
                    characterDetailDescriptionText.text =
                        if (it.isNotEmpty()) it else getString(R.string.no_description)
                }
                val imagePath =
                    it.thumbnail.path + "/landscape_xlarge." + it.thumbnail.extension
                Glide
                    .with(requireContext())
                    .load(imagePath.replace("http", "https"))
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.characterDetailImage)
            }
        })
    }
}