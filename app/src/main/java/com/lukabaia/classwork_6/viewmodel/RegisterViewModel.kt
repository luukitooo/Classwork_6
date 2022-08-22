package com.lukabaia.classwork_6.viewmodel

import androidx.lifecycle.ViewModel
import com.lukabaia.classwork_6.model.RegisterResult
import com.lukabaia.classwork_6.model.UserInfo
import com.lukabaia.classwork_6.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class RegisterViewModel: ViewModel() {

    private val _registerFlow = MutableSharedFlow<RegisterResult?>()
    val registerFlow get() = _registerFlow.asSharedFlow()

    suspend fun getRegisterResult(userInfo: UserInfo) {

        val response = RetrofitInstance.getAuthApi().getRegisterResult(userInfo)

        if (response.isSuccessful && response.body() != null)
            _registerFlow.emit(response.body())
        else
            _registerFlow.emit(null)

    }

}