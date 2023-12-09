package com.example.bm_currency_app.networking.supported_symbols

import com.example.bm_currency_app.networking.FixerApiFactory
import com.example.bm_currency_app.networking.FixerApiService

/**
 * The [SupportedSymbolsApiFactory] for creating the [FixerApiService].
 */
object SupportedSymbolsApiFactory : FixerApiFactory<FixerApiService> {
	override val apiService: SupportedSymbolsApiService by lazy {
		serverApiRetrofit().create(SupportedSymbolsApiService::class.java)
	}
}