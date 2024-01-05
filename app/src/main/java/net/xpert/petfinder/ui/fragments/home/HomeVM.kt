package net.xpert.petfinder.ui.fragments.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.xpert.android.helpers.properties.domain.IConfigurationUtil
import net.xpert.core.common.data.model.Resource
import net.xpert.core.common.data.model.exception.LeonException
import net.xpert.features.getPetByCategoryUC.data.models.CategoryPetRequest
import net.xpert.features.getPetByCategoryUC.domain.interactor.GetPetByCategoryUC
import net.xpert.features.getPetTypes.domain.enums.PetType
import net.xpert.features.getUserTokenUC.data.models.TokenRequest
import net.xpert.features.getUserTokenUC.domain.interactor.GenerateUserTokenUC
import net.xpert.petfinder.android.viewModel.AndroidBaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    context: Application,
    private val getPetByCategoryUC: GetPetByCategoryUC,
    private val generateUserTokenUC: GenerateUserTokenUC,
    private val configurationUtil: IConfigurationUtil
) : AndroidBaseViewModel<HomeState>(context) {

    private var retryLimitCount = 2
    private var lastPetTypeSelected: PetType = PetType.ALL

    fun getPetByCategoryType(petType: PetType) {
        lastPetTypeSelected = petType
        getPetByCategoryUC.invoke(
            viewModelScope, CategoryPetRequest(petType)
        ) {
            when (it) {
                is Resource.Failure -> handleExceptionIfUnauthorized(it.exception)
                is Resource.Progress -> produce(HomeState.Loading(it.loading))
                is Resource.Success -> {
                    produce(HomeState.ShowPetData(it.model))
                    retryLimitCount = 2
                }
            }
        }
    }

    private fun handleExceptionIfUnauthorized(exception: LeonException) {
        when (exception) {
            LeonException.Client.Unauthorized -> {
                if (retryLimitCount > 0) {
                    generateNewUserToken {
                        retryLimitCount--
                        getPetByCategoryType(lastPetTypeSelected)
                    }
                } else {
                    produce(HomeState.Failure(exception))
                }
            }

            else -> produce(HomeState.Failure(exception))
        }
    }

    private fun generateNewUserToken(onSuccess: () -> Unit) {
        generateUserTokenUC.invoke(
            viewModelScope, TokenRequest.buildTokenRequestFromAssets(configurationUtil)
        ) {
            when (it) {
                is Resource.Failure -> produce(HomeState.Failure(it.exception))
                is Resource.Progress -> produce(HomeState.Loading(it.loading))
                is Resource.Success -> onSuccess()
            }
        }
    }
}