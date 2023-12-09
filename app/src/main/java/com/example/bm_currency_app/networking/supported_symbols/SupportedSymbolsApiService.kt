package com.example.bm_currency_app.networking.supported_symbols

import com.example.bm_currency_app.networking.ApiResponseFactory
import com.example.bm_currency_app.networking.FixerApiService
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Tag

/**
 * The [FixerApiService] specific to Supported Symbols data.
 *
 * See [Blueprint](https://fixer.io/documentation#supportedsymbols) documentation.
 */
interface SupportedSymbolsApiService : FixerApiService {
	/**
	 * Make a server [GetSupportedSymbolsResponseFactory] to get the supported
	 * currency symbols by Fixer server.
	 * Answers a flow of a [GetSupportedSymbolsResponse] that provides the
	 * outcome of the request.
	 *
	 * See [Blueprint](https://fixer.io/documentation#supportedsymbols)
	 */
	@GET("symbols")
	suspend fun getSupportedSymbols(
		@Query("access_key") accessKey: String = "391c580bd1362f8c127263fa83774f03",
		@Tag apiResponseFactory: ApiResponseFactory<GetSupportedSymbolsResponse> =
			GetSupportedSymbolsResponseFactory
	): GetSupportedSymbolsResponse
}