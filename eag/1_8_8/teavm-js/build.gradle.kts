group = "net.ada"

sourceSets {
    named("main") {
        java {
            srcDir("src/main/java")
        }
    }
}

dependencies {
    implementation(files("${rootProject.projectDir}/dependencies/1_8_8_u53/javascript.jar"))
    implementation(project(":common"))

}
