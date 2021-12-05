package com.ann.taipeizoo.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ann.taipeizoo.R
import com.ann.taipeizoo.dataclass.District
import com.ann.taipeizoo.loadUrl
import com.ann.taipeizoo.openBrowser

class DistrictContentFragment : Fragment() {
    private lateinit var mContext: Context

    companion object {
        private const val DISTRICT_CONTENT = "district_detail"

        fun getInstance(district: District): DistrictContentFragment {
            return DistrictContentFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DISTRICT_CONTENT, district)
                }
            }
        }
    }

    private val districtDetail: District by lazy {
        arguments?.get(DISTRICT_CONTENT) as District
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
        val view = inflater.inflate(R.layout.fragment_district_content, container, false)
        view.findViewById<ImageView>(R.id.iv_content_pic).loadUrl(districtDetail.picUrl)
        view.findViewById<TextView>(R.id.tv_content_info).text = districtDetail.info
        view.findViewById<TextView>(R.id.tv_content_memo).text =
            if (districtDetail.memo.isEmpty()) mContext.getString(R.string.msg_none_opening_hours) else districtDetail.memo
        view.findViewById<TextView>(R.id.tv_content_category).text = districtDetail.category
        view.findViewById<Button>(R.id.btn_open_web).setOnClickListener { openBrowser(mContext, districtDetail.url) }
        return view
    }
}
