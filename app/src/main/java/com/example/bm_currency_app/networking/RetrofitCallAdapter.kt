package com.example.bm_currency_app.networking

import android.util.Log
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * The Retrofit [Call] used to wrap and process [FixerApiService] [Response]s.
 *
 * @param T
 *   Represents the [ApiResponse] that is serialized in the API [Response]
 *   that is expected to be received in response to a [FixerApiService]
 *   [ApiResponseFactory].
 * @property wrapped
 *   The original wrapped String [Call] being processed.
 */
class ApiResponseCall<T: ApiResponse>(
	private val wrapped: Call<String>
): Call<T> {
	override fun execute(): Response<T> {
		throw UnsupportedOperationException(
			"ApiResponseCall is only meant to be used asynchronously")
	}

	override fun enqueue(callback: Callback<T>) {
		wrapped.enqueue(object : Callback<String> {
			override fun onResponse(
				call: Call<String>,
				response: Response<String>
			) {
				try {
					// The original request is required to pass in an ApiRequest
					// so that it can be used to deserialize the response based
					// upon the Response's HTTP Status Code.
					val apiResponseFactory =
						wrapped.request().tag(ApiResponseFactory::class.java)
							?: throw ApiResponseFactoryMissingTagException(
								wrapped.request().url.toString()
							)
					val body =
						if (response.isSuccessful) {
							response.body() ?: ""
						} else {
							response.errorBody()?.bytes()?.decodeToString() ?: ""
						}
					val rsp = apiResponseFactory.provideResponse(
						response.code(), body
					) ?: throw ApiHttpException.from(
						body,
						"${apiResponseFactory.javaClass.simpleName}: " +
							wrapped.request().url.toString(),
						response
					)

					@Suppress("UNCHECKED_CAST")
					callback.onResponse(
						this@ApiResponseCall,
						// We know the cast must succeed here given that we expect
						// a known, well-formed response from the server that
						// conforms to the expected parameterized type, T.
						Response.success(rsp as T)
					)
				} catch (e: Throwable) {
					// We need to pass this up stream to the onFailure of the
					// original Call<T>
					Log.e(
						"RetrofitCallAdapter",
						"Received Response but encountered unexpected " +
							"exception during response processing: ${e.message}",
						e)
					callback.onFailure(this@ApiResponseCall, e)
				}
			}

			override fun onFailure(call: Call<String>, t: Throwable) {
				// We need to pass this up stream to the onFailure of the
				// original Call<T>
				val apiResponseFactory =
					wrapped.request().tag(ApiResponseFactory::class.java)
				Log.e(
					"RetrofitCallAdapter",
					"Unexpected Exception for " +
						"${apiResponseFactory?.javaClass?.simpleName 
							?: apiResponseFactory}: ${t.message}",
					t)
				callback.onFailure(this@ApiResponseCall, t)
			}
		})
	}

	override fun isExecuted(): Boolean = wrapped.isExecuted

	override fun cancel() {
		wrapped.cancel()
	}

	override fun isCanceled(): Boolean = wrapped.isCanceled

	override fun request(): Request = wrapped.request()

	override fun timeout(): Timeout = wrapped.timeout()

	override fun clone(): Call<T> = ApiResponseCall(wrapped)
}

/**
 * The [CallAdapter] for providing the [ApiResponseCall].
 */
class ApiResponseCallAdapter: CallAdapter<String, Call<ApiResponse>> {
	// We need this to be a String type as we need to be able to force the
	// usage of the  retrofit2.converter.scalars.ScalarsConverterFactory to
	// process the response as a Response<String> type as an intermediary step.
	// This will enable the the final deserialization to be handled by the
	// ApiResponseCall by the enqueued Callback.onResponse in
	// ApiResponseCall.wrapped.enqueue.
	override fun responseType(): Type = String::class.java

	override fun adapt(call: Call<String>): Call<ApiResponse> =
		ApiResponseCall(call)
}

/**
 * The [CallAdapter.Factory] that can be used by the
 * [Retrofit.Builder] [Retrofit.Builder.addCallAdapterFactory] to add the
 * ability to process [ApiResponseCall]s.
 */
object ApiResponseCallAdapterFactory: CallAdapter.Factory() {
	override fun get(
		returnType: Type,
		annotations: Array<out Annotation>,
		retrofit: Retrofit
	): CallAdapter<*, *>? =
		if (getRawType(returnType) != Call::class.java) {
			null
		} else {
			ApiResponseCallAdapter()
		}
}