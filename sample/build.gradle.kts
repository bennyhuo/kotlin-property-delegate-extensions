plugins {
    kotlin("jvm")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.30")

    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("ch.qos.logback:logback-classic:1.3.0-alpha10")

    implementation(project(":kotlin-property-delegate-extensions"))
}
