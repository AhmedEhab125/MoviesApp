plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.network"
    compileSdk = VersionManger.COMPILE_SDK
    
    defaultConfig {
        minSdk = VersionManger.MIN_SDK
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3\"")
        buildConfigField(
            "String",
            "USER_TOKEN",
            "\"Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1YzQ2OTJkYTZjM2Y5MDE3ODlhZGNhZTJlYzNkNGVhZCIsIm5iZiI6MTc0ODM0NzUxMi4xODcsInN1YiI6IjY4MzVhYTc4ODZhYTU0YzdjMGE4NzM4MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.gnaWwjVz7LXBQ-yj7iqDyaoVDC752SqpCqQYAucGIv8\""
        )

    }

    buildFeatures {
        buildConfig = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.kotlinx.serialization.json.v173)
    implementation(libs.bundles.ktor.client)
    implementation(libs.ktor.client.logging)
    implementation(libs.bundles.koin)
}