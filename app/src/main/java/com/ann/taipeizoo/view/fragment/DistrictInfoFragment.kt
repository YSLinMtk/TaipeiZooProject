package com.ann.taipeizoo.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ann.taipeizoo.R
import com.ann.taipeizoo.dataclass.District
import com.ann.taipeizoo.view.BaseFragment
import com.ann.taipeizoo.view.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DistrictInfoFragment : BaseFragment() {
    private lateinit var mContext: Context
    private lateinit var viewPager: ViewPager2
    private val districtDetail: District by lazy {
        arguments?.get("dist") as District
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
        val view = inflater.inflate(R.layout.fragment_district_info, container, false)
        setActionBar(true, districtDetail.districtName)
        viewPager = view.findViewById(R.id.view_pager)
        val fragmentList = arrayListOf(
            DistrictContentFragment.getInstance(districtDetail),
            PlantsListFragment.getInstance(districtDetail)
        )
        val pagerAdapter = ViewPagerAdapter(this, fragmentList)
        viewPager.adapter = pagerAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(
                when (position) {
                    0 -> R.string.tab_title_district
                    else -> R.string.tab_title_plants
                }
            )
        }.attach()
        // viewPager中的多個fragment layout高度不會被彼此影響
//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                if (position > 0 && positionOffset == 0.0f && positionOffsetPixels == 0) {
//                    viewPager.layoutParams.height =
//                        viewPager.getChildAt(0).height
//                }
//            }
//        })
        viewPager.setPageTransformer { page, _ ->
            val wMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(page.width, View.MeasureSpec.EXACTLY)
            val hMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            page.measure(wMeasureSpec, hMeasureSpec)
            pagerAdapter?.notifyDataSetChanged()
        }
        return view
    }
}
