package com.example.enum

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.lang.IllegalArgumentException

enum class JobStatus {
    CREATED,
    PROCESSING,
    COMPLETE
}

@Converter(autoApply = true)
class JobStatusConverter : AttributeConverter<JobStatus, String> {
    override fun convertToDatabaseColumn(attribute: JobStatus?): String {
        if (attribute == null) {
            throw IllegalArgumentException("Job Status cannot be null")
        }
        return attribute.toString()
    }

    override fun convertToEntityAttribute(dbData: String?): JobStatus {
        if (dbData == null) {
            throw IllegalArgumentException("Job Status cannot be null")
        }
        return JobStatus.valueOf(dbData)
    }
}
