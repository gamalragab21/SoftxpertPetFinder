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

fun View.makeForeGroundClickable() {
    val outValue = TypedValue()
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
    foreground = ContextCompat.getDrawable(context, outValue.resourceId)
}

fun View.refreshView() {
    invalidate()
    requestLayout()
}

fun View.getPxFromDimenRes(@DimenRes res: Int) = context.getPxFromDimenRes(res)

fun Context.getPxFromDimenRes(@DimenRes res: Int) = resources.getDimensionPixelSize(res)

fun Context.getDrawableFromRes(@DrawableRes drawable: Int): Drawable? =
    ContextCompat.getDrawable(this, drawable)

fun Context.getColorStateListFromRes(@ColorRes color: Int): ColorStateList {
    return ColorStateList.valueOf(getColorFromRes(color))
}

fun Context.getColorFromRes(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Fragment.showSnackBar(message: String, onRetryClicked: () -> Unit) {
    hideKeyboard()
    Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
        .setAction(R.string.retry) { onRetryClicked() }
        .show()
}

fun View.showSnackBar(message: String, onRetryClicked: () -> Unit) {
    (context as? Activity)?.hideKeyboard()
    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
        .setAction(R.string.retry) { onRetryClicked() }
        .show()
}

fun Fragment.showSnackBar(message: Int, onRetryClicked: () -> Unit) {
    hideKeyboard()
    Snackbar.make(requireView(), getString(message), Snackbar.LENGTH_INDEFINITE)
        .setAction(R.string.retry) { onRetryClicked() }
        .show()
}

fun Fragment.showSnackBar(message: String) {
    hideKeyboard()
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
}

infix fun View.showSnackBar(message: String) {
    (context as? Activity)?.hideKeyboard()
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(message: Int) {
    hideKeyboard()
    Snackbar.make(requireView(), getString(message), Snackbar.LENGTH_SHORT).show()
}

// -------------------------------------------- Resources ------------------------------------------

fun AppCompatActivity.setWindowLayoutDirection(layoutDirection: Int) {
    window.decorView.layoutDirection = layoutDirection
}

@Suppress("DEPRECATION")
inline val Fragment.windowWidth: Int
    @SuppressLint("ObsoleteSdkInt") get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = requireActivity().windowManager.currentWindowMetrics
            val insets = metrics.windowInsets.getInsets(WindowInsets.Type.systemBars())
            metrics.bounds.width() - insets.left - insets.right
        } else {
            val view = requireActivity().window.decorView
            val insets: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val res = WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets, view)
                    .getInsets(WindowInsetsCompat.Type.systemBars())
                return resources.displayMetrics.widthPixels - res.left - res.right
            } else {
                val displayMetrics = DisplayMetrics()
                requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.widthPixels
            }
            return insets
        }
    }

val Context.actionBarSize
    get() = theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        .let { attrs -> attrs.getDimension(0, 0F).toInt().also { attrs.recycle() } }

fun Fragment.getColorFromColorId(colorId:Int): Int {
    return ContextCompat.getColor(requireContext(), colorId)
}
fun showContentBehindTheStatusBar(activity: Activity, view: View) {
    activity.apply {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(view) { root, windowInset ->
            val inset = windowInset.getInsets(WindowInsetsCompat.Type.systemBars())
            root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = inset.left
                bottomMargin = inset.bottom
                rightMargin = inset.right
            }
            WindowInsetsCompat.CONSUMED
        }
    }
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
