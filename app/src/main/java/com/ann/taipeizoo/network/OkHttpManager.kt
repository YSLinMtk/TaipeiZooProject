package com.ann.taipeizoo.network

import com.ann.taipeizoo.BuildConfig
import okhttp3.* // ktlint-disable no-wildcard-imports
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.util.concurrent.TimeUnit

object OkHttpManager {
    private var client: OkHttpClient = OkHttpClient()

    internal var HTTP_CONNECTION_TIMEOUT: Long = 30 // 30秒連線
    internal var HTTP_READ_TIMEOUT: Long = 30 // 30秒response time
    internal var HTTP_WRITE_TIMEOUT: Long = 30 // 30秒response time

    // 紀錄從server取得的cookie
    internal var cookieList: List<Cookie> = arrayListOf()

    fun syncGetDistrict(responseHandler: ResponseHandler): ResponseHandler.StatusData {
        return try {
            val request: Request = Request.Builder()
                .cacheControl(CacheControl.Builder().noCache().build())
                .url("${BuildConfig.DOMAIN_URL}api/v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire")
                .build()
            val response: Response = client.newCall(request).execute()

            val responseBody = response.body?.string()

            println("syncGet response = $responseBody")
            responseHandler.handleSuccess(responseBody!!)
        } catch (e: Exception) {
            responseHandler.handleError(e)
        }
    }

    fun syncGetPlants(
        responseHandler: ResponseHandler,
        location: String?
    ): ResponseHandler.StatusData {
        return try {
            val httpBuilder: HttpUrl.Builder =
                "${BuildConfig.DOMAIN_URL}/api/v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire".toHttpUrlOrNull()!!
                    .newBuilder()
            httpBuilder.addQueryParameter("q", location ?: "")

            val request: Request = Request.Builder()
                .cacheControl(CacheControl.Builder().noCache().build())
                .url(httpBuilder.build())
                .build()
            val response: Response = client.newCall(request).execute()

            val responseBody = response.body?.string()

            println("syncGet response = $responseBody")
            responseHandler.handleSuccess(responseBody!!)
        } catch (e: Exception) {
            responseHandler.handleError(e)
        }
    }

    class Builder {
        fun build() {
            client = OkHttpClient.Builder()
                .connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(object : CookieJar {
                    override
                    fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                        cookieList = cookies
                    }

                    override
                    fun loadForRequest(url: HttpUrl): List<Cookie> {
                        return ArrayList()
                    }
                })
                .build()
        }
    }
}
