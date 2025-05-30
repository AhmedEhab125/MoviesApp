plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.use_case"
    compileSdk = VersionManger.COMPILE_SDK

    defaultConfig {
        minSdk = VersionManger.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Project dependencies
    implementation(project(Modules.DOMAIN_MODELS))
    implementation(project(Modules.DOMAIN_REPOSITORIES_INTERFACES))

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Paging 3
    implementation(libs.androidx.paging.runtime.ktx)

    // Koin for DI
    implementation(libs.bundles.koin)

    // Testing dependencies
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.paging.testing)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
}