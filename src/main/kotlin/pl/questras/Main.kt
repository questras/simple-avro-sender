package pl.questras

import okhttp3.Headers.Companion.toHeaders
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import tech.allegro.schema.json2avro.converter.JsonAvroConverter

fun main() {
    assert(AVRO_MESSAGE.isNotBlank()) { "You did not provide the avro message" }
    assert(AVRO_SCHEMA.isNotBlank()) { "You did not provide the avro schema" }
    val message = AVRO_MESSAGE
    val schema = AVRO_SCHEMA
    val url: String = TODO("Provide url of the request")
    val customHeaders: Map<String, String> = emptyMap()

    val converter = JsonAvroConverter()
    val avro = converter.convertToAvro(message.toByteArray(charset = Charsets.UTF_8), schema)

    println("Sending request...")
    val response = sendRequest(url, avro, customHeaders)

    println("Response:")
    println(response.toString())
}

private fun sendRequest(url: String, payload: ByteArray, customHeaders: Map<String, String> = emptyMap()): Response {
    val client = OkHttpClient()
    val mediaType = "avro/binary;charset=UTF-8".toMediaType()
    val body = payload.toRequestBody(mediaType)
    val request = Request.Builder()
        .url(url)
        .post(body)
        .headers(customHeaders.toHeaders())
        .build()

    println("Request: $request")
    println("Payload: ${payload.toString(Charsets.UTF_8)}")

    client.newCall(request).execute().use { response ->
        return response
    }
}
