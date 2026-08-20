plugins {
    id("java")
}

group = "net.ada"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    dependencies {
        implementation(rootProject)
        implementation("net.lenni0451.classtransform:mixinsdummy:1.15.1")
        implementation("net.lenni0451.classtransform:core:1.15.1")
        implementation(files("${rootProject.projectDir}/dependencies/1_8_8_u53/base.jar"))
        implementation(project(":common"))
    }
}

sourceSets {
    named("main") {
        java {
            srcDir("../../src/core/java")
        }
    }
}
repositories {
    mavenCentral()
}
