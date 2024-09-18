import de.fayard.refreshVersions.core.FeatureFlag.GRADLE_UPDATES
import de.fayard.refreshVersions.core.FeatureFlag.VERSIONS_CATALOG

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
    id("de.fayard.refreshVersions") version "0.60.5"
}

refreshVersions {
    featureFlags {
        disable(GRADLE_UPDATES)
        disable(VERSIONS_CATALOG)
    }

    versionsPropertiesFile = File("$rootDir/versions.properties")

    rejectVersionIf {
        @Suppress("UnstableApiUsage")
        candidate.stabilityLevel.isLessStableThan(current.stabilityLevel)
    }
}

gradle.startParameter.isContinueOnFailure = true

rootProject.name = "archunit-to-konsist"
