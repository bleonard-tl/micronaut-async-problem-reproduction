package com.example.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotEmpty

@JsonInclude
data class JobCueDto(

    @param:JsonProperty(required = true)
    val publisherId: Int,

    @param:JsonProperty(required = true)
    @NotEmpty
    val cueIds: List<Int>
)
