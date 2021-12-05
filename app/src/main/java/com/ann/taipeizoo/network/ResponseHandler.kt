package com.ann.taipeizoo.network

import java.net.ConnectException
import java.net.UnknownHostException
import java.security.cert.CertificateException
import javax.net.ssl.SSLException

open class ResponseHandler {

    private val errorMsg = "網路連線異常，請確認網路狀況"
    enum class State {
        SUCCESS,
        ERROR
    }

    data class StatusData(
        val state: State,
        val message: String = ""
    )

    private fun apiFailure(e: Exception): String {
        e.printStackTrace()
        return when (e) {
            is ConnectException -> {
                // 無網路等
                errorMsg
            }
            is UnknownHostException -> {
                // 連線主機問題
                errorMsg
            }
            is CertificateException, is SSLException -> {
                // 憑證等
                errorMsg
            }
            else -> {
                // 其他未知錯誤
                "發生未知錯誤，請稍後再試"
            }
        }
    }

    fun handleSuccess(data: String): StatusData {
        return StatusData(State.SUCCESS, data)
    }

    fun handleError(e: Exception): StatusData {
        return StatusData(State.ERROR, apiFailure(e))
    }
}
