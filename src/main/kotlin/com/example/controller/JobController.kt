package com.example.controller

import com.example.dto.*
import io.micronaut.core.version.annotation.Version
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import com.example.mapper.JobMapper
import com.example.service.JobService
import java.time.Instant

@Controller
@Version("1")
class JobController(
    private val jobService: JobService,
) {

    @Post("/api/jobs", processes = [MediaType.APPLICATION_JSON])
    fun createJobs(
        @Body
        jobRequestDto: JobRequestDto,
    ): HttpResponse<PayloadSuccessResponseDto<JobResponseDto>> {
        val job = jobService.createJob(jobRequestDto)
        val jobResponseDto = JobMapper.convertJobToJobResponseDto(job)

        return HttpResponse.created(PayloadSuccessResponseDto(jobResponseDto))
    }

    @Post("/api/jobs/initiations", processes = [MediaType.APPLICATION_JSON])
    fun startJob(
        @Body
        jobRequestInitiationDto: JobRequestInitiationDto,
    ): HttpResponse<PayloadSuccessResponseDto<StartJobResponseDto>> {
        val startedAt = Instant.now()
        jobService.startJobWithAllCues(jobRequestInitiationDto.getJobId(), startedAt)

        return HttpResponse.ok(PayloadSuccessResponseDto(StartJobResponseDto(startedAt.toString())))
    }
}
