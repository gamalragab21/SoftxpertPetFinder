package net.xpert.petfinder.ui.fragments.entryPoint

import net.xpert.core.common.data.model.exception.LeonException
import net.xpert.petfinder.android.viewModel.ViewState

sealed class EntryPointState : ViewState {
    data class Loading(val loading: Boolean) : EntryPointState()
    data class Failure(val exception: LeonException) : EntryPointState()
    data object NavigateToHomeScreen : EntryPointState()
}