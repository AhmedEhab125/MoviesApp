plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.repositories"
    compileSdk = VersionManger.COMPILE_SDK

    defaultConfig {
        minSdk = VersionManger.MIN_SDK
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Paging 3
    implementation(libs.androidx.paging.runtime.ktx)

    // Koin for DI
    implementation(libs.bundles.koin)

    // Project dependencies
    implementation(project(Modules.DOMAIN_MODELS))
    implementation(project(Modules.CORE_CASHING))
    implementation(project(Modules.DOMAIN_REPOSITORIES_INTERFACES))
    implementation(project(Modules.DATA_SERVICES))
}