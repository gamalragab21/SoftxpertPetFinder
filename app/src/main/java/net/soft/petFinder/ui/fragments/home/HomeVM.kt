package net.soft.petFinder.ui.fragments.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.soft.android.helpers.logging.getClassLogger
import net.soft.android.helpers.properties.domain.IConfigurationUtil
import net.soft.core.common.data.model.Resource
import net.soft.core.common.data.model.exception.LeonException
import net.soft.features.getPetByCategoryUC.data.models.CategoryPetRequest
import net.soft.features.getPetByCategoryUC.domain.interactor.GetPetByCategoryUC
import net.soft.features.getPetByCategoryUC.domain.models.Pagination
import net.soft.features.getPetTypes.domain.enums.PetType
import net.soft.features.getUserTokenUC.data.models.TokenRequest
import net.soft.features.getUserTokenUC.domain.interactor.TokenRefreshUC
import net.soft.petFinder.android.extension.PetFinderString
import net.soft.petFinder.android.viewModel.AndroidBaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    context: Application,
    private val getPetByCategoryUC: GetPetByCategoryUC,
    private val generateUserTokenUC: TokenRefreshUC,
    private val configurationUtil: IConfigurationUtil
) : AndroidBaseViewModel<HomeState>(context) {

    private var retryLimitCount = 2
    private var lastPetTypeSelected: PetType = PetType.ALL
    private var lastPaginationItem: Pagination = Pagination(0, 0)
    private var selectedPage: Int = 1

    fun getPetByCategoryType(petType: PetType, currentPage: Int = 1) {
        selectedPage = currentPage
        lastPetTypeSelected = petType
        getPetByCategoryUC.invoke(
            viewModelScope, CategoryPetRequest(petType, selectedPage)
        ) {
            when (it) {
                is Resource.Failure -> {
                    handleExceptionIfUnauthorized(it.exception)
                    produce(
                        HomeState.UpdateNoDataView(
                            lastPaginationItem.currentPage == 1,
                            it.exception.message ?: getString(PetFinderString.unknown_error_occur)
                        )
                    )
                }

                is Resource.Progress -> produce(HomeState.Loading(it.loading))
                is Resource.Success -> it.model.apply {
                    lastPaginationItem = pagination
                    produce(HomeState.ShowPetData(animals))
                    produce(
                        HomeState.UpdateNoDataView(
                            (animals.isEmpty() && lastPaginationItem.currentPage == 1)
                        )
                    )
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
                        getPetByCategoryType(lastPetTypeSelected, selectedPage)
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

    fun loadCurrentPage(currentPage: Int) {
        if (currentPage > lastPaginationItem.currentPage && currentPage <= lastPaginationItem.totalPages) {
            getPetByCategoryType(lastPetTypeSelected, currentPage)
        } else logger.warning("we will ignore this page: $currentPage")
    }

    companion object {
        private val logger = getClassLogger()
    }
}