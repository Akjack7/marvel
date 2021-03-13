package com.example.marvel

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
    private val fragment: Fragment,
    klazz: Class<T>
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null
    private val bindMethod = klazz.getMethod("bind", View::class.java)

    init {
        fragment.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE) fun onCreate() {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(ViewLifecycleBinding())
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        binding ?: (bindMethod.invoke(null, thisRef.requireView()) as T).also { binding = it }

    inner class ViewLifecycleBinding : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY) internal fun onDestroy() {
            Log.v(this.javaClass.simpleName, "clear binding of ${fragment.javaClass.simpleName}")
            binding = null
            fragment.lifecycle.removeObserver(this)
        }
    }
}
