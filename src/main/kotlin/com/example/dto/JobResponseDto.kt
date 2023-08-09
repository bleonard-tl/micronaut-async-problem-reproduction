package com.example.dto

import com.example.enum.JobStatus
import com.example.enum.DemandSource
import java.util.UUID

data class JobResponseDto(
    val jobId: UUID,
    val comment: String,
    val status: JobStatus,
    val activatedAt: String,
    val configuration: String,
    val publisherIds: List<Int>,
    val createdBy: String,
    val createdOn: String,
    val lastModified: String,
    val startedAt: String,
    val demandSource: DemandSource,
    val autoActivateOnComplete: Boolean
)
