package com.example.bm_currency_app.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * The interface that declares the requirements for creating a Retrofit-based
 * networking API.
 */
interface RetrofitApiFactory {
	/** The base URL for this API. */
	val baseUrl: String

	/**
	 * The default [OkHttpClient] used for the creating a [Retrofit].
	 */
	val okHttpClient: OkHttpClient

	// TODO RAA both jsonRetrofit and jsonStringMixedRetrofit are unused; we may
	//  want to remove them and thus no longer need the dependency:
	//  com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0

	/**
	 * Answer a [Retrofit] for handling [ServerApiJson]-based API using the provided
	 * [OkHttpClient]. Uses the [okHttpClient] by default.
	 */
	fun jsonRetrofit (
		okHttpClient: OkHttpClient = this.okHttpClient,
		json: Json = Json { ignoreUnknownKeys = true }
	): Retrofit =
		 Retrofit.Builder()
			 .baseUrl(baseUrl)
			 .client(okHttpClient)
			 .addConverterFactory(
				 json.asConverterFactory("application/json".toMediaType()))
			 .build()

	/**
	 * Answer a [Retrofit] for handling a mix of the provided [Json] for
	 * JSON-based API and String based API using the provided [OkHttpClient].
	 * This allows for handling API messages (request/response) as either JSON
	 * or Strings.
	 *
	 * Uses the [okHttpClient] by default.
	 */
	fun jsonStringMixedRetrofit (
		okHttpClient: OkHttpClient = this.okHttpClient,
		json: Json = Json { ignoreUnknownKeys = true }
	): Retrofit =
		Retrofit.Builder()
			.baseUrl(baseUrl)
			.client(okHttpClient)
			// String support
			.addConverterFactory(ScalarsConverterFactory.create())
			// JSON support
			.addConverterFactory(
				json.asConverterFactory("application/json".toMediaType()))
			.build()
}