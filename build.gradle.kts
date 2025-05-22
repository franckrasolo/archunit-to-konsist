import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_1
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

tasks.wrapper {
    gradleVersion = "8.14.1"
    distributionSha256Sum = "d7042b3c11565c192041fc8c4703f541b888286404b4f267138c1d094d8ecdca"
    distributionType = Wrapper.DistributionType.ALL
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
}

tasks {
    withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget = JVM_21
            apiVersion = KOTLIN_2_1
            languageVersion = KOTLIN_2_1
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

    testApi(platform(Testing.junit.bom))
    testApi(Testing.junit.jupiter.api)
    testApi(Testing.kotest.assertions.core)
    testApi("com.tngtech.archunit:archunit-junit5:_")
    testApi("com.lemonappdev:konsist:_")

    testRuntimeOnly(Testing.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:_")
}
