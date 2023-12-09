package com.example.bm_currency_app.networking.supported_symbols

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * The general network API for managing location info.
 *
 * @property apiService
 *   The backing [SupportedSymbolsApiService] used to access the Supported Symbols API.
 */
class SupportedSymbolsApi constructor(
	private val apiService: SupportedSymbolsApiService = SupportedSymbolsApiFactory.apiService
) {
	/**
	 * Make a server [GetSupportedSymbolsResponseFactory] to get the supported
	 * currency symbols by Fixer server.
	 * Answers a flow of a [GetSupportedSymbolsResponse] that provides the
	 * outcome of the request.
	 */
	fun getSupportedSymbols(): Flow<GetSupportedSymbolsResponse> =
		flow {
			val response = apiService.getSupportedSymbols()
			when (response) {
				is GetSupportedSymbolsSuccessResponse -> Log.i(
					"SupportedSymbolsApi", "$response"
				)
				/**
				 * ToDo handle the rest of the response status codes here
				 */
			}
			emit(response)
		}

	companion object {
		/**
		 * The sole instance of the [SupportedSymbolsApi].
		 */
		lateinit var sole: SupportedSymbolsApi
			private set

		/**
		 * Initialize [SupportedSymbolsApi.sole] with the provided [SupportedSymbolsApi]. This
		 * should only be done once; any subsequent attempt to initialize this
		 * will be logged as an error.
		 */
		fun initialize(api: SupportedSymbolsApi = SupportedSymbolsApi()) {
			if (Companion::sole.isInitialized) {
				Log.e(
					"SupportedSymbolsApi",
					"An attempt was made to initialize MapsApi.sole " +
							"after it " +
							"has already been initialized",
					RuntimeException(
						"Unexpected MapsApi initialization attempt"
					)
				)
			}
			sole = api
		}
	}
}