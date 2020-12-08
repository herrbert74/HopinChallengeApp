package com.babestudios.hopin

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.babestudios.hopin.data.network.HopinRepositoryContract
import com.babestudios.hopin.model.GetSessionRequest
import com.babestudios.hopin.model.GetStageStatusResponse
import com.babestudios.hopin.model.GetStagesResponse
import com.babestudios.hopin.navigation.HopinNavigator
import kotlinx.coroutines.launch
import java.util.*


class HopinViewModel @ViewModelInject constructor(
        private val hopinRepository: HopinRepositoryContract,
        private val hopinNavigator: HopinNavigator,
) : ViewModel() {

    fun userTokenReceived(cookies: String) {
        val sp = cookies.split("; ")
        val spl = sp.filter { it.startsWith("user.token") }
        if (spl.isNotEmpty()) {
            val userToken = spl[0].removePrefix("user.token=")
            viewModelScope.launch {
                if (convertUserId(userToken))
                    hopinNavigator.mainToStreamer()
                else {
                    //error handling
                }
            }
        }
    }

    private suspend fun convertUserId(userToken: String): Boolean {
        val getSessionRequest = GetSessionRequest("babe-project")
        return hopinRepository.convertUserId(
                "user.token=$userToken",
                getSessionRequest
        )
    }

    suspend fun getStreamUrl(): String? {
        val uuid = getStages().stages[0].uuid
        val status = getStageStatus(uuid)
        val broadcast = status.broadcasts.filter { it.broadcast_type == "mixer" }.getOrNull(0)
        return broadcast?.stream_url
    }

    private suspend fun getStages(): GetStagesResponse {
        return hopinRepository.getStages()
    }

    private suspend fun getStageStatus(uuid: String): GetStageStatusResponse {
        return hopinRepository.getStageStatus(
                UUID.fromString(uuid)
        )
    }

    fun bindNavController(navController: NavController) {
        hopinNavigator.bind(navController)
    }
}