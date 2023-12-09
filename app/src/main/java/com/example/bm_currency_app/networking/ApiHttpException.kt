package com.example.bm_currency_app.networking

import retrofit2.HttpException
import retrofit2.Response

/**
 * The [HttpException] for unrecognized [ApiResponse]s.
 *
 * @property responseBody
 *   The the body of the [response].
 * @property requestInfo
 *   Information that used to identify the original request this
 *   [ApiHttpException] is for.
 *
 * @constructor
 * Construct an [ApiHttpException].
 *
 * @param responseBody
 *   The error message from the body of the [response].
 * @param requestInfo
 *   Information that used to identify the original request this
 *   [ApiHttpException] is for.
 * @param response
 *   The [HttpException.response].
 */
sealed class ApiHttpException(
	val responseBody: String,
	@Suppress("MemberVisibilityCanBePrivate") val requestInfo: String,
	response: Response<*>
) : HttpException(response) {

	/**
	 * The [HttpStatusCode] associated with this [ApiHttpException].
	 */
	open val statusCode: HttpStatusCode get() = HttpResponseStatusCode[code()]

	override fun toString(): String =
		"${javaClass.simpleName}: $statusCode\n\t$requestInfo"

	companion object {
		/**
		 * Answer an [ApiHttpException] from the provided error body String,
		 * request URL, and [Response].
		 */
		fun from(
			errorBody: String,
			requestInfo: String,
			response: Response<*>
		): ApiHttpException =
			when (response.code()) {
				HttpResponseStatusCode.UNAUTHORIZED.code ->
					UnauthorizedHttpException(
						errorBody,
						requestInfo,
						response
					)
				HttpResponseStatusCode.NOT_FOUND.code ->
					NotFoundHttpException(
						errorBody,
						requestInfo,
						response
					)
				HttpResponseStatusCode.BAD_REQUEST.code ->
					BadRequestHttpException(
						errorBody,
						requestInfo,
						response
					)
				else ->
					GenericHttpException(
						errorBody,
						requestInfo,
						response
					)
			}
	}
}

/**
 * An [ApiHttpException] specific to [HttpResponseStatusCode.UNAUTHORIZED].
 */
class UnauthorizedHttpException(
	errorBody: String,
	requestUrl: String,
	response: Response<*>
) : ApiHttpException(errorBody, requestUrl, response) {
	override val statusCode: HttpStatusCode = HttpResponseStatusCode.UNAUTHORIZED
}

/**
 * An [ApiHttpException] specific to [HttpResponseStatusCode.NOT_FOUND].
 */
class NotFoundHttpException(
	errorBody: String,
	requestUrl: String,
	response: Response<*>
) : ApiHttpException(errorBody, requestUrl, response) {
	override val statusCode: HttpStatusCode = HttpResponseStatusCode.NOT_FOUND
}

/**
 * An [ApiHttpException] specific to [HttpResponseStatusCode.BAD_REQUEST].
 */
class BadRequestHttpException(
	errorBody: String,
	requestUrl: String,
	response: Response<*>
) : ApiHttpException(errorBody, requestUrl, response) {
	override val statusCode: HttpStatusCode = HttpResponseStatusCode.BAD_REQUEST
}

/**
 * A generic [ApiHttpException] that isn't specific to any
 * [HttpResponseStatusCode]. This is the catch-all for any unexpected
 * [Response].
 */
class GenericHttpException(
	errorBody: String,
	requestUrl: String,
	response: Response<*>
) : ApiHttpException(errorBody, requestUrl, response)