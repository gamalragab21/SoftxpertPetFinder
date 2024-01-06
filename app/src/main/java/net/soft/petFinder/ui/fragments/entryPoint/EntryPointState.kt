package net.soft.petFinder.ui.fragments.entryPoint

import net.soft.core.common.data.model.exception.LeonException
import net.soft.petFinder.android.viewModel.ViewState

sealed class EntryPointState : ViewState {
    data class Loading(val loading: Boolean) : EntryPointState()
    data class Failure(val exception: LeonException) : EntryPointState()
    data object NavigateToHomeScreen : EntryPointState()
}