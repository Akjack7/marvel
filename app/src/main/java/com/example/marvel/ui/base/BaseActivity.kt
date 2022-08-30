package com.example.marvel.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.marvel.R

open class BaseActivity : AppCompatActivity() {

    fun replaceFragment(fragment: Fragment, backStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        if (backStack) transaction.addToBackStack(null)
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}