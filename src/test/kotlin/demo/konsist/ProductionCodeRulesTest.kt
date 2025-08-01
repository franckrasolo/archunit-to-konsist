package demo.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

class ProductionCodeRulesTest : ArchitectureRules, NamingRules {
    override val basePackage: String = "com.acme"

    override val appLayer = Layer("app", "$basePackage.app..")
    override val domainLayer = Layer("domain", "$basePackage.domain..")
    override val modelLayer = Layer("model", "$basePackage.domain.model..")
    override val portsLayer = Layer("ports", "$basePackage.domain.ports..")
    override val hubsLayer = Layer("hubs", "$basePackage.domain.hubs..")
    override val adaptersLayer = Layer("adapters", "$basePackage.infra.adapters..")

    @Test
    fun `classes with 'UseCase' suffix should reside in 'usecase' package`() {
        Konsist.scopeFromProduction()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..usecase..") }
    }
}
