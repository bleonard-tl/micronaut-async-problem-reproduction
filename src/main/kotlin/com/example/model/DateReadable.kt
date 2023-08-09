package com.example.model

import java.time.Instant

interface DateReadable {
    val createdOn: Instant?
    val lastModified: Instant?
}
