package net.xpert.petfinder.android.base

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.ViewBinding
import net.xpert.petfinder.android.extension.bindView
import net.xpert.petfinder.android.extension.currentNavigationFragments
import net.xpert.petfinder.android.extension.hideKeyboard
import net.xpert.petfinder.android.extension.showToastAsShort
import net.xpert.petfinder.android.viewModel.CurrentAction
import net.xpert.petfinder.R
import net.xpert.core.common.data.model.exception.LeonException

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    private var _binding: B? = null
    protected val binding: B get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        _binding = bindView()
        setContentView(binding.root)
        viewInit()
        onActivityReady(savedInstanceState)
    }

    abstract fun onActivityReady(savedInstanceState: Bundle?)

    abstract fun viewInit()

    open fun onLoading(loading: Boolean = false) {
        when (loading) {
            true -> closeTouche()
            false -> openTouche()
        }
    }

    private fun closeTouche() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        hideKeyboard()
    }

    private fun openTouche() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun handleHttpsStatusCode(
        exception: LeonException, currentAction: CurrentAction? = null,
        onValidateForm: (Pair<String, String>) -> Unit = {},
    ) {
        when (exception) {
            LeonException.Client.Unauthorized -> {
                // handle Unauthorized
            }

            is LeonException.Client.ResponseValidation ->
                if (exception.errors.isEmpty()) showToastAsShort(
                    exception.message ?: getString(R.string.unknown_error_occur)
                ) else {
                    exception.errors.forEach {
                        onValidateForm.invoke(
                            Pair(it.key, it.value)
                        )
                    }
                }

            is LeonException.Network.Retrial -> {
                supportFragmentManager.currentNavigationFragments?.forEach {
                    (it as BaseFragment<*>).onRetryCurrentAction(
                        currentAction, exception.message ?: getString(exception.messageRes)
                    )
                }
                onRetryCurrentAction(
                    currentAction, exception.message ?: getString(exception.messageRes)
                )
            }

            else -> showToastAsShort(exception.message ?: getString(R.string.unknown_error_occur))

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun onRetryCurrentAction(currentAction: CurrentAction?, message: String)
}