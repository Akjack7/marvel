package com.example.marvel.presentation

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        (activity as MainActivity).replaceFragment(fragment, addToBackStack)
    }

    fun showMainLoading(show: Boolean) {
        (activity as MainActivity).showLoading(show)
    }
}