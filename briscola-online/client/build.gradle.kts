plugins {
    java
    `java-library`
    application

    id("org.openjfx.javafxplugin") version "0.0.13"
}

application {
    // Define the main class for the application.
    mainClass.set("it.zammarchi.briscola.client.WrapperLauncher")
}

tasks.getByName<JavaExec>("run") {
    standardInput = System.`in`
    if (project.hasProperty("host")) {
        args(project.property("host"))
    }
    if (project.hasProperty("port")) {
        args(project.property("port"))
    }
}

dependencies {
    api(project(":common"))
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml", "javafx.base", "javafx.graphics", "javafx.media")
}
