package com.example.bm_currency_app.networking

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * A logging [Interceptor] used to log HTTP requests.
 *
 * @property includeBody
 *   `true` indicates the body of the request/response should be included in
 *   the log; `false` otherwise.
 */
internal class CustomHttpLoggingInterceptor constructor(
	private val includeBody: Boolean
): Interceptor {

	/**
	 * `true` if we should avoid logging this this header; `false` otherwise.
	 */
	private fun isSensitiveHeader(name: String): Boolean {
		return name.equals("Authorization", ignoreCase = true) ||
			name.equals("Cookie", ignoreCase = true) ||
			name.equals("Proxy-Authorization", ignoreCase = true) ||
			name.equals("Set-Cookie", ignoreCase = true)
	}

	override fun intercept(chain: Interceptor.Chain): Response {
		val request: Request = chain.request()
		val t1 = System.nanoTime()
		Log.i(
			"HttpLogging",
			buildString {
				append("Sending request: ")
				append(request.method)
				append(" ")
				append(request.url)
				append("\n\theaders:")
				request.headers.let { headers ->
					headers.names().iterator().forEach {
						append("\n\t\t")
						append(it)
						append(": ")
						append(
							if (isSensitiveHeader(it)) "[REMOVED]"
							else headers[it]
						)
					}
					if (includeBody) {
						append("\n\tRequest Body: ")
						append(request.body.toString())
					}
				}
			}
		)
		val response: Response = chain.proceed(request)
		val t2 = System.nanoTime()
		Log.i(
			"HttpLogging",
			buildString {
				append("Received response from: ")
				append(response.request.url)
				append(" (")
				append((t2 - t1) / 1000)
				append(" µs)")
				append("\n\tresponse code: ")
				append(HttpResponseStatusCode[response.code])
				append("\n\theaders:")
				response.headers.let { headers ->
					headers.names().iterator().forEach {
						append("\n\t\t")
						append(it)
						append(": ")
						append(
							if (isSensitiveHeader(it)) "[REMOVED]"
							else headers[it]
						)
					}
					if (includeBody) {
						append("\n\tResponse Body: ")
						append(response.peekBody(1024).string())
					}
				}
			}
		)
		return response
	}
}