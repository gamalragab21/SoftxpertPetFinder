package net.xpert.petfinder.android.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import net.xpert.petfinder.R

fun Context.showToastAsLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showToastAsLong(@StringRes resID: Int) {
    Toast.makeText(this, getString(resID), Toast.LENGTH_LONG).show()
}

fun Context.showToastAsShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToastAsShort(@StringRes resID: Int) {
    Toast.makeText(this, getString(resID), Toast.LENGTH_SHORT).show()
}

// -------------------------------------------- Keyboard -------------------------------------------

fun Activity.showKeyboard() =
    WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.ime())

fun Activity.hideKeyboard() =
    WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.ime())

fun Fragment.hideKeyboard() = activity?.hideKeyboard()

fun Fragment.showKeyboard() = activity?.showKeyboard()

// -------------------------------------------- Item Visibility ------------------------------------

fun View.show(show: Boolean = true) {
    if (show) toVisible() else toGone()
}

fun View.showInvisible(show: Boolean = true) {
    if (show) toVisible() else toInvisible()
}

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
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
