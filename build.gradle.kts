plugins {
    id("java")
    id("idea")
    id("org.jetbrains.kotlin.jvm") version "2.0.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "2.0.10"
    id("org.jetbrains.kotlin.kapt") version "2.0.10"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        name = "mvnpm"
        url = uri("https://repo.mvnpm.org/maven2")
        content {
            includeGroupByRegex("org\\.mvnpm.*")
        }
    }
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

fun DependencyHandlerScope.npm(packageName: String, packageVersion: String, dev: Boolean = false) {
    val namespace = packageName.split('/')
    val sb = StringBuilder()
    sb.append("org.mvnpm")
    for (i in 0 until namespace.size - 1) {
        if (namespace[i].startsWith('@')) {
            sb.append(".at."); sb.append(namespace[i].substring(1))
        } else {
            sb.append('.'); sb.append(namespace[i])
        }
    }
    sb.append(':'); sb.append(namespace.last()); sb.append(':'); sb.append(packageVersion)
    val mvnpmPackageName = sb.toString()
    if (dev) {
        compileOnly(mvnpmPackageName)
    } else {
        runtimeOnly(mvnpmPackageName)
    }
}

fun DependencyHandlerScope.npmDev(packageName: String, packageVersion: String) =
    npm(packageName, packageVersion, true)

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-container-image-jib")
    implementation("io.quarkiverse.itext:quarkus-itext:3.0.7")
    implementation("io.quarkus:quarkus-flyway")
    implementation("org.flywaydb:flyway-database-oracle")
    implementation("io.quarkiverse.web-bundler:quarkus-web-bundler:1.7.2")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-scheduler")
    implementation("io.quarkus:quarkus-mailer")
    implementation("io.quarkus:quarkus-websockets")
    implementation("io.quarkus:quarkus-reactive-oracle-client")
    implementation("io.quarkus:quarkus-jdbc-oracle")
    implementation("io.quarkiverse.renarde:quarkus-renarde:3.0.18")
    implementation("io.quarkiverse.renarde:quarkus-renarde-security:3.0.18")
    implementation("io.quarkus:quarkus-smallrye-jwt-build")
    implementation("io.quarkus:quarkus-smallrye-jwt")
    implementation("io.quarkus:quarkus-security-jpa")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-smallrye-openapi")

    implementation("io.vertx:vertx-lang-kotlin-coroutines:4.5.8")
    implementation("org.jetbrains:annotations:24.1.0")
    implementation("org.jboss.slf4j:slf4j-jboss-logmanager")
    implementation("dnsjava:dnsjava:3.5.2")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")

    implementation("org.apache.poi:poi:5.2.2")
    implementation("org.apache.poi:poi-ooxml:5.2.2") // contains XSSF
    implementation("org.apache.poi:poi-ooxml-full:5.2.2") // contains ooxml-schemas without XSSF
    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.22.0")

    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    kapt("com.github.pozo:mapstruct-kotlin-processor:1.4.0.0")

    testImplementation("org.seleniumhq.selenium:selenium-java:4.25.0")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")

    npmDev("typescript", "5.6.2")
    npmDev("tsx", "4.19.1")
    npmDev("@types/react", "18.3.0")
    npmDev("@types/react-dom", "18.3.0")
    npm("classnames", "2.5.1")
    npm("react-fast-compare", "3.2.2")
    npm("js-tokens", "9.0.0")
    npm("react", "18.3.1")
    npm("react-dom", "18.3.1")
    npm("react-is", "18.3.1")
}

group = "lv.kristianskaneps"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        javaParameters = true
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

//tasks.register<Exec>("imageStart") {
//    group = "quarkus"
//    commandLine(
//        "docker", "run", "-i", "--rm", "-p", "8080:8080",
//        "-e", "QUARKUS_LOG_LEVEL=WARN",
//        "-e", "JAVA_OPTS=-Xmx1024M",
//        "-e", "LANGUAGE=lv_LV:lv",
//        "kristians/autoserviss:latest"
//    )
//}
