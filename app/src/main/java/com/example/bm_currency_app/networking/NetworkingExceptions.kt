package com.example.bm_currency_app.networking

import retrofit2.Response

/**
 * An abstract [RuntimeException] associated with networking.
 *
 * @property response
 *   The [Response] to a network request.
 */
sealed class NetworkingException(
	val response: Response<*>,
	message: String
): RuntimeException(message) {
	/**
	 * The [HttpStatusCode] that describes the nature of the [response].
	 */
	val httpStatusCode: HttpStatusCode
		get() = HttpResponseStatusCode[response.code()]
}

/**
 * A [NetworkingException] that indicates a [Response] was received that was
 * expected to have a [Response.body] but none was included.
 */
class MissingResponseBodyException(
	response: Response<*>,
	message: String = ""
): NetworkingException(response, message)