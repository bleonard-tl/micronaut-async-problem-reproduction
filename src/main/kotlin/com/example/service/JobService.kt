package com.example.service

import com.example.enum.JobStatus
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.inject.Singleton
import jakarta.transaction.Transactional
import com.example.dto.JobCueDto
import com.example.dto.JobRequestDto
import com.example.mapper.JobMapper
import com.example.model.Job
import com.example.repository.JobRepository
import java.time.Instant
import java.util.UUID

@Singleton
open class JobService(
    private val jobRepository: JobRepository,
    private val jobCueService: JobCueService,
) {
    @Transactional
    open fun createJob(jobRequestDto: JobRequestDto): Job {
        val job = JobMapper.convertJobRequestDtoToJob(
            jobRequestDto
        )

        job.publisherIds = getPublisherIds(jobRequestDto.publisherIds)

        val jobResult: Job = jobRepository.save(job)

        processCuesByPublisher(jobResult, jobRequestDto.publisherIds)

        return jobResult
    }

    @Transactional
    open fun startJob(entityKey: String, startedAt: Instant) {
        val job = jobRepository.findById(UUID.fromString(entityKey)).orElseThrow()

        job.startedAt = startedAt
        job.status = JobStatus.PROCESSING
        jobRepository.update(job)

        return jobCueService.startCueProcessWithAllCues(job)
    }

    @Transactional
    open fun startJobWithAllCues(entityKey: String, startedAt: Instant) {
        return startJob(entityKey, startedAt)
    }

    private fun processCuesByPublisher(job: Job, publisherIds: List<JobCueDto>): Map<Int, Int> {
        val cuesByPublisher = HashMap<Int, Int>()

        for (element: JobCueDto in publisherIds) {
            val jobs = jobCueService.createJobCuesBatchByPublisher(
                job,
                element.publisherId,
                element.cueIds
            )
            cuesByPublisher[element.publisherId] = jobs.toList().size
        }
        return cuesByPublisher
    }

    private fun getPublisherIds(publisherIds: List<JobCueDto>): String {
        val publisherNode = jacksonObjectMapper().createObjectNode()
        val publisherList = mutableListOf<Int>()

        for (element: JobCueDto in publisherIds) {
            publisherList.add(element.publisherId)
        }

        publisherNode.put("publisherIds", publisherList.toString())

        return publisherNode.toString()
    }
}
