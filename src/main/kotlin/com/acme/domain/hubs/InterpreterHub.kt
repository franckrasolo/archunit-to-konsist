package com.acme.domain.hubs

import com.acme.domain.model.Document
import com.acme.domain.ports.inbound.Translator
import com.acme.domain.ports.outbound.DocumentRepository

class InterpreterHub(
    private val translator: Translator,
    private val repository: DocumentRepository
) {
    fun translate(document: Document) = document
        .map(translator::translate)
        .also(repository::save)
}
