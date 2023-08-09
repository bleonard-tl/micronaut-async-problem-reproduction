package com.example.model

import com.example.enum.JobCueStatus
import com.example.enum.JobCueStatusConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "job_cue")
data class JobCue(
    @Column(name = "job_id", nullable = false, updatable = false)
    var jobId: UUID = UUID.fromString("000a000a-0000-0a00-aaaa-000d00000a0a"),

    @Column(name = "placement_cue_id", nullable = true, updatable = false)
    var placementCueId: Int?,

    @Column(name = "publisher_id", nullable = true, updatable = false)
    var publisherId: Int?,

    @Convert(converter = JobCueStatusConverter::class)
    @Column(nullable = false)
    var status: JobCueStatus

) : DateReadable, SingleColumnId<Int> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, insertable = false)
    override var id: Int = 0

    @Column(nullable = false)
    @DateCreated
    override var createdOn: Instant = Instant.MIN

    @Column(nullable = false)
    @DateUpdated
    override var lastModified: Instant = Instant.MIN
}
