package demo

import org.junit.platform.commons.annotation.Testable

interface ArchitectureRules {
    val basePackage: String

    @Testable
    fun `domain members do not depend on packages outside of the domain package`()

    @Testable
    fun `domain model members do not have outgoing dependencies`()

    @Testable
    fun `ports do not access the application, adapters or domain hubs`()

    @Testable
    fun `hubs can only access ports, the domain model and other hubs`()

    @Testable
    fun `adapters do not access the application, other adapters or domain hubs`()

    @Testable
    fun `the app can only access hubs and the domain model`()

    // NOTE: the application entry point is allowed to use any layer, if required
}
