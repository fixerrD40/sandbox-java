plugins {
    java
    kotlin("jvm") version "2.1.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("execute") {
    group = "application"
    dependsOn("classes")
    
    val scriptProperty = providers.gradleProperty("script")
    
    mainClass.set(scriptProperty)
    
    classpath = sourceSets["main"].runtimeClasspath
    standardInput = System.`in`

    doFirst {
        if (!scriptProperty.isPresent) {
            throw GradleException("ERROR: You must provide the class. Example:\n" +
                  "./gradlew execute -Pscript=cses.introductory_problems.Permutations")
        }
        println("Starting: ${scriptProperty.get()}")
    }
}
