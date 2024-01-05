package net.xpert.petfinder.ui.fragments.entryPoint

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.xpert.android.helpers.logging.getClassLogger
import net.xpert.android.helpers.properties.domain.IConfigurationUtil
import net.xpert.core.common.data.model.Resource
import net.xpert.features.getUserTokenUC.data.models.TokenRequest
import net.xpert.features.getUserTokenUC.domain.interactor.CheckUserTokenExistenceUC
import net.xpert.petfinder.android.viewModel.AndroidBaseViewModel
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
            logger.error("generateUserTokenIfLocalTokenNotExistence: $it")
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

    companion object {
        private val logger = getClassLogger()
    }
}