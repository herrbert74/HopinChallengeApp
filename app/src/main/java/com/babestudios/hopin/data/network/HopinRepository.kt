package com.babestudios.hopin.data.network

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTVerificationException
import com.babestudios.hopin.model.GetSessionRequest
import com.babestudios.hopin.model.GetStageStatusResponse
import com.babestudios.hopin.model.GetStagesResponse
import java.util.*
import javax.inject.Singleton


@Singleton
class HopinRepository(private val hopinService: HopinService) : HopinRepositoryContract {
    private var sessionToken: String? = null
    private var eventId: String? = null

    override suspend fun convertUserId(userToken: String, body: GetSessionRequest): Boolean {
        sessionToken = hopinService.getSessionToken("application/json", userToken, body).token
        eventId = decodeSessionToken(sessionToken ?: "")
        return eventId != null
    }

    private fun decodeSessionToken(sessionToken: String): String {
        require(sessionToken.isNotEmpty())
        return try {
            val jwt = JWT.decode(sessionToken)
            jwt.getClaim("event_id").asInt().toString()
        } catch (exception: JWTVerificationException) {
            //Invalid signature/claims
            ""
        }
    }

    override suspend fun getStages(): GetStagesResponse {
        return hopinService.getStages("Bearer $sessionToken", eventId)
    }

    override suspend fun getStageStatus( uuid: UUID): GetStageStatusResponse {
        return hopinService.getStageStatus("Bearer $sessionToken", eventId, uuid)
    }
}