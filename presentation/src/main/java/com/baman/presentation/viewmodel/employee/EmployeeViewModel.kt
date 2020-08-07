package com.baman.presentation.viewmodel.employee

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.baman.domain.interactor.UseCaseResult
import com.baman.domain.interactor.employee.GetEmployeeDetailsUseCase
import com.baman.presentation.model.EmployeeModel
import com.baman.presentation.model.mapper.EmployeeModelDataMapper
import com.baman.presentation.viewmodel.base.DataWrapper
import javax.inject.Inject

class EmployeeViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    @Inject
    lateinit var getEmployeeDetailsUseCase: GetEmployeeDetailsUseCase

    @Inject
    lateinit var employeeModelDataMapper: EmployeeModelDataMapper

    //**********************************************************************************************
    fun getEmployeeDetails(employeeId: Long): LiveData<DataWrapper<EmployeeModel>> {
        val liveData = MutableLiveData<DataWrapper<EmployeeModel>>()
        liveData.value = DataWrapper.loading()

        val params = GetEmployeeDetailsUseCase.Params(employeeId)

        getEmployeeDetailsUseCase.execute(params) { useCaseResult ->

            when (useCaseResult) {

                is UseCaseResult.Success -> {
                    val serverResponse = useCaseResult.data

                    if (serverResponse.isSuccess()) {
                        liveData.value = DataWrapper.success(
                            employeeModelDataMapper.transform(serverResponse.data)
                        )
                    }
                    else {
                        liveData.value = DataWrapper.error(
                            Throwable(serverResponse.message), serverResponse.message
                        )
                    }

                }

                is UseCaseResult.Error -> {
                    liveData.value = DataWrapper.error(useCaseResult.throwable)
                }

            } //End of when

        } //End of execute

        return liveData
    }

    //**********************************************************************************************
    override fun onCleared() {
        super.onCleared()

        if (::getEmployeeDetailsUseCase.isInitialized) {
            getEmployeeDetailsUseCase.dispose()
        }

    }
}