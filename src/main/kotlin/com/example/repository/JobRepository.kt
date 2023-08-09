package com.example.repository

import com.example.model.Job
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import java.util.UUID

@Repository
abstract class JobRepository: PageableRepository<Job, UUID>
