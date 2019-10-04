package com.kisannetwork.contacts.sample.data.remote.api

import com.kisannetwork.contacts.sample.data.remote.api.model.NexmoSmsResponse
import com.kisannetwork.contacts.sample.result.Result
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private const val TAG = "NexmoSmsService"

interface NexmoSmsService {

    @FormUrlEncoded
    @POST("/sms/json")
    fun sendSms(
        @Field("to") to: String,
        @Field("from") form: String,
        @Field("text") text: String,
        @Field("api_key") apiKey: String,
        @Field("api_secret") apiSecret: String
    ): Call<NexmoSmsResponse>

    companion object {
        private const val BASE_URL = "https://rest.nexmo.com"

        fun create(): NexmoSmsService {

            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NexmoSmsService::class.java)
        }
    }
}

fun sendSms(
    service: NexmoSmsService,
    to: String,
    form: String,
    text: String,
    apiKey: String,
    apiSecret: String
): Result<Boolean> {

    val response = service.sendSms(to, form, text, apiKey, apiSecret)
        .execute()

    return if (response.isSuccessful) {
        val messageStatus = response.body()
            ?.messages?.get(0)
            ?.status
        if (messageStatus == null) {
            Result.Error(NaxmoException("Unknown error"))
        } else {
            if (messageStatus == "0") {
                Result.Success(true)
            } else {
                Result.Error(
                    NaxmoException(
                        response.body()?.messages?.get(0)?.errorText ?: "Unknown error"
                    )
                )
            }
        }
    } else {
        Result.Error(Exception(response.errorBody()?.string() ?: "Unknown error"))
    }
}

class NaxmoException(message: String) : Exception(message)