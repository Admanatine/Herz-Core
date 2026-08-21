plugins {
    id("java")
}

group = "net.ada"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    plugins.apply("java")

    dependencies {
        implementation(rootProject)
        implementation("net.lenni0451.classtransform:mixinsdummy:1.15.1")
        implementation("net.lenni0451.classtransform:core:1.15.1")
        implementation(files("${rootProject.projectDir}/dependencies/1_8_8_u53/base.jar"))
    }
}

sourceSets {
    named("main") {
        java {
        }
        resources {
            srcDir("resources/resources")
        }
    }
}
repositories {
    mavenCentral()
}
val compileHPCKG = tasks.register<Zip>("compileHPCKG") {
    group = "build"
    description = "Compile HPCKG"
    dependsOn(
        "build",
        ":common:build",
        ":desktop:build",
        ":teavm-js:build",
        ":teavm-wasm_gc:build"
    )
    from(project(":common").layout.buildDirectory.dir("libs"))
    from(project(":teavm-js").layout.buildDirectory.dir("libs"))
    from(project(":teavm-wasm_gc").layout.buildDirectory.dir("libs"))
    from(project(":desktop").layout.buildDirectory.dir("libs"))
    from(layout.buildDirectory.dir("resources/main"))

    destinationDirectory.set(rootProject.layout.buildDirectory.dir("dist"))
    archiveFileName.set("Herz-Core-1_8.hpckg")
}
val resetBuildFolders = tasks.register("resetBuildFolders") {
    group = "build"
    description = "Reset build folders"
    dependsOn(        "clean",
        ":common:clean",
        ":desktop:clean",
        ":teavm-js:clean",
        ":teavm-wasm_gc:clean",
        )
}