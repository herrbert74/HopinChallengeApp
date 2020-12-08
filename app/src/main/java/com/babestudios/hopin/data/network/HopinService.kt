package com.babestudios.hopin.data.network

import com.babestudios.hopin.model.GetSessionRequest
import com.babestudios.hopin.model.GetStageStatusResponse
import com.babestudios.hopin.model.GetStagesResponse
import com.babestudios.hopin.model.GetTokenResponse
import retrofit2.http.*
import java.util.*

interface HopinService {
    @POST("users/sso")
    suspend fun getSessionToken(
            @Header("Content-Type") contentType: String,
            @Header("Cookie") userToken: String,
            @Body body: GetSessionRequest
    ): GetTokenResponse

    @GET("api/v2/events/{eventId}/stages")
    suspend fun getStages(
            @Header("Authorization") sessionToken: String,
            @Path("eventId") eventId: String?
    ): GetStagesResponse

    @GET("api/v2/events/{eventId}/studio/{uuid}/status")
    suspend fun getStageStatus(
            @Header("Authorization") sessionToken: String,
            @Path("eventId") eventId: String?,
            @Path("uuid") uuid: UUID
    ): GetStageStatusResponse
}