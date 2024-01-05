package net.soft.petFinder.android.extension

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import net.soft.petFinder.R

fun Context.showToastAsShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// -------------------------------------------- Keyboard -------------------------------------------
fun Activity.hideKeyboard() =
    WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.ime())

fun Fragment.hideKeyboard() = activity?.hideKeyboard()

// -------------------------------------------- Item Visibility ------------------------------------

fun View.show(show: Boolean = true) {
    if (show) toVisible() else toGone()
}

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

// -------------------------------------------- StatusBar ------------------------------------------

fun Activity.hideStatusBars() {
    WindowInsetsControllerCompat(
        window, window.decorView
    ).hide(WindowInsetsCompat.Type.statusBars())
}

fun Activity.showStatusBars() {
    WindowInsetsControllerCompat(
        window, window.decorView
    ).show(WindowInsetsCompat.Type.statusBars())
}

// -------------------------------------------- Resources ------------------------------------------
fun Context.getDrawableFromRes(@DrawableRes drawable: Int): Drawable? =
    ContextCompat.getDrawable(this, drawable)

fun Context.getColorFromRes(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Fragment.showSnackBar(message: String, onRetryClicked: () -> Unit) {
    hideKeyboard()
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        .setAction(R.string.retry) { onRetryClicked() }
        .show()
}

fun RecyclerView.init(
    animator: RecyclerView.ItemAnimator? = null,
    nestedScroll: Boolean = false,
    fixedSize: Boolean = true,
    lm: RecyclerView.LayoutManager = LinearLayoutManager(context),
) = apply {
    itemAnimator = animator
    isNestedScrollingEnabled = nestedScroll
    setHasFixedSize(fixedSize)
    layoutManager = lm
}
