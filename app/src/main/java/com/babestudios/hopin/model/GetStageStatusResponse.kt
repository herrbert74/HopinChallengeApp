package com.babestudios.hopin.model

data class GetStageStatusResponse(val broadcasts: List<Broadcast>)

data class Broadcast(val uuid: String, val stream_url: String, val broadcast_type: String)