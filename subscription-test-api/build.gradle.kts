
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask

plugins {
    id("org.springframework.boot") version "2.7.1"
    id("com.netflix.dgs.codegen") version "5.1.16"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.subscriptiontest"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release"))
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.github.javafaker:javafaker:1.+")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
    implementation("com.netflix.graphql.dgs:graphql-dgs-subscriptions-websockets-autoconfigure:5.0.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
        jvmTarget = "11"
    }
    dependsOn(tasks.withType<GenerateJavaTask>())
}

tasks.withType<GenerateJavaTask> {
    packageName = "com.jeniusbank.jeniusevolvedmicroservice"
    language = "kotlin"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
