pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TriangleZMoviesApp"
include(":app")
include(":core:network")
include(":core:base")
include(":features:movies")
include(":core:ui")
include(":core:cashing")
include(":data:repositories")
include(":data:services")
include(":domain:use_case")
include(":domain:models")
include(":domain:repositories_interfaces")
