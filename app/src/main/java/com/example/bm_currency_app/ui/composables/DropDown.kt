package com.example.bm_currency_app.ui.composables

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExposedDropdownMenuBox(optionsList: List<String>) {
	val context = LocalContext.current
	var expanded by remember { mutableStateOf(false) }
	var selectedText by remember { mutableStateOf(optionsList[0]) }

	Box(
		modifier = Modifier
			.fillMaxWidth()
			.padding(32.dp)
	) {
		ExposedDropdownMenuBox(
			expanded = expanded,
			onExpandedChange = {
				expanded = !expanded
			}
		) {
			TextField(
				value = selectedText,
				onValueChange = {},
				readOnly = true,
				trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
				modifier = Modifier.menuAnchor()
			)

			ExposedDropdownMenu(
				expanded = expanded,
				onDismissRequest = { expanded = false }
			) {
				optionsList.forEach { item ->
					DropdownMenuItem(
						text = { Text(text = item) },
						onClick = {
							selectedText = item
							expanded = false
							Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
						}
					)
				}
			}
		}
	}
}