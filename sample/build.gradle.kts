plugins {
    kotlin("jvm")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib:1.5.30")

    api("org.slf4j:slf4j-api:1.7.25")
    api("ch.qos.logback:logback-site:1.3.0-alpha5")

    implementation(project(":kotlin-property-delegate-extensions"))
}
