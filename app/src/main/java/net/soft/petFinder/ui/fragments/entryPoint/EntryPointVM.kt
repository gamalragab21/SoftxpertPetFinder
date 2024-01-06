package net.soft.petFinder.ui.fragments.entryPoint

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.soft.android.helpers.properties.domain.IConfigurationUtil
import net.soft.core.common.data.model.Resource
import net.soft.features.getUserTokenUC.data.models.TokenRequest
import net.soft.features.getUserTokenUC.domain.interactor.CheckUserTokenExistenceUC
import net.soft.petFinder.android.viewModel.AndroidBaseViewModel
import javax.inject.Inject

@HiltViewModel
class EntryPointVM @Inject constructor(
    context: Application,
    private val checkUserTokenExistenceUC: CheckUserTokenExistenceUC,
    private val configurationUtil: IConfigurationUtil
) : AndroidBaseViewModel<EntryPointState>(context) {


    fun generateUserTokenIfLocalTokenNotExistence() {
        checkUserTokenExistenceUC.invoke(
            viewModelScope, TokenRequest.buildTokenRequestFromAssets(configurationUtil)
        ) {
            when (it) {
                is Resource.Failure -> produce(EntryPointState.Failure(it.exception))
                is Resource.Progress -> produce(EntryPointState.Loading(it.loading))
                is Resource.Success -> produce(EntryPointState.NavigateToHomeScreen)
            }
        }
    }

    init {
        generateUserTokenIfLocalTokenNotExistence()
    }
}