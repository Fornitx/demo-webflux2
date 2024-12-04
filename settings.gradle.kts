pluginManagement {
    plugins {
        kotlin("jvm") version settings.extra["kotlin-lang.version"] as String
        kotlin("plugin.spring") version settings.extra["kotlin-lang.version"] as String
        id("org.springframework.boot") version settings.extra["spring-boot.version"] as String
        id("io.spring.dependency-management") version settings.extra["spring-dm.version"] as String
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenCentral()
    }
}

rootProject.name = "demo-webflux2"
