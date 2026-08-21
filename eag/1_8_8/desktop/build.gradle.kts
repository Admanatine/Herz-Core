group = "net.ada"

sourceSets {
    named("main") {
        java {
            srcDir("src/main/java")
        }
    }
}

dependencies {
    implementation(files("${rootProject.projectDir}/dependencies/1_8_8_u53/desktop.jar"))
    implementation(project(":common"))

}

