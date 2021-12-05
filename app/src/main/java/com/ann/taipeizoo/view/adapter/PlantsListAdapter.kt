package com.ann.taipeizoo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ann.taipeizoo.R
import com.ann.taipeizoo.dataclass.Plant
import com.ann.taipeizoo.loadUrl

class PlantsListAdapter(
    private val plantsList: List<Plant>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClick: ((Plant) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(R.layout.adapter_plants_list, parent, false)
        return ViewHolderItem(v)
    }

    override fun getItemCount(): Int {
        return plantsList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, p1: Int) {
        if (viewHolder is ViewHolderItem) {
            viewHolder.ivDistrict.loadUrl(plantsList[p1].pic01Url)
            viewHolder.tvDistrictName.text = plantsList[p1].nameCh
            viewHolder.tvDistrictInfo.text = plantsList[p1].alsoKnow
        }
    }

    private inner class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cellView = itemView
        val ivDistrict: ImageView = itemView.findViewById(R.id.iv_plant)
        val tvDistrictName: TextView = itemView.findViewById(R.id.tv_plant_name)
        val tvDistrictInfo: TextView = itemView.findViewById(R.id.tv_plant_info)

        init {
            cellView.setOnClickListener {
                onItemClick?.invoke(plantsList[adapterPosition])
            }
        }
    }
}
