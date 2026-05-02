import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

tasks.wrapper {
    gradleVersion = "9.5.0"
    distributionSha256Sum = "a3c4ba4aca8f0075688b9c5b18939fd28e8cb4357c227da5c1d9f38343791439"
    distributionType = Wrapper.DistributionType.ALL
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
}

tasks {
    withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget = JVM_25
            apiVersion = KOTLIN_2_3
            languageVersion = KOTLIN_2_3
            allWarningsAsErrors = true
            freeCompilerArgs.addAll("-Xjsr305=strict", "-progressive")
        }
    }

    withType<Test>().configureEach {
        jvmArgs("-XX:+UseParallelGC")
        systemProperty("java.util.logging.config.file", "$rootDir/junit-logging.properties")
        useJUnitPlatform()

        reports {
            junitXml.required = true
            html.required = true
        }

        testLogging {
            events = setOf(TestLogEvent.FAILED)
            showExceptions = true
            exceptionFormat = TestExceptionFormat.FULL
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(Kotlin.stdlib)

    testImplementation(platform(Testing.junit.bom))
    testImplementation(Testing.junit.jupiter.api)
    testImplementation(Testing.kotest.assertions.core)
    testImplementation("com.tngtech.archunit:archunit-junit5:_")
    testImplementation("com.lemonappdev:konsist:_")

    testRuntimeOnly(Testing.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:_")
}
