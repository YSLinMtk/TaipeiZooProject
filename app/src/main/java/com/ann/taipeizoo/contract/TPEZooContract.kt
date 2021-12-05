package com.ann.taipeizoo.contract

import com.ann.taipeizoo.network.ResponseHandler

interface TPEZooContract {
    interface View {
        fun updateView(result: ResponseHandler.StatusData)
    }
    interface Presenter {
        fun getDistrict()
        fun getPlants(location: String?)
    }
}
