package com.example.service

import com.example.dto.ManifestCreationMessageDto
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Singleton
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import com.example.enum.JobCueStatus
import com.example.model.Job
import com.example.model.JobCue
import com.example.repository.JobCueRepository

@Singleton
open class JobCueService(
    private val jobCueRepository: JobCueRepository,
) {
    @Transactional
    open fun createJobCuesBatchByPublisher(
        job: Job,
        publisherId: Int,
        placementCueList:
            List<Int>
    ): MutableIterable<JobCue> {
        val cueJobList: MutableList<JobCue> = mutableListOf()

        for (cueId: Int in placementCueList) {
            cueJobList.add(
                JobCue(
                    job.id,
                    cueId,
                    publisherId,
                    JobCueStatus.RECEIVED
                )
            )
        }

        if (cueJobList.isNotEmpty()) {
            jobCueRepository.saveAll(cueJobList)
        }
        return cueJobList
    }

    @Async
    @Transactional
    open fun startCueProcess(job: Job) {
        val jobCues = jobCueRepository.findByJobIdOrderByCreatedOnDesc(job.id)

        val jobCueMessages = mutableListOf<ManifestCreationMessageDto>()

        val batchIds = StringBuilder()
        for ((index, jobCue) in jobCues.withIndex()) {
            batchIds.append(jobCue.jobId.toString() + "-" + jobCue.placementCueId + " ")

            jobCueMessages.add(
                ManifestCreationMessageDto(
                    jobCue.jobId.toString() + "-" + jobCue.id,
                    jobCue.id,
                    jobCue.jobId.toString(),
                    jobCue.placementCueId,
                    jobCue.publisherId,
                    job.configuration
                )
            )

            if (jobCueMessages.size == 10 ||
                (index + 1) >= jobCues.size
            ) {
                LOGGER.info(jobCueMessages.toString())
            }
        }

        if (jobCues.isEmpty()) {
            LOGGER.error("Job has no cues to push to the ManifestCreation queue.")
            return
        }

        LOGGER.info(
            "Job id ${job.id} has completed successfully the job requests " +
                "to the ManifestCreation queue."
        )

        LOGGER.info("All cues have been successfully pushed to the ManifestCreation queue.")

        return
    }

    @Async
    @Transactional
    open fun startCueProcessWithAllCues(job: Job) {
        startCueProcess(job)
    }

    // --------------------------------------------------------------------------------------------

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val LOGGER = LoggerFactory.getLogger(javaClass.enclosingClass)
    }
}
