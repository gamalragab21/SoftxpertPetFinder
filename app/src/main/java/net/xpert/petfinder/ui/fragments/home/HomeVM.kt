package net.xpert.petfinder.ui.fragments.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.xpert.android.helpers.logging.getClassLogger
import net.xpert.android.helpers.properties.domain.IConfigurationUtil
import net.xpert.core.common.data.model.Resource
import net.xpert.core.common.data.model.exception.LeonException
import net.xpert.features.getPetByCategoryUC.data.models.CategoryPetRequest
import net.xpert.features.getPetByCategoryUC.domain.interactor.GetPetByCategoryUC
import net.xpert.features.getPetByCategoryUC.domain.models.Pagination
import net.xpert.features.getPetTypes.domain.enums.PetType
import net.xpert.features.getUserTokenUC.data.models.TokenRequest
import net.xpert.features.getUserTokenUC.domain.interactor.TokenRefreshUC
import net.xpert.petfinder.android.extension.PetFinderString
import net.xpert.petfinder.android.viewModel.AndroidBaseViewModel
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

    fun getPetByCategoryType(petType: PetType,currentPage:Int=1,index:String) {
        selectedPage = currentPage
        logger.error("getPetByCategoryType $index :lastPaginationItem $lastPaginationItem")
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
                is Resource.Success -> it.model.apply{
                    lastPaginationItem = pagination
                    logger.error("getPetByCategoryType $index :pagination $pagination")
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
                        getPetByCategoryType(lastPetTypeSelected,selectedPage,"handleExceptionIfUnauthorized")
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
            getPetByCategoryType(lastPetTypeSelected,currentPage,"loadCurrentPage")
        } else logger.warning("we will ignore this page: $currentPage")
    }

    companion object {
        private val logger = getClassLogger()
    }
}