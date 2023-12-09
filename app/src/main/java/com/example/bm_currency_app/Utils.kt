package com.example.bm_currency_app

import com.example.bm_currency_app.networking.supported_symbols.GetSupportedSymbolsSuccessResponse

/**
 * used to map all the supported symbol values.
 */
fun GetSupportedSymbolsSuccessResponse.Symbols.mapAllSymbolValues(): List<String> =
	this::class.java.declaredFields.map { it.name }