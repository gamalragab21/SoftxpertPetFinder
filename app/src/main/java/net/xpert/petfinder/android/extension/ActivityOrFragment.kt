package net.xpert.petfinder.android.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


val FragmentManager.currentNavigationFragments: MutableList<Fragment>?
    get() = primaryNavigationFragment?.childFragmentManager?.fragments

fun Fragment.replaceFragment(containerId: Int, fragment: Fragment) {
    activity?.supportFragmentManager?.commit {
        replace(containerId, fragment)
    }
}

fun <T : ComponentActivity> Fragment.showActivity(
    activity: Class<T>, args: Bundle? = null, clearAllStack: Boolean = false
) {
    requireActivity().navToActivity(activity, args, clearAllStack)
}

fun Activity.navToActivity(
    destinationActivity: Class<out ComponentActivity>, bundle: Bundle? = null,
    clearStacks: Boolean = false
) {
    val intent = Intent(this, destinationActivity)
    bundle?.let { intent.putExtras(it) }
    startActivity(intent)
    if (clearStacks) finishAffinity()
}

inline fun <reified T : ComponentActivity> Fragment.castToActivity(callback: (T?) -> Unit): T? {
    return if (requireActivity() is T) {
        callback(requireActivity() as T)
        requireActivity() as T
    } else {
        callback(null)
        null
    }
}


/*This called when method onBackClick in activity is called */
fun Fragment.onBackClicked(callback: () -> Unit) {
    requireActivity().onBackClicked(this, callback)
}

fun ComponentActivity.onBackClicked(fragment: Fragment? = null, callback: () -> Unit) {
    val onBackCallback = object : OnBackPressedCallback(fragment?.isDetached?.not() ?: true) {
        override fun handleOnBackPressed() {
            callback.invoke()
        }
    }
    onBackPressedDispatcher.addCallback(this, onBackCallback)
}


// Binding View (Activity - Fragment) on the fly
// usually used in base Fragment/Activity
@Suppress("UNCHECKED_CAST")
fun <B : ViewBinding> LifecycleOwner.bindView(container: ViewGroup? = null): B {
    return if (this is Activity) {
        val inflateMethod = getTClass<B>().getMethod("inflate", LayoutInflater::class.java)
        val invokeLayout = inflateMethod.invoke(null, this.layoutInflater) as B
        this.setContentView(invokeLayout.root)
        invokeLayout
    } else {
        val fragment = this as Fragment
        val inflateMethod = getTClass<B>().getMethod(
            "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
        )
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
        }
        val invoke: B = inflateMethod.invoke(null, layoutInflater, container, false) as B
        invoke
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T : Any> Any.getTClass(): Class<T> {
    val type: Type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
    return type as Class<T>
}

//fun Fragment.setActivityResult(bundle: Bundle? = null, resultCode: Int = Activity.RESULT_OK) {
//    activity?.setResult(resultCode, Intent().apply {
//        if (bundle != null) putExtras(bundle)
//    })
//    activity?.finish()
//}

//fun <T> Fragment.viewLifecycleNullable(): ReadWriteProperty<Fragment, T> =
//    object : ReadWriteProperty<Fragment, T>, DefaultLifecycleObserver {
//
//        private var binding: T? = null
//        private var viewLifecycleOwner: LifecycleOwner? = null
//
//        init {
//            viewLifecycleOwnerLiveData.observe(this@viewLifecycleNullable) { lifecycleOwner ->
//                viewLifecycleOwner?.lifecycle?.removeObserver(this)
//                viewLifecycleOwner = lifecycleOwner.also {
//                    it.lifecycle.addObserver(this)
//                }
//            }
//
//        }
//
//        override fun onDestroy(owner: LifecycleOwner) {
//            super.onDestroy(owner)
//            binding = null
//        }
//
//        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
//            return this.binding!!
//        }
//
//        override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
//            this.binding = value
//        }
//    }
