package com.example.model

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import jakarta.persistence.GenerationType
import com.example.enum.JobStatus
import com.example.enum.JobStatusConverter
import com.example.enum.DemandSource
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "job")
data class Job(

    @Convert(converter = JobStatusConverter::class)
    @Column(nullable = false)
    var status: JobStatus,

    @Column(name = "activated_at")
    var activatedAt: Instant?,

    @Column(name = "started_at")
    var startedAt: Instant?,

    @Column
    var comment: String,

    @Column
    var configuration: String,

    @Column(name = "publisher_ids")
    var publisherIds: String,

    @Column(name = "created_by")
    var createdBy: String,

    @Column(name = "demand_source", nullable = false)
    @Enumerated(EnumType.STRING)
    var demandSource: DemandSource,

    @Column(name = "auto_activate_on_complete", nullable = false)
    var autoActivateOnComplete: Boolean

) : DateReadable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "job_id", insertable = false, nullable = false, updatable = false)
    lateinit var id: UUID

    @Column(nullable = false)
    @DateCreated
    override var createdOn: Instant = Instant.MIN

    @Column(nullable = false)
    @DateUpdated
    override var lastModified: Instant = Instant.MIN
}
