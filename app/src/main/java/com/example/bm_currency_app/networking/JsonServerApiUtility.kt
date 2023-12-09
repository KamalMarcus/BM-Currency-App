package com.example.bm_currency_app.networking

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

///////////////////////////////////////////////////////////////////////////////
// Contains utility functionality for Kotlin serialization when used with    //
// Fixer server API.                                                       //
///////////////////////////////////////////////////////////////////////////////

/**
 * The [Json] used for data serialization and data deserialization of Fixer
 * Server API messages.
 */
val ServerApiJson: Json = Json {
	ignoreUnknownKeys = true
	encodeDefaults = true
}

/**
 * Deserializes as JSON the given string to the value of type `T` using
 * deserializer retrieved from the reified type parameter. This is specifically
 * targeted at Fixer Server API.
 *
 * @param T
 *   The type of the [Serializable] to deserialize.
 * @param serialized
 *   The serialized JSON string to deserialize.
 * @return
 *   The deserialized [T].
 * @throws SerializationException
 *   Thrown in the event that any encoding-specific error is encountered.
 * @throws IllegalArgumentException
 *   Thrown if the encoded input does not comply format's specification.
 */
inline fun <reified T> decodeApiJson (serialized: String): T =
	ServerApiJson.decodeFromString(serialized)