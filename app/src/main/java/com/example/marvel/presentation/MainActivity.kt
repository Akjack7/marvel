package com.example.marvel.presentation

import android.os.Bundle
import com.example.marvel.R
import com.example.marvel.presentation.general.GeneralCharactersFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        replaceFragment(GeneralCharactersFragment())
    }
}