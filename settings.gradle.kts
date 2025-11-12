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
<<<<<<< HEAD
    }
}

rootProject.name = "HomeUI_FoodPlannerProject"
include(":app")
 
=======
        
    }
}

rootProject.name = "Smart-Food-Planner"
include(":app")
>>>>>>> 1b0f7fa545ce69c9aa292906525cb436b6327c39
