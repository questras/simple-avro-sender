plugins {
    kotlin("jvm") version "2.0.0"
}

group = "pl.questras"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("tech.allegro.schema.json2avro:converter:0.2.15")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
