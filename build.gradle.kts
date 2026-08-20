plugins {
    id("java")
}

group = "dev.speedslicer"
version = "1.0-SNAPSHOT"

sourceSets {
    named("main") {
        java {
            srcDir("src/core/java")
        }
    }
}
repositories {
    mavenCentral()
}

dependencies {

}
