import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

tasks.wrapper {
    gradleVersion = "9.3.0"
    distributionSha256Sum = "046f36af261f2c6ed09eef06bf25b93d1f20d5220991bb8a3f08fd5fb6f6629a"
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
            apiVersion = KOTLIN_2_2
            languageVersion = KOTLIN_2_2
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
