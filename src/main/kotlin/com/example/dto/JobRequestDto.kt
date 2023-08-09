package com.example.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import com.example.enum.DemandSource

@JsonInclude
data class JobRequestDto(

    @NotBlank
    @NotNull
    @param:JsonProperty(required = true)
    val configuration: String,

    @NotBlank
    @NotNull
    @param:JsonProperty(required = true)
    val publisherIds: List<JobCueDto>,

    @param:JsonProperty(required = true)
    val createdBy: String,

    val comment: String,

    val demandSource: DemandSource,

    val autoActivateOnComplete: Boolean
)
