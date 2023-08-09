package com.example.dto

data class ManifestCreationMessageDto(
    val id: String,
    val jobCueId: Int,
    val jobId: String,
    val placementCueId: Int?,
    val publisherId: Int?,
    val configuration: String
) : QueueMessageDto(MESSAGE_TYPE, id) {

    companion object {
        @JvmStatic
        val MESSAGE_TYPE = "manifest.creation"
    }
}
