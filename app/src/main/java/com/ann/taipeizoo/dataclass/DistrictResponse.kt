package com.ann.taipeizoo.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class DistrictResponse(
    var result: DistrictResult = DistrictResult(),
)

data class DistrictResult(
    val limit: Int = -1,
    val offset: Int = -1,
    val count: Int = -1,
    val sort: String = "",
    @SerializedName("results")
    val districtList: List<District> = arrayListOf(),
)

@Parcelize
data class District(
    @SerializedName("E_Pic_URL")
    val picUrl: String,
    @SerializedName("E_Category")
    val category: String,
    @SerializedName("E_Name")
    val districtName: String,
    @SerializedName("E_Info")
    val info: String,
    @SerializedName("_id")
    val id: Int,
    @SerializedName("E_Memo")
    val memo: String,
    @SerializedName("E_URL")
    val url: String,
) : Parcelable
