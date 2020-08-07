package com.architecture.presentation.viewmodel.employee

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.architecture.domain.interactor.UseCaseResult
import com.architecture.domain.interactor.employee.GetEmployeeListUseCase
import com.architecture.presentation.model.EmployeeModel
import com.architecture.presentation.model.mapper.EmployeeModelDataMapper
import com.architecture.presentation.viewmodel.base.DataWrapper
import javax.inject.Inject

class EmployeeListViewModel @Inject constructor(application: Application)
    : AndroidViewModel(application) {

    @Inject
    lateinit var getEmployeeListUseCase: GetEmployeeListUseCase

    @Inject
    lateinit var employeeModelDataMapper: EmployeeModelDataMapper

    private var mEmployeeListLiveDate: MutableLiveData<DataWrapper<List<EmployeeModel>>>? = null

    //**********************************************************************************************
    fun getEmployeeList() : LiveData<DataWrapper<List<EmployeeModel>>> {

        if (mEmployeeListLiveDate == null) {
            mEmployeeListLiveDate = MutableLiveData()
            mEmployeeListLiveDate!!.value = DataWrapper.loading()

            getEmployeeListUseCase.execute(null) { useCaseResult ->

                when (useCaseResult) {
                    is UseCaseResult.Success -> {
                        val serverResponse = useCaseResult.data

                        if (serverResponse.isSuccess()) {
                            mEmployeeListLiveDate!!.value = DataWrapper.success(
                                employeeModelDataMapper.transform(serverResponse.data)
                            )
                        }
                        else {
                            mEmployeeListLiveDate!!.value = DataWrapper.error(
                                Throwable(serverResponse.message), serverResponse.message
                            )
                        }

                    }

                    is UseCaseResult.Error -> {
                        mEmployeeListLiveDate!!.value = DataWrapper.error(useCaseResult.throwable)
                    }
                } //End of when

            } //End of execute

        }

        return mEmployeeListLiveDate!!
    }

    //**********************************************************************************************
    override fun onCleared() {
        super.onCleared()

        if (::getEmployeeListUseCase.isInitialized) {
            getEmployeeListUseCase.dispose()
        }

    }

}