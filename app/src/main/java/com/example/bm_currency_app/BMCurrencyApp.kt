package com.example.bm_currency_app

import android.app.Application
import com.example.bm_currency_app.networking.supported_symbols.SupportedSymbolsApi

class BMCurrencyApp : Application() {
	/**
	 * An initializer flag that tracks whether or not
	 * [initializeStatefulResources] has already run.
	 */
	private var isInitialized: Boolean = false
	override fun onCreate() {
		super.onCreate()

		initializeStatefulResources()
	}

	private fun initializeStatefulResources() {
		if (isInitialized) {
			return
		}
		isInitialized = true
		////////////////////////////////////////////////////////////////////////
		//                  Initialize Network API Resources                  //
		////////////////////////////////////////////////////////////////////////
		SupportedSymbolsApi.initialize()
	}
}