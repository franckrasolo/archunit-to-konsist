import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

tasks.wrapper {
    gradleVersion = "8.10.2"
    distributionSha256Sum = "2ab88d6de2c23e6adae7363ae6e29cbdd2a709e992929b48b6530fd0c7133bd6"
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
            apiVersion = KOTLIN_2_0
            languageVersion = KOTLIN_2_0
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
