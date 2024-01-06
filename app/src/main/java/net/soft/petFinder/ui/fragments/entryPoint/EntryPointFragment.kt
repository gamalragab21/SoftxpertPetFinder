package net.soft.petFinder.ui.fragments.entryPoint

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.soft.android.helpers.logging.getClassLogger
import net.soft.petFinder.android.base.BaseFragment
import net.soft.petFinder.android.extension.navigateSafe
import net.soft.petFinder.android.extension.observe
import net.soft.petFinder.android.extension.showSnackBar
import net.soft.petFinder.android.viewModel.CurrentAction
import net.soft.petFinder.databinding.FragmentEntryPointBinding

@AndroidEntryPoint
class EntryPointFragment : BaseFragment<FragmentEntryPointBinding>() {
    private val entryPointVM: EntryPointVM by viewModels()
    override fun onFragmentReady() {}

    override fun subscribeToObservables() {
        entryPointVM.apply {
            observe(viewState) {
                handleUIState(it)
            }
        }
    }

    private fun handleUIState(state: EntryPointState) {
        getClassLogger().error("handleUIState: $state")

        when (state) {
            is EntryPointState.Failure -> handleHttpsStatusCode(state.exception)
            is EntryPointState.Loading -> binding.animationView.isIndeterminate = state.loading
            EntryPointState.NavigateToHomeScreen -> navigateSafe(EntryPointFragmentDirections.actionEntryPointFragmentToHomeFragment())
        }
    }

    override fun onRetryCurrentAction(currentAction: CurrentAction?, message: String) {
        showSnackBar(message) { entryPointVM.generateUserTokenIfLocalTokenNotExistence() }
    }
}