package com.doancuoinam.hostelappdoancuoinam.compose.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
class OtpRegisterViewModel : ViewModel() {
    var phoneNumber by mutableStateOf("")
    var verificationId by mutableStateOf("")
    var password by mutableStateOf("")

    fun setUserData(phone: String, verification: String, pwd: String) {
        phoneNumber = phone
        verificationId = verification
        password = pwd
    }
}
