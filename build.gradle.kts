plugins {
    application
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation(platform("org.seleniumhq.selenium:selenium-bom:4.25.0"))
    implementation("org.seleniumhq.selenium:selenium-java")

    implementation("io.github.bonigarcia:webdrivermanager:5.9.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.twelvemonkeys.imageio:imageio-core:3.0.2")
    implementation("com.twelvemonkeys.imageio:imageio-ico:3.0.2")

    runtimeOnly("org.slf4j:slf4j-simple:2.0.16")
}

application {
    mainClass.set("tr.com.uludem.b4b.Main")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
