package com.example.enum

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.lang.IllegalArgumentException

enum class JobCueStatus {
    RECEIVED,
    PREPARED,
    FAILED_PREPARE,
    RENDERED,
    FAILED_RENDER;

    companion object {
        fun convertToList(dbData: String?): List<String> {
            var cueJobList = mutableListOf<String>()
            if (dbData != null && dbData != "") {
                cueJobList = dbData.split(",").toMutableList()
            }
            return cueJobList
        }
    }
}

@Converter(autoApply = true)
class JobCueStatusConverter : AttributeConverter<JobCueStatus, String> {
    override fun convertToDatabaseColumn(attribute: JobCueStatus?): String {
        if (attribute == null) {
            throw IllegalArgumentException(" Job Cue Status cannot be null")
        }
        return attribute.toString()
    }

    override fun convertToEntityAttribute(dbData: String?): JobCueStatus {
        if (dbData == null) {
            throw IllegalArgumentException(" Job Cue Status cannot be null")
        }
        return JobCueStatus.valueOf(dbData)
    }
}
