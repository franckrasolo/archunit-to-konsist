package com.acme.app

import com.acme.domain.hubs.InterpreterHub
import com.acme.domain.model.Document

class App(
    private val hub: InterpreterHub,
    private val observe: (Document) -> Unit = {}
) {
    fun process(document: Document) {
        observe(hub.translate(document))
    }
}
