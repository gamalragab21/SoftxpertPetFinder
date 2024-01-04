package net.xpert.petfinder.ui.fragments.entryPoint

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import net.xpert.android.helpers.logging.getClassLogger
import net.xpert.android.helpers.properties.ConfigurationKey
import net.xpert.android.helpers.properties.ConfigurationUtil
import net.xpert.core.common.data.model.Resource
import net.xpert.features.getUserTokenUC.data.models.TokenRequest
import net.xpert.features.getUserTokenUC.domain.interactor.GenerateUserTokenUC
import net.xpert.petfinder.android.viewModel.AndroidBaseViewModel
import javax.inject.Inject

@HiltViewModel
class EntryPointVM @Inject constructor(
    context: Application,
    private val generateUserTokenUC: GenerateUserTokenUC,
    private val configurationUtil: ConfigurationUtil
) : AndroidBaseViewModel<EntryPointState>(context) {


    fun generateUserTokenIfIsFirstTimeUsingApp() {
        generateUserTokenUC.invoke(
            viewModelScope, buildTokenRequest()
        ) {
            logger.error("generateUserTokenIfIsFirstTimeUsingApp: $it")
            when (it) {
                is Resource.Failure -> produce(EntryPointState.Failure(it.exception))
                is Resource.Progress -> produce(EntryPointState.Loading(it.loading))
                is Resource.Success -> produce(EntryPointState.NavigateToHomeScreen)
            }
        }
    }

    private fun buildTokenRequest() = TokenRequest(
        configurationUtil.getProperty(ConfigurationKey.API_KEY),
        configurationUtil.getProperty(ConfigurationKey.SECRET_KEY)
    )

    init {
        generateUserTokenIfIsFirstTimeUsingApp()
    }

    companion object {
        private val logger = getClassLogger()
    }
}