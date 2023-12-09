// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
	id("com.android.application") version "8.2.0" apply false
	id("org.jetbrains.kotlin.android") version "1.9.0" apply false

	// Kotlin serialization plugin
	//https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.serialization
	kotlin("plugin.serialization") version "1.9.10" apply false

	// Kotlin parcelize plugin
	// https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.parcelize
	id("org.jetbrains.kotlin.plugin.parcelize") version "1.9.10" apply false

	// Kotlin KSP
	id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}