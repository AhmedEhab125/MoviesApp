plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.trianglezmoviesapp"
    compileSdk = VersionManger.COMPILE_SDK

    defaultConfig {
        applicationId = "com.example.trianglezmoviesapp"
        minSdk = VersionManger.MIN_SDK
        targetSdk = VersionManger.TARGET_SDK
        versionCode = VersionManger.VERSION_CODE
        versionName = VersionManger.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Project modules
    implementation(project(Modules.CORE_BASE))
    implementation(project(Modules.CORE_NETWORK))
    implementation(project(Modules.CORE_UI))
    implementation(project(Modules.CORE_CASHING))
    implementation(project(Modules.DOMAIN_MODELS))
    implementation(project(Modules.DOMAIN_USE_CASE))
    implementation(project(Modules.DATA_REPOSITORIES))
    implementation(project(Modules.DATA_SERVICES))
    implementation(project(Modules.FEATURES_MOVIES))

    // Koin for DI
    implementation(libs.bundles.koin)

    // Navigation
    implementation(libs.navigation.compose)

    // Serialization
    implementation(libs.kotlinx.serialization.json.v173)
}