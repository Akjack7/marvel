package com.example.marvel.presentation.general

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.marvel.R
import org.koin.android.viewmodel.ext.android.viewModel

class GeneralCharactersFragment : Fragment(R.layout.fragment_general_characters){

    private val viewModel by viewModel<GeneralCharactersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCharacters()
    }

}