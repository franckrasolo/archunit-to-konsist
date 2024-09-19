package demo.archunit

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter

interface ArchUnitRules {
    val basePackage: String

    val importedClasses: JavaClasses
        get() = ClassFileImporter().importPackages(basePackage)
}
