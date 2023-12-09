package com.example.bm_currency_app.networking

import retrofit2.Response
import retrofit2.http.Tag

/**
 * A [RuntimeException] thrown when processing a [Response] to a
 * [FixerApiService] function that does not receive an [ApiResponseFactory]
 * parameter with the [@Tag][Tag] annotation.
 */
class ApiResponseFactoryMissingTagException (
	url: String
): RuntimeException(
	"Missing @TAG ApiResponseFactory input for FixerApiService associated " +
		"with url: $url")

/**
 * Declares the interface for providing [ApiResponse]s from [FixerApiService].
 * It is responsible for either:
 *  - deserializing a [ResponseType] in the String [Response.body] returned from
 *   the server according to the [Response.code]
 *  - providing a singleton [ResponseType] for a [Response] without a
 *   [body][Response.body] from the server according to the [Response.code]
 *
 * All [FixerApiService] functions that represent an API endpoint must have a
 * unique corresponding [ApiResponseFactory] type associated with it.
 * The implementers of [ApiResponseFactory] must be singletons and as such
 * created as a Kotlin `object`.
 *
 * The concrete object implementation of [ApiResponseFactory] ***must*** be
 * provided to the corresponding API function as parameter with [@Tag][Tag]
 * annotation. This annotation guarantees that the [ApiResponseFactory] object
 * will be available to the [ApiResponseCall] when it receives the response from
 * the server. [ApiResponseCall.enqueue] uses the object to deserialize and
 * return the [ResponseType]. If object is not passed into the function, or if
 * the [@Tag][Tag] annotation is missing from the parameter,
 * [ApiResponseCall.enqueue] will throw an
 * [ApiResponseFactoryMissingTagException].
 *
 * Additionally the [@Tag][Tag] annotated parameter passed to the
 * [FixerApiService] API function must use the
 * `ApiResponseFactory<ResponseType>` as the input type as request object is
 * keyed as a [Tag] using the [ApiResponseFactory] class name. Using the
 * concrete type of the [ApiResponseFactory], such as
 * [AuthenticationResponseFactory], as the parameter's type will result in the
 * [ApiResponseFactory] object not being found by [ApiResponseCall.enqueue].
 *
 * For example [AuthenticationApiService.login] uses
 * [AuthenticationResponseFactory] as its [@Tag][Tag] annotated
 * [ApiResponseFactory] parameter:
 *
 * ```kotlin
 * @POST("login")
 * suspend fun login(
 * 	@Body loginRequest: LoginRequest,
 * 	@Tag apiResponseFactory: ApiResponseFactory<AuthenticationResponse> =
 * 		AuthenticationResponseFactory
 * ): AuthenticationResponse
 * ```
 *
 * @param ResponseType
 *   The type of [ApiResponse] this [ApiResponseFactory] is expected to produce
 *   as the return type of the associated [FixerApiService] API function.
 */
interface ApiResponseFactory<ResponseType: ApiResponse> {
	/**
	 * Answer a [ResponseType] corresponding to the given
	 * [HttpResponseStatusCode.code] and String [Response.body] or `null` if the
	 * [HttpResponseStatusCode.code] is not associated with any known
	 * [ResponseType].
	 *
	 * This is used in [ApiResponseCall.enqueue] to create [ResponseType] based
	 * the [Response.code]. All [ResponseType] subtypes must be provided by
	 * this function according to their [ApiResponse.httpResponseStatusCode].
	 */
	fun provideResponse (
		responseCode: Int,
		responseBody: String): ResponseType?
}