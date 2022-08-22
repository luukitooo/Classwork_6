package com.lukabaia.classwork_6.viewmodel

import androidx.lifecycle.ViewModel
import com.lukabaia.classwork_6.model.LoginResult
import com.lukabaia.classwork_6.model.UserInfo
import com.lukabaia.classwork_6.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class LoginViewModel: ViewModel() {

    private val _loginFlow = MutableSharedFlow<LoginResult?>()
    val loginFlow get() = _loginFlow.asSharedFlow()

    suspend fun getLoginResult(userInfo: UserInfo) {

        val response = RetrofitInstance.getAuthApi().getLoginResult(userInfo)

        if (response.isSuccessful && response.body() != null)
            _loginFlow.emit(response.body())
        else
            _loginFlow.emit(null)

    }

}