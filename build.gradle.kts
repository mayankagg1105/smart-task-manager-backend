////plugins {
////	id("org.springframework.boot") version "3.5.7"
////	id("io.spring.dependency-management") version "1.1.7"
////    kotlin("jvm") version "1.9.0"
////    kotlin("plugin.spring") version "1.9.0"
////}
////
////group = "com.example"
////version = "0.0.1-SNAPSHOT"
////description = "Demo project for Spring Boot"
////
////java {
////	toolchain {
////		languageVersion = JavaLanguageVersion.of(17)
////	}
////}
////
////repositories {
////	mavenCentral()
////}
////
////dependencies {
////	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
////	implementation("org.springframework.boot:spring-boot-starter-web")
////	testImplementation("org.springframework.boot:spring-boot-starter-test")
////	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//////	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//////	implementation("org.springframework.boot:spring-boot-starter-web")
////
////	// ✅ use this (latest driver)
////	runtimeOnly("com.mysql:mysql-connector-j:9.1.0")
////
////	testImplementation("org.springframework.boot:spring-boot-starter-test")
////	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
////	implementation("com.google.genai:google-genai:0.1.0")
////}
////
////tasks.withType<Test> {
////	useJUnitPlatform()
////}
////
////tasks.bootJar {
////    archiveFileName.set("smarttaskmanager.jar")
////}
////
//
//plugins {
//    id("org.springframework.boot") version "3.5.7"
//    id("io.spring.dependency-management") version "1.1.7"
//    kotlin("jvm") version "1.9.0"
//    kotlin("plugin.spring") version "1.9.0"
//}
//
//group = "com.example"
//version = "0.0.1-SNAPSHOT"
//description = "Demo project for Spring Boot"
//
//// Configure Java 17 Compatibility (Uses server's installed JDK)
//java {
//    sourceCompatibility = JavaVersion.VERSION_17
//    targetCompatibility = JavaVersion.VERSION_17
//}
//
//repositories {
//    mavenCentral()
//}
//
//dependencies {
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.boot:spring-boot-starter-web")
//
//    // Database Driver
//    runtimeOnly("com.mysql:mysql-connector-j:9.1.0")
//
//    // Testing
//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//
//    // AI
//    implementation("com.google.genai:google-genai:0.1.0")
//}
//
//// ✅ FIX: Use explicit class reference and configureEach to fix the syntax error
//tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class as Class<S>).configureEach {
//    kotlinOptions {
//        freeCompilerArgs = listOf("-Xjsr305=strict")
//        jvmTarget = "17"
//    }
//}
//
//tasks.withType(Test::class) {
//    useJUnitPlatform()
//}
//
//tasks.bootJar {
//    archiveFileName.set("smarttaskmanager.jar")
//}

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

// ✅ FIX: Use Cloud Server's Java 17 (No Toolchain download)
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Database Driver
    runtimeOnly("com.mysql:mysql-connector-j:9.1.0")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // AI
    implementation("com.google.genai:google-genai:0.1.0")
}

// ✅ FIX: Correct syntax using '::class.java' to avoid type mismatch
tasks.withType(KotlinCompile::class.java).configureEach {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType(Test::class.java) {
    useJUnitPlatform()
}

tasks.bootJar {
    archiveFileName.set("smarttaskmanager.jar")
}