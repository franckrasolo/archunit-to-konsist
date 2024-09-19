package demo.archunit

import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import demo.ArchitectureRules

interface ArchitectureRules : ArchUnitRules, ArchitectureRules {

    @ArchTest
    override fun `domain model members do not have outgoing dependencies`() {
        noClasses()
            .that()
            .resideInAnyPackage("$basePackage.domain.model..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                "$basePackage.app..",
                "$basePackage.domain.hubs..",
                "$basePackage.domain.ports..",
                "$basePackage.infra.."
            )
            .check(importedClasses)
    }

    @ArchTest
    override fun `domain members do not depend on packages outside of the domain package`() {
        noClasses()
            .that()
            .resideInAPackage("$basePackage.domain..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                "$basePackage.app..",
                "$basePackage.infra.."
            )
            .check(importedClasses)
    }

    @ArchTest
    override fun `ports do not access the application, adapters or domain hubs`() {
        noClasses()
            .that()
            .resideInAPackage("$basePackage.domain.ports..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                "$basePackage.app..",
                "$basePackage.infra.adapters..",
                "$basePackage.domain.hubs.."
            )
            .check(importedClasses)
    }

    @ArchTest
    override fun `hubs can only access ports, the domain model and other hubs`() {
        noClasses()
            .that()
            .resideInAPackage("$basePackage.domain.hubs..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                "$basePackage.app..",
                "$basePackage.infra.."
            )
            .check(importedClasses)
    }

    @ArchTest
    override fun `adapters do not access the application, other adapters or domain hubs`() {
        noClasses()
            .that()
            .resideInAPackage("$basePackage.infra.adapters..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                "$basePackage.adapters..",
                "$basePackage.app..",
                "$basePackage.domain.hubs.."
            )
            .check(importedClasses)
    }

    @ArchTest
    override fun `the app can only access hubs and the domain model`() {
        noClasses()
            .that()
            .resideInAPackage("$basePackage.app..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage(
                "$basePackage.domain.ports..",
                "$basePackage.infra.adapters.."
            )
            .check(importedClasses)
    }
}
