package com.example.bm_currency_app.networking

import kotlinx.serialization.Serializable
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * The common abstract type for Fixer server API responses. It is necessary
 * that all API responses be a subclass of this class in order for
 * deserialization of an API response to work using the [Converter.Factory]
 * created using [ServerApiJson] that is supplied to the
 * [Retrofit.Builder][Retrofit.Builder.addConverterFactory]
 * during the creation of the [Retrofit] in the [FixerApiFactory] which is
 * used to manage API communication with the Fixer server.
 *
 * When creating a new subtype of [ApiResponse] it is required that all direct
 * subtypes are sealed classes. These sealed classes must form a unified type
 * hierarchy for a specific response type for a specific [ApiResponseFactory].
 */
@Serializable
abstract class ApiResponse {
	/**
	 * The [HttpResponseStatusCode] that is associated with this [ApiResponse].
	 * This provides
	 */
	abstract val httpResponseStatusCode: HttpResponseStatusCode
}