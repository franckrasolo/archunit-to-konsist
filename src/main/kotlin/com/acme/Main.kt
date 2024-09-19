package com.acme

import com.acme.app.App
import com.acme.domain.hubs.InterpreterHub
import com.acme.domain.model.Document
import com.acme.infra.adapters.inbound.InMemoryTextInverter
import com.acme.infra.adapters.outbound.InMemoryDocumentRepository

fun main(args: Array<String>) {
    val hub = InterpreterHub(
        InMemoryTextInverter(),
        InMemoryDocumentRepository()
    )
    App(hub).process(Document(args.asSequence()))
}
