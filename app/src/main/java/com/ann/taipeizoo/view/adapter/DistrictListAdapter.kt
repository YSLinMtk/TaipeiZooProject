package com.ann.taipeizoo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ann.taipeizoo.R
import com.ann.taipeizoo.dataclass.District
import com.ann.taipeizoo.loadUrl

class DistrictListAdapter(
    private val mContext: Context,
    private val districtList: List<District>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClick: ((District) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(R.layout.adapter_district_list, parent, false)
        return ViewHolderItem(v)
    }

    override fun getItemCount(): Int {
        return districtList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, p1: Int) {
        if (viewHolder is ViewHolderItem) {
            viewHolder.ivDistrict.loadUrl(districtList[p1].picUrl)
            viewHolder.tvDistrictName.text = districtList[p1].districtName
            viewHolder.tvDistrictInfo.text = districtList[p1].info
            viewHolder.tvMemo.text = if (districtList[p1].memo.isEmpty()) mContext.getString(R.string.msg_none_opening_hours) else districtList[p1].memo
        }
    }

    private inner class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cellView = itemView
        val ivDistrict: ImageView = itemView.findViewById(R.id.iv_district)
        val tvDistrictName: TextView = itemView.findViewById(R.id.tv_district_name)
        val tvDistrictInfo: TextView = itemView.findViewById(R.id.tv_district_info)
        val tvMemo: TextView = itemView.findViewById(R.id.tv_memo)
        init {
            cellView.setOnClickListener {
                onItemClick?.invoke(districtList[adapterPosition])
            }
        }
    }
}
