package com.architecture.clean.view.activity.employee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.architecture.clean.R
import com.architecture.clean.base.*
import com.architecture.clean.base.hide
import com.architecture.clean.base.provideViewModel
import com.architecture.clean.base.show
import com.architecture.clean.databinding.ActivityEmployeeDetailsBinding
import com.architecture.clean.di.component.DaggerEmployeeComponent
import com.architecture.clean.view.activity.base.BaseActivity
import com.architecture.presentation.model.EmployeeModel
import com.architecture.presentation.viewmodel.base.Status
import com.architecture.presentation.viewmodel.employee.EmployeeViewModel
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