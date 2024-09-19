package demo.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.DependencyRules
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import demo.ArchitectureRules
import org.junit.jupiter.api.Test

interface ArchitectureRules : ArchitectureRules {

    private val appLayer get() = Layer("app", "$basePackage.app..")
    private val domainLayer get() = Layer("domain", "$basePackage.domain..")
    private val modelLayer get() = Layer("model", "$basePackage.domain.model..")
    private val portsLayer get() = Layer("ports", "$basePackage.domain.ports..")
    private val hubsLayer get() = Layer("hubs", "$basePackage.domain.hubs..")
    private val adaptersLayer get() = Layer("adapters", "$basePackage.infra.adapters..")

    @Test
    override fun `domain model members do not have outgoing dependencies`() =
        rules { modelLayer.dependsOnNothing() }

    @Test
    override fun `domain members do not depend on packages outside of the domain package`() =
        rules { domainLayer.doesNotDependOn(appLayer, adaptersLayer) }

    @Test
    override fun `ports do not access the application, adapters or domain hubs`() =
        rules {
            portsLayer.dependsOn(modelLayer)
            portsLayer.doesNotDependOn(appLayer, adaptersLayer, hubsLayer)
        }

    @Test
    override fun `hubs can only access ports, the domain model and other hubs`() =
        rules {
            hubsLayer.dependsOn(modelLayer, portsLayer)
            hubsLayer.doesNotDependOn(appLayer, adaptersLayer)
        }

    @Test
    override fun `adapters do not access the application, other adapters or domain hubs`() =
        rules {
            adaptersLayer.dependsOn(modelLayer, portsLayer)
            adaptersLayer.doesNotDependOn(appLayer, hubsLayer)
        }

    @Test
    override fun `the app can only access hubs and the domain model`() =
        rules {
            appLayer.dependsOn(hubsLayer, modelLayer)
            appLayer.doesNotDependOn(adaptersLayer, portsLayer)
        }

    private fun rules(dependencyRules: DependencyRules.() -> Unit) =
        Konsist.scopeFromProduction().assertArchitecture(dependencyRules)
}
