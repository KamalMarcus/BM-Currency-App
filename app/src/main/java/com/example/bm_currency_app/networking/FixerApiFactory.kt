package com.example.bm_currency_app.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * A [RetrofitApiFactory] for accessing the Fixer server API.
 *
 * @param ApiService
 *   The [FixerApiService] type associated with this [FixerApiFactory].
 */
interface FixerApiFactory<ApiService : FixerApiService> : RetrofitApiFactory {
	/**
	 * The [ApiService] associated with this [FixerApiFactory].
	 */
	val apiService: ApiService

	/**
	 * `true` indicates the [CustomHttpLoggingInterceptor] should capture
	 * request/response body in the log; `false` indicates it should not.
	 */
	val logHttpBody: Boolean get() = true

	/**
	 * The [Interceptor] used to intercept Fixer network API HTTP messages
	 * and add [headers][CommonApiHttpRequestHeader].
	 */
	val fixerHeadersInterceptor: Interceptor
		get() =
			Interceptor { chain ->
				val request = chain.request()
				chain.proceed(
					request.newBuilder().build()
				)
			}

	override val baseUrl: String
		get() = "http://data.fixer.io/api/"

	override val okHttpClient: OkHttpClient
		get() =
			OkHttpClient.Builder()
				.addInterceptor(fixerHeadersInterceptor)
				.addInterceptor(CustomHttpLoggingInterceptor(logHttpBody))
				.connectTimeout(20, TimeUnit.SECONDS)
				.readTimeout(90, TimeUnit.SECONDS)
				.build()

	/**
	 * Answer a [Retrofit] for handling [ServerApiJson]-based API using the provided
	 * [OkHttpClient]. Uses the [okHttpClient] by default.
	 */
	fun serverApiRetrofit(
		okHttpClient: OkHttpClient = this.okHttpClient
	): Retrofit =
		Retrofit.Builder()
			.baseUrl(baseUrl)
			.client(okHttpClient)
			.addCallAdapterFactory(ApiResponseCallAdapterFactory)
			// String support
			.addConverterFactory(ScalarsConverterFactory.create())
			// JSON support
			.addConverterFactory(
				ServerApiJson.asConverterFactory(
					"application/json".toMediaType()
				)
			)
			.build()
}