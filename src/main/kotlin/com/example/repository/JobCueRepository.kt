package com.example.repository

import com.example.model.JobCue
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import java.util.UUID

@Repository
abstract class JobCueRepository : PageableRepository<JobCue, Int> {
    abstract fun findByJobIdOrderByCreatedOnDesc(jobId: UUID): List<JobCue>
}
