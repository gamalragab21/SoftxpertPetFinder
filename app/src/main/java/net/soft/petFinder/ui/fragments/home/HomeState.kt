package net.soft.petFinder.ui.fragments.home

import net.soft.core.common.data.model.exception.LeonException
import net.soft.features.getPetByCategoryUC.domain.models.Animal
import net.soft.petFinder.android.viewModel.ViewState

sealed class HomeState : ViewState {
    data class Loading(val loading: Boolean) : HomeState()
    data class Failure(val exception: LeonException) : HomeState()
    data class ShowPetData(val animals: List<Animal>) : HomeState()
    data class UpdateNoDataView(val show: Boolean = false, val errorMessage: String? = null) :
        HomeState()
}