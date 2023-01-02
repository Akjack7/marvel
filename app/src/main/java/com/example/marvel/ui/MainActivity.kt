package com.example.marvel.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.marvel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showLoading(show: Boolean) {
        if (show) {
            binding.generalCharactersLoading.loadingContainer.visibility = View.VISIBLE
        } else {
            binding.generalCharactersLoading.loadingContainer.visibility = View.GONE
        }
    }
}