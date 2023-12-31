plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	kotlin("plugin.serialization")
	id("org.jetbrains.kotlin.plugin.parcelize")
	id("com.google.devtools.ksp")
}

android {
	namespace = "com.example.bm_currency_app"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.example.bm_currency_app"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
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
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
		freeCompilerArgs = listOf(
			"-opt-in=androidx.compose.material3.ExperimentalMaterial3Api") +
				freeCompilerArgs
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.3"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {
	val composeVersion = "1.5.4"

	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
	implementation("androidx.activity:activity-compose:1.8.1")
	implementation(platform("androidx.compose:compose-bom:2023.08.00"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest")
	implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

	//////////////////////
	// Kotlin libraries //
	//////////////////////
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

	//////////////////////////
	// Networking libraries //
	//////////////////////////
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-wire:2.9.0")
	implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
	// TODO: This library will eventually be included as official retrofit
	//  library. Keep track on repo to see when this happens and remove this
	//  dependency when it happens and update the retrofit version to the version
	//  that includes Kotlin serialization:
	//  https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter
	implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
	implementation("androidx.test.ext:junit-ktx:1.1.5")
	implementation ("com.squareup.okhttp3:okhttp:4.11.0")

}