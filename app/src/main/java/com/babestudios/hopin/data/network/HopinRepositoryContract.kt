package com.babestudios.hopin.data.network

import com.babestudios.hopin.model.GetSessionRequest
import com.babestudios.hopin.model.GetStageStatusResponse
import com.babestudios.hopin.model.GetStagesResponse
import com.babestudios.hopin.model.GetTokenResponse
import com.github.michaelbull.result.Result
import java.util.*

interface HopinRepositoryContract {
    suspend fun convertUserId(userToken: String, body: GetSessionRequest): Boolean
    suspend fun getStages(): GetStagesResponse
    suspend fun getStageStatus(uuid: UUID): GetStageStatusResponse
}