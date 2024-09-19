package com.acme.infra.adapters.outbound

import com.acme.domain.model.Document
import com.acme.domain.ports.outbound.DocumentRepository
import kotlin.collections.mutableListOf

class InMemoryDocumentRepository : DocumentRepository {
    private val documents = mutableListOf<Document>()

    override fun save(document: Document) {
        documents.add(document)
    }
}
