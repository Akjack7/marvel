package com.example.marvel.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.marvel.ui.MainActivity

open class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    fun showMainLoading(show: Boolean) {
        (activity as MainActivity).showLoading(show)
    }
}