package net.xpert.petfinder.ui.fragments.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.xpert.android.helpers.logging.getClassLogger
import net.xpert.core.common.data.model.Resource
import net.xpert.features.getPetByCategoryUC.data.models.CategoryPetRequest
import net.xpert.features.getPetByCategoryUC.domain.interactor.GetPetByCategoryUC
import net.xpert.features.getPetTypes.domain.enums.PetType
import net.xpert.petfinder.android.viewModel.AndroidBaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    context: Application, private val getPetByCategoryUC: GetPetByCategoryUC
) : AndroidBaseViewModel<HomeState>(context) {

    fun getPetByCategoryType(petType: PetType) {
        getPetByCategoryUC.invoke(
            viewModelScope, CategoryPetRequest(petType)
        ) {
            logger.error("getPetByCategoryType: $it")
            when (it) {
                is Resource.Failure -> produce(HomeState.Failure(it.exception))
                is Resource.Progress -> produce(HomeState.Loading(it.loading))
                is Resource.Success -> produce(HomeState.ShowPetData(it.model))
            }
        }
    }

    companion object {
        private val logger = getClassLogger()
    }
}