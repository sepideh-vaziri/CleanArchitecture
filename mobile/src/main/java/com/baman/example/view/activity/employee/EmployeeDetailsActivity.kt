package com.baman.example.view.activity.employee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.baman.example.R
import com.baman.example.base.*
import com.baman.example.base.hide
import com.baman.example.base.provideViewModel
import com.baman.example.base.show
import com.baman.example.databinding.ActivityEmployeeDetailsBinding
import com.baman.example.di.component.DaggerEmployeeComponent
import com.baman.example.view.activity.base.BaseActivity
import com.baman.presentation.model.EmployeeModel
import com.baman.presentation.viewmodel.base.Status
import com.baman.presentation.viewmodel.employee.EmployeeViewModel
import kotlinx.android.synthetic.main.activity_employee_details.*
import timber.log.Timber

class EmployeeDetailsActivity : BaseActivity() {

    companion object {
        private val TAG = EmployeeDetailsActivity::class.java.simpleName

        private const val KEY_CHALLENGE = "challenge"

        internal fun getIntent(context: Context, employeeModel: EmployeeModel) : Intent {
            return Intent(context, EmployeeDetailsActivity::class.java).apply {
                putExtra(KEY_CHALLENGE, employeeModel)
            }
        }
    }


    private lateinit var mEmployeeViewModel: EmployeeViewModel

    //**********************************************************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get data from intent
        val challenge = intent.getSerializableExtra(KEY_CHALLENGE) as? EmployeeModel

        //View Model
        val challengeComponent = DaggerEmployeeComponent
            .builder()
            .applicationComponent(getApplicationComponent())
            .build()

        mEmployeeViewModel = provideViewModel(this, EmployeeViewModel::class.java) {
            challengeComponent.challengeViewModel()
        }

        //Load details
        challenge?.id?.let { challengeId ->
            getChallengeDetails(challengeId)
        }
    }

    //**********************************************************************************************
    private fun getChallengeDetails(id: Long) {

        mEmployeeViewModel.getEmployeeDetails(id).observe(this, Observer { dataWrapper ->

            when (dataWrapper.status) {
                Status.LOADING -> {
                    progressbarLoading.show()
                }

                Status.SUCCESS -> {
                    progressbarLoading.hide()

                    showChallengeDetails(dataWrapper.data)
                }

                Status.ERROR -> {
                    Timber.tag(TAG).e(dataWrapper.error)
                    progressbarLoading.hide()

                    showToast(
                        resources.getString(R.string.employee_details_load_data_failed_message)
                    )
                }
            }

        })

    }

    //**********************************************************************************************
    private fun showChallengeDetails(employeeModel: EmployeeModel?) {
        if (employeeModel == null) {
            return
        }

        textName.text = String.format(
            resources.getString(R.string.employee_details_name),
            employeeModel.employee_name
        )

        textSalary.text = String.format(
            resources.getString(R.string.employee_details_salary),
            employeeModel.employee_salary
        )

        textAge.text = String.format(
            resources.getString(R.string.employee_details_age),
            employeeModel.employee_age
        )

    }

}