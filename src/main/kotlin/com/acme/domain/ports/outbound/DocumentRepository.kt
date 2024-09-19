package com.acme.domain.ports.outbound

import com.acme.domain.model.Document

fun interface DocumentRepository {

    fun save(document: Document)
}
