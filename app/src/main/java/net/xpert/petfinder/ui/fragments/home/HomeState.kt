package net.xpert.petfinder.ui.fragments.home

import net.xpert.core.common.data.model.exception.LeonException
import net.xpert.features.getPetByCategoryUC.domain.models.Animal
import net.xpert.petfinder.android.viewModel.ViewState

sealed class HomeState : ViewState {
    data class Loading(val loading: Boolean) : HomeState()
    data class Failure(val exception: LeonException) : HomeState()
    data class ShowPetData(val animals: List<Animal>) : HomeState()
    data class UpdateNoDataView(val show: Boolean = false, val errorMessage: String?=null) : HomeState()
}