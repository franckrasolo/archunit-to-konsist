package com.acme.domain.ports.inbound

fun interface Translator {

    fun translate(text: String): String
}
