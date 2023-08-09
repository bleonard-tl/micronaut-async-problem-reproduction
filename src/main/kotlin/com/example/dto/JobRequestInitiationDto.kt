package com.example.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

@JsonInclude
data class JobRequestInitiationDto(

    @param:JsonProperty(required = true)
    private val jobId: String
) {
    @NotBlank(message = "jobId is required.")
    fun getJobId(): String {
        return this.jobId
    }
}
