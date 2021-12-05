package com.ann.taipeizoo.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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
import com.ann.taipeizoo.dataclass.DistrictResponse
import com.ann.taipeizoo.network.ResponseHandler
import com.ann.taipeizoo.presenter.TPEZooPresenter
import com.ann.taipeizoo.view.BaseFragment
import com.ann.taipeizoo.view.adapter.DistrictListAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_tpezoo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TPEZooFragment : BaseFragment(), TPEZooContract.View {
    private lateinit var presenter: TPEZooContract.Presenter
    private lateinit var rvDistrictList: RecyclerView
    private val dataResultLiveData = MutableLiveData<DistrictListAdapter>()
    private lateinit var mContext: Context
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
        val view = inflater.inflate(R.layout.fragment_tpezoo, container, false)
        setActionBar(false)
        progressBar = view.findViewById(R.id.pb_loading)
        rvDistrictList = view.findViewById(R.id.rv_district)
        rvDistrictList.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rvDistrictList.addItemDecoration(
            DividerItemDecoration(
                mContext,
                LinearLayoutManager.VERTICAL
            )
        )
        viewLifecycleOwner.let { lifecycleOwner ->
            dataResultLiveData.observe(
                lifecycleOwner,
                Observer {
                    it.onItemClick = { district ->
                        val bundle = bundleOf("dist" to district)
                        findNavController().navigate(R.id.action_to_district_detail, bundle)
                    }
                    progressBar.visibility = View.GONE
                    rvDistrictList.adapter = it
                }
            )
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TPEZooPresenter(this@TPEZooFragment)
        callApi()
    }

    private fun callApi() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                presenter.getDistrict()
            }
        }
    }

    override fun updateView(result: ResponseHandler.StatusData) {
        if (result.state == ResponseHandler.State.SUCCESS) {
            SingletonDataObject.districtListData = Gson().fromJson(
                result.message,
                DistrictResponse::class.java
            )
            val districtListAdapter =
                DistrictListAdapter(
                    mContext,
                    SingletonDataObject.districtListData.result.districtList
                )
            dataResultLiveData.postValue(districtListAdapter)
        } else {
            activity?.runOnUiThread {
                progressBar.visibility = View.GONE
                showErrorDialog(result.message)
            }
        }
    }
}
