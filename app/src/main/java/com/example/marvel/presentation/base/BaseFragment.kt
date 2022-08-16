package com.example.marvel.presentation.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.marvel.presentation.MainActivity

open class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        (activity as MainActivity).replaceFragment(fragment, addToBackStack)
    }

    fun showMainLoading(show: Boolean) {
        (activity as MainActivity).showLoading(show)
    }
}