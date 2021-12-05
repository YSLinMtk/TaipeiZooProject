package com.ann.taipeizoo.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ann.taipeizoo.R
import com.ann.taipeizoo.dataclass.Plant
import com.ann.taipeizoo.loadUrl

class PlantDetailFragment : Fragment() {
    private lateinit var mContext: Context
    private val plantDetail: Plant by lazy {
        arguments?.get("plant") as Plant
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plant_detail, container, false)
        view.findViewById<ImageView>(R.id.iv_plant_pic).loadUrl(plantDetail.pic01Url)
        view.findViewById<TextView>(R.id.tv_ch_name).text = plantDetail.nameCh
        view.findViewById<TextView>(R.id.tv_en_name).text = plantDetail.nameEn
        view.findViewById<TextView>(R.id.tv_also_known_content).text = plantDetail.alsoKnow
        view.findViewById<TextView>(R.id.tv_brief_content).text = plantDetail.brief
        view.findViewById<TextView>(R.id.tv_feature_content).text = plantDetail.feature
        view.findViewById<TextView>(R.id.tv_func_and_application_content).text = plantDetail.application
        view.findViewById<TextView>(R.id.tv_last_update).text = "${getString(R.string.tv_title_last_update)}：${plantDetail.update}"
        return view
    }
}
