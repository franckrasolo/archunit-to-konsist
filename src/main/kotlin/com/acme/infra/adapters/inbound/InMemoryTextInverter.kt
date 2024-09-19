package com.acme.infra.adapters.inbound

import com.acme.domain.ports.inbound.Translator

class InMemoryTextInverter : Translator {

    override fun translate(text: String) = text.reversed()
}
