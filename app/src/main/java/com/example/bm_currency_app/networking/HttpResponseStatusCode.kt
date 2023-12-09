package com.example.bm_currency_app.networking

import android.util.Log

/**
 * An interface that represents a single HTTP Response Status Code per RFC 7231.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6">
 * HTTP Response Status Codes</a>
 */
sealed interface HttpStatusCode {
	/**
	 * The numeric code that represents this [HttpStatusCode].
	 */
	val code: Int
}

/**
 * The [HttpStatusCode] that wraps unrecognized [HttpStatusCode.code]s.
 */
data class UnknownHttpStatusCode (
	override val code: Int
): HttpStatusCode {
	override fun toString (): String =
		"Unrecognized Http Status Code: $code"
}

/**
 * The enumeration of HTTP Response Status Codes as listed in RFC 7231.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6">
 * HTTP Response Status Codes</a>
 */
enum class HttpResponseStatusCode(
	override val code: Int
): HttpStatusCode {
	/**
	 * Code: 100
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.2.1">
	 * RFC 7231 6.2.1</a>
	 */
	CONTINUE(100),

	/**
	 * Code: 101
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.2.2">
	 * RFC 7231 6.2.2</a>
	 */
	SWITCHING_PROTOCOLS(101),

	/**
	 * Code: 200
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.1">
	 * RFC 7231 6.3.1</a>
	 */
	OK(200),

	/**
	 * Code: 201
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.2">
	 * RFC 7231 6.2.2</a>
	 */
	CREATED(201),

	/**
	 * Code: 202
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.3">
	 * RFC 7231 6.3.3</a>
	 */
	ACCEPTED(202),

	/**
	 * Code: 203
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.4">
	 * RFC 7231 6.3.4</a>
	 */
	NON_AUTHORITATIVE_INFORMATION(203),

	/**
	 * Code: 204
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.5">
	 * RFC 7231 6.3.5</a>
	 */
	NO_CONTENT(204),

	/**
	 * Code: 205
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.3.6">
	 * RFC 7231 6.3.6</a>
	 */
	RESET_CONTENT(205),

	/**
	 * Code: 206
	 * @see <a href="https://tools.ietf.org/html/rfc7233#section-4.1">
	 * RFC 7233 4.1</a>
	 */
	PARTIAL_CONTENT(206),

	/**
	 * Code: 300
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.4.1">
	 * RFC 7231 6.4.1</a>
	 */
	MULTIPLE_CHOICES(300),

	/**
	 * Code: 301
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.4.2">
	 * RFC 7231 6.4.2</a>
	 */
	MOVED_PERMANENTLY(301),

	/**
	 * Code: 302
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.4.3">
	 * RFC 7231 6.4.3</a>
	 */
	FOUND(302),

	/**
	 * Code: 303
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.4.4">
	 * RFC 7231 6.4.4</a>
	 */
	SEE_OTHER(303),

	/**
	 * Code: 304
	 * @see <a href="https://tools.ietf.org/html/rfc7232#section-4.1">
	 * RFC 7232 4.1</a>
	 */
	NOT_MODIFIED(304),

	/**
	 * Code: 305
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.4.5">
	 * RFC 7231 6.4.5</a>
	 */
	USE_PROXY(305),

	/**
	 * Code: 307
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.4.7">
	 * RFC 7231 6.4.7</a>
	 */
	TEMPORARY_REDIRECT(307),

	/**
	 * Code: 400
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.1">
	 * RFC 7231 6.5.1</a>
	 */
	BAD_REQUEST(400),

	/**
	 * Code: 401
	 * @see <a href="https://tools.ietf.org/html/rfc7235#section-3.1">
	 * RFC 7235 3.1</a>
	 */
	UNAUTHORIZED(401),

	/**
	 * Code: 402
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.2">
	 * RFC 7231 6.5.2</a>
	 */
	PAYMENT_REQUIRED(402),

	/**
	 * Code: 403
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.3">
	 * RFC 7231 6.5.3</a>
	 */
	FORBIDDEN(403),

	/**
	 * Code: 404
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.4">
	 * RFC 7231 6.5.4</a>
	 */
	NOT_FOUND(404),

	/**
	 * Code: 405
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.5">
	 * RFC 7231 6.5.5</a>
	 */
	METHOD_NOT_ALLOWED(405),

	/**
	 * Code: 406
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.6">
	 * RFC 7231 6.5.6</a>
	 */
	NOT_ACCEPTABLE(406),

	/**
	 * Code: 407
	 * @see <a href="https://tools.ietf.org/html/rfc7235#section-3.2">
	 * RFC 7235 3.2</a>
	 */
	PROXY_AUTHENTICATION_REQUIRED(407),

	/**
	 * Code: 408
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.7">
	 * RFC 7231 6.5.7</a>
	 */
	REQUEST_TIMEOUT(408),

	/**
	 * Code: 409
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.8">
	 * RFC 7231 6.5.8</a>
	 */
	CONFLICT(409),

	/**
	 * Code: 410
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.9">
	 * RFC 7231 6.5.9</a>
	 */
	GONE(410),

	/**
	 * Code: 411
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.10">
	 * RFC 7231 6.5.10</a>
	 */
	LENGTH_REQUIRED(411),

	/**
	 * Code: 412
	 * @see <a href="https://tools.ietf.org/html/rfc7232#section-4.2">
	 * RFC 7232 4.2</a>
	 */
	PRECONDITION_FAILED(412),

	/**
	 * Code: 413
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.11">
	 * RFC 7231 6.5.11</a>
	 */
	PAYLOAD_TOO_LARGE(413),

	/**
	 * Code: 414
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.12">
	 * RFC 7231 6.5.12</a>
	 */
	URI_TOO_LONG(414),

	/**
	 * Code: 415
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.13">
	 * RFC 7231 6.5.13</a>
	 */
	UNSUPPORTED_MEDIA_TYPE(415),

	/**
	 * Code: 416
	 * @see <a href="https://tools.ietf.org/html/rfc7233#section-4.4">
	 * RFC 7233 4.4</a>
	 */
	RANGE_NOT_SATISFIABLE(416),

	/**
	 * Code: 417
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.14">
	 * RFC 7231 6.5.15</a>
	 */
	EXPECTATION_FAILED(417),

	/**
	 * Code: 422
	 * @see <a href="https://httpwg.org/specs/rfc9110.html#status.422">
	 * RFC 9110 15.5.21. 422 Unprocessable Content</a>
	 */
	UNPROCESSABLE_CONTENT(422),

	/**
	 * Code: 426
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.5.15">
	 * RFC 7231 6.5.15</a>
	 */
	UPGRADE_REQUIRED(426),

	/**
	 * Code: 500
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.1">
	 * RFC 7231 6.6.1</a>
	 */
	INTERNAL_SERVER_ERROR(500),

	/**
	 * Code: 501
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.2">
	 * RFC 7231 6.6.2</a>
	 */
	NOT_IMPLEMENTED(501),

	/**
	 * Code: 502
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.3">
	 * RFC 7231 6.6.3</a>
	 */
	BAD_GATEWAY(502),

	/**
	 * Code: 503
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.4">
	 * RFC 7231 6.6.4</a>
	 */
	SERVICE_UNAVAILABLE(503),

	/**
	 * Code: 504
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.5">
	 * RFC 7231 6.6.5</a>
	 */
	GATEWAY_TIMEOUT(504),

	/**
	 * Code: 505
	 * @see <a href="https://tools.ietf.org/html/rfc7231#section-6.6.6">
	 * RFC 7231 6.6.6</a>
	 */
	HTTP_VERSION_NOT_SUPPORTED(505);

	override fun toString (): String =
		"${name.replace('_', ' ')} $code"

	companion object
	{
		/** The map of [HttpResponseStatusCode.code] to [HttpResponseStatusCode]. */
		private val codeMap: Map<Int, HttpResponseStatusCode>

		init
		{
			val cm = mutableMapOf<Int, HttpResponseStatusCode>()
			values().forEach { cm[it.code] = it }
			codeMap = cm
		}

		/**
		 * Answer the [HttpResponseStatusCode] for the given code or
		 * [UnknownHttpStatusCode] if the code is not known by this app.
		 */
		operator fun get (code: Int): HttpStatusCode =
			codeMap[code] ?: UnknownHttpStatusCode(code).apply {
				Log.e(
					"HttpStatusCode",
					"Received unknown status code: $code")
			}
	}
}