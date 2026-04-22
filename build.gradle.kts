import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "2.1.10"
}

repositories {
    mavenCentral()
}

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(21)) }
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
    }
}

// 1. Task for Java
tasks.register<JavaExec>("execute") {
    group = "application"
    dependsOn("compileJava")
    val scriptProperty = providers.gradleProperty("script")
    mainClass.set(scriptProperty)
    
    val javaClasses = layout.buildDirectory.dir("classes/java/main")
    classpath = files(javaClasses) + sourceSets.main.get().runtimeClasspath
    
    standardInput = System.`in`
}

// 2. Task for Kotlin
tasks.register<JavaExec>("executeKotlin") {
    group = "application"
    dependsOn("compileKotlin")
    val scriptProperty = providers.gradleProperty("script")
    mainClass.set(scriptProperty)
    
    val kotlinClasses = layout.buildDirectory.dir("classes/kotlin/main")
    classpath = files(kotlinClasses) + sourceSets.main.get().runtimeClasspath
    
    standardInput = System.`in`
}
