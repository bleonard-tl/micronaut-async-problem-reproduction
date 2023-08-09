package com.example.mapper

import com.example.enum.JobStatus
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.micronaut.core.util.StringUtils
import com.example.dto.JobRequestDto
import com.example.dto.JobResponseDto
import com.example.model.Job

object JobMapper {
    fun convertJobRequestDtoToJob(
        jobRequestDto: JobRequestDto
    ): Job {
        return Job(
            JobStatus.CREATED, null, null, jobRequestDto.comment,
            jobRequestDto.configuration, "", jobRequestDto.createdBy, jobRequestDto.demandSource, jobRequestDto.autoActivateOnComplete
        )
    }

    fun convertJobToJobResponseDto(job: Job): JobResponseDto {
        var publisherIds: List<Int> = listOf()

        if (!StringUtils.isEmpty(job.publisherIds)) {
            val jsonNode = jacksonObjectMapper().readTree(job.publisherIds)
            val content = jsonNode.findValue("publisherIds").toString().replace("\"", "")
            publisherIds = jacksonObjectMapper().readValue(content, IntArray::class.java).asList()
        }
        return JobResponseDto(
            job.id, job.comment, job.status,
            if (job.activatedAt != null) job.activatedAt.toString() else "",
            job.configuration, publisherIds,
            job.createdBy, job.createdOn.toString(),
            job.lastModified.toString(),
            if (job.startedAt != null) job.startedAt.toString() else "",
            job.demandSource,
            job.autoActivateOnComplete
        )
    }
}
