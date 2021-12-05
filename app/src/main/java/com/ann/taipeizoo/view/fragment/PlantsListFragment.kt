package com.ann.taipeizoo.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ann.taipeizoo.R
import com.ann.taipeizoo.SingletonDataObject
import com.ann.taipeizoo.contract.TPEZooContract
import com.ann.taipeizoo.dataclass.District
import com.ann.taipeizoo.dataclass.Plant
import com.ann.taipeizoo.dataclass.PlantResponse
import com.ann.taipeizoo.network.ResponseHandler
import com.ann.taipeizoo.presenter.TPEZooPresenter
import com.ann.taipeizoo.view.BaseFragment
import com.ann.taipeizoo.view.adapter.PlantsListAdapter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlantsListFragment : BaseFragment(), TPEZooContract.View {
    companion object {
        private const val PLANT_DETAIL_CONTENT = "plant_detail"

        fun getInstance(district: District): PlantsListFragment {
            return PlantsListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PLANT_DETAIL_CONTENT, district)
                }
            }
        }
    }

    private val districtDetail: District by lazy {
        arguments?.get(PLANT_DETAIL_CONTENT) as District
    }
    private lateinit var mContext: Context
    private lateinit var presenter: TPEZooContract.Presenter
    private lateinit var plantList: List<Plant>
    private lateinit var rvPlantsList: RecyclerView
    private lateinit var tvEmpty: TextView
    private val dataResultLiveData = MutableLiveData<PlantsListAdapter>()
    private val dataResultEmptyLiveData = MutableLiveData<Boolean>()
    private lateinit var progressBar: ProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plants_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TPEZooPresenter(this@PlantsListFragment)
        progressBar = view.findViewById(R.id.pb_loading)
        tvEmpty = view.findViewById(R.id.tv_empty)
        rvPlantsList = view.findViewById(R.id.rv_plants)
        rvPlantsList.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rvPlantsList.addItemDecoration(
            DividerItemDecoration(
                mContext,
                LinearLayoutManager.VERTICAL
            )
        )
        viewLifecycleOwner.let { lifecycleOwner ->
            dataResultLiveData.observe(
                lifecycleOwner,
                Observer {
                    it.onItemClick = { plant ->
                        val bundle = bundleOf("plant" to plant)
                        findNavController().navigate(
                            R.id.action_navi_district_info_to_navi_plant_detail,
                            bundle
                        )
                    }
                    progressBar.visibility = View.GONE
                    rvPlantsList.adapter = it
                }
            )
            dataResultEmptyLiveData.observe(
                lifecycleOwner,
                Observer {
                    if (it) {
                        tvEmpty.visibility = View.VISIBLE
                        rvPlantsList.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        dataResultEmptyLiveData.postValue(false)
                    }
                }
            )
        }
        callApi()
    }

    private fun callApi() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                presenter.getPlants(districtDetail.districtName)
            }
        }
    }

    override fun updateView(result: ResponseHandler.StatusData) {
        if (result.state == ResponseHandler.State.SUCCESS) {
            SingletonDataObject.plantListData = Gson().fromJson(
                result.message,
                PlantResponse::class.java
            )
            if (SingletonDataObject.plantListData.result.plantList.isEmpty()) {
                dataResultEmptyLiveData.postValue(true)
            } else {
                plantList =
                    SingletonDataObject.plantListData.result.plantList.distinctBy { it.nameCh }
                val plantsListAdapter =
                    PlantsListAdapter(plantList)
                dataResultLiveData.postValue(plantsListAdapter)
            }
        } else {
            activity?.runOnUiThread {
                progressBar.visibility = View.GONE
                showErrorDialog(result.message)
            }
        }
    }
}
