plugins {
    java
    `java-library`
    application

    id("org.openjfx.javafxplugin") version "0.0.13"
}

repositories {
    mavenCentral()
}

application {
    // Define the main class for the application.
    mainClass.set("it.zammarchi.briscola.server.WrapperLauncher")
}

tasks.getByName<JavaExec>("run") {
    standardInput = System.`in`
    if (project.hasProperty("port")) {
        args(project.property("port"))
    }
}

dependencies {
    api("io.javalin:javalin:5.1.3")

    api(project(":common"))

    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("org.slf4j:slf4j-simple:2.0.3")

    annotationProcessor("io.javalin.community.openapi:openapi-annotation-processor:5.1.3")

    implementation("io.javalin.community.openapi:javalin-openapi-plugin:5.1.3")
    implementation("io.javalin.community.openapi:javalin-swagger-plugin:5.1.3")
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml", "javafx.base", "javafx.graphics", "javafx.media")
}
