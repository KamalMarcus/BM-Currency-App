package com.example.bm_currency_app.ui.currency_conversion

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bm_currency_app.mapAllSymbolValues
import com.example.bm_currency_app.networking.supported_symbols.GetSupportedSymbolsSuccessResponse
import com.example.bm_currency_app.networking.supported_symbols.SupportedSymbolsApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CurrencyConversionViewModel : ViewModel() {
	val supportedSymbolsList: MutableState<List<String>?> =
		mutableStateOf(null)

	init {
		getSupportedSymbols()
	}

	private fun getSupportedSymbols() {
		viewModelScope.launch {
			val response = SupportedSymbolsApi.sole.getSupportedSymbols().catch {
				Log.e("SupportedSymbolsApi error", "${it.message}")
			}.first()
			when (response) {
				is GetSupportedSymbolsSuccessResponse -> {
					supportedSymbolsList.value =
						response.symbols.mapAllSymbolValues()
					Log.d("SupportedSymbolsApi", "$response")
				}
			}
		}
	}
}