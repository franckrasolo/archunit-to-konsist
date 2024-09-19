package com.acme.domain.model

data class Document(private val lines: Sequence<String>) {

    fun map(transform: (String) -> String) = Document(lines.map(transform))
}
