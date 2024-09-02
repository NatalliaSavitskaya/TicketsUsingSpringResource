plugins {
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("io.qameta.allure") version "2.11.2"
}

allure {
    version.set("2.20.0")
    adapter {
        aspectjWeaver.set(true)
    }
}

tasks.test {
    useJUnitPlatform()
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.hibernate.orm:hibernate-core:6.5.2.Final")
    implementation("org.hibernate.orm:hibernate-hikaricp:6.5.2.Final")

    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    implementation("org.postgresql:postgresql:42.7.2")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.4.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.4.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.qameta.allure:allure-junit5:2.20.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
}

tasks.test {
    useJUnitPlatform()
}