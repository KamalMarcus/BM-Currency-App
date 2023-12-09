package com.example.bm_currency_app.ui.currency_conversion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bm_currency_app.ui.composables.ExposedDropdownMenuBox
import com.example.bm_currency_app.ui.theme.BMCurrencyAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bm_currency_app.R

@Composable
fun CurrencyConversionScreen(
	viewModel: CurrencyConversionViewModel = viewModel(
		factory = viewModelFactory {
			initializer { CurrencyConversionViewModel() }
		})
) {
	val supportedSymbolsList by remember {
		viewModel.supportedSymbolsList
	}
	Column {
		Text(stringResource(R.string.from))
		supportedSymbolsList?.let { ExposedDropdownMenuBox(it) }
		Spacer(modifier = Modifier.padding(10.dp))
		Text(stringResource(R.string.to))
		supportedSymbolsList?.let { ExposedDropdownMenuBox(it) }
	}
}

@Preview(showBackground = true)
@Composable
fun CurrencyConversionScreenPreview() {
	BMCurrencyAppTheme {
		CurrencyConversionScreen()
	}
}