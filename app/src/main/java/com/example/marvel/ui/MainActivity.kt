package com.example.marvel.ui

import android.os.Bundle
import android.view.View
import com.example.marvel.databinding.ActivityMainBinding
import com.example.marvel.ui.base.BaseActivity
import com.example.marvel.ui.general.GeneralCharactersFragment

class MainActivity : BaseActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        replaceFragment(GeneralCharactersFragment(), false)
    }

    fun showLoading(show: Boolean) {
        if (show) {
            binding.generalCharactersLoading.loadingContainer.visibility = View.VISIBLE
        } else {
            binding.generalCharactersLoading.loadingContainer.visibility = View.GONE
        }

    }
}