package com.ann.taipeizoo.presenter // ktlint-disable filename

import com.ann.taipeizoo.contract.TPEZooContract
import com.ann.taipeizoo.network.OkHttpManager
import com.ann.taipeizoo.network.ResponseHandler

class TPEZooPresenter(private val view: TPEZooContract.View) : TPEZooContract.Presenter {

    override fun getDistrict() {
        val responseHandler = ResponseHandler()
        val result = OkHttpManager.syncGetDistrict(
            responseHandler
        )
        view.updateView(result)
    }

    override fun getPlants(location: String?) {
        val responseHandler = ResponseHandler()
        val result = OkHttpManager.syncGetPlants(
            responseHandler,
            location
        )
        view.updateView(result)
    }
}
