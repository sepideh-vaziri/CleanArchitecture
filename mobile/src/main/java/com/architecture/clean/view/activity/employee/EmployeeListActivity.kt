package com.architecture.clean.view.activity.employee

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.architecture.clean.R
import com.architecture.clean.base.*
import com.architecture.clean.databinding.ActivityEmployeeListBinding
import com.architecture.clean.di.component.DaggerEmployeeComponent
import com.architecture.clean.view.Navigator
import com.architecture.clean.view.activity.base.BaseActivity
import com.architecture.clean.view.adapter.EmployeeAdapter
import com.architecture.presentation.viewmodel.base.Status
import com.architecture.presentation.viewmodel.employee.EmployeeListViewModel
import kotlinx.android.synthetic.main.activity_employee_list.*
import timber.log.Timber

class EmployeeListActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private val TAG = EmployeeListActivity::class.java.simpleName
    }

    private lateinit var mNavigator: Navigator

    private lateinit var mEmployeeListViewModel: EmployeeListViewModel

    private lateinit var mEmployeeAdapter: EmployeeAdapter

    //**********************************************************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEmployeeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Provide navigator
        mNavigator = getApplicationComponent().navigator()

        //Provide view model
        val challengeComponent = DaggerEmployeeComponent
            .builder()
            .applicationComponent(getApplicationComponent())
            .build()

        mEmployeeListViewModel = provideViewModel(this, EmployeeListViewModel::class.java) {
            challengeComponent.challengeListViewModel()
        }

        //Set refresh listener
        swipeRefresh.setOnRefreshListener(this)

        //List config
        listConfig()
    }

    override fun onResume() {
        super.onResume()
        loadChallengeList()
    }

    override fun onRefresh() {
        swipeRefresh.isRefreshing = false

        loadChallengeList()
    }

    //**********************************************************************************************
    private fun listConfig() {
        mEmployeeAdapter = EmployeeAdapter(this)
        mEmployeeAdapter.setOnItemClickListener { _, challenge ->
            mNavigator.openEmployeeDetails(this, challenge)
        }

        val spacing = resources.getDimension(R.dimen.employee_list_item_spacing).toInt()

        recyclerViewChallenge.apply {
            linearLayout(
                context,
                spacing = spacing,
                noRightSpacingForFirstItem = true
            )
            adapter = mEmployeeAdapter
        }
    }

    //**********************************************************************************************
    private fun loadChallengeList() {

        mEmployeeListViewModel.getEmployeeList().observe(this, Observer { dataWrapper ->

            when (dataWrapper.status) {

                Status.LOADING -> {
                    progressbarLoading.show()
                }

                Status.SUCCESS -> {
                    progressbarLoading.hide()

                    dataWrapper.data?.let {
                        mEmployeeAdapter.setDataList(it)
                    }
                }

                Status.ERROR -> {
                    Timber.tag(TAG).e(dataWrapper.error)
                    progressbarLoading.hide()

                    val message = resources.getString(R.string.employee_list_load_data_failed_message)
                    showToast(message)

                }

            } //End of when

        })


    }

}