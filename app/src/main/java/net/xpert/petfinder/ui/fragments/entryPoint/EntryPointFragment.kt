package net.xpert.petfinder.ui.fragments.entryPoint

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.xpert.android.helpers.logging.getClassLogger
import net.xpert.petfinder.android.base.BaseFragment
import net.xpert.petfinder.android.extension.navigateSafe
import net.xpert.petfinder.android.extension.observe
import net.xpert.petfinder.android.extension.showSnackBar
import net.xpert.petfinder.android.viewModel.CurrentAction
import net.xpert.petfinder.databinding.FragmentEntryPointBinding

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
        showSnackBar(message) { entryPointVM.generateUserTokenIfIsFirstTimeUsingApp() }
    }
}