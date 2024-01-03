package net.xpert.petfinder.android.base

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import net.xpert.petfinder.android.extension.castToActivity
import net.xpert.petfinder.android.viewModel.CurrentAction
import net.xpert.android.helpers.logging.getClassLogger
import net.xpert.core.common.data.model.exception.LeonException

abstract class BaseFragment<B : ViewBinding> : BaseViewBinding<B>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentReady()
        subscribeToObservables()
    }

    abstract fun onFragmentReady()

    abstract fun subscribeToObservables()

    abstract fun onRetryCurrentAction(currentAction: CurrentAction?, message: String)

    protected fun handleHttpsStatusCode(
        exception: LeonException, currentAction: CurrentAction? = null,
        onValidateForm: (Pair<String, String>) -> Unit = {}
    ) {
        if (requireActivity() is BaseActivity<*>) {
            castToActivity<BaseActivity<*>> {
                it?.handleHttpsStatusCode(exception, currentAction, onValidateForm)
            }
        } else logger.debug("Cast activity to handle status code")
    }

    override fun onRefreshView() {}

    companion object {
        private val logger = getClassLogger()
    }
}