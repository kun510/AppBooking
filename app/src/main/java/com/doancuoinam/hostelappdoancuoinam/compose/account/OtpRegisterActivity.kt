package com.doancuoinam.hostelappdoancuoinam.compose.account



import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doancuoinam.hostelappdoancuoinam.Model.Request.RegisterRequest
import com.doancuoinam.hostelappdoancuoinam.Model.Response.Response
import com.doancuoinam.hostelappdoancuoinam.Service.ApiClient
import com.doancuoinam.hostelappdoancuoinam.Service.ApiService
import com.doancuoinam.hostelappdoancuoinam.compose.account.ui.theme.CustomDimensions
import com.doancuoinam.hostelappdoancuoinam.compose.account.ui.theme.HostelAppDoanCuoiNamTheme
import com.doancuoinam.hostelappdoancuoinam.compose.account.ui.theme.teal700
import com.doancuoinam.hostelappdoancuoinam.test.LoginSmS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class OtpRegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OtpRegisterScreen()
        }
    }
}

@Composable
fun OtpRegisterScreen() {
    var otp1 by remember { mutableStateOf("") }
    var otp2 by remember { mutableStateOf("") }
    var otp3 by remember { mutableStateOf("") }
    var otp4 by remember { mutableStateOf("") }
    var otp5 by remember { mutableStateOf("") }
    var otp6 by remember { mutableStateOf("") }
    var isProcessBar by remember { mutableStateOf(false )}
    val otpRegisterViewModel: OtpRegisterViewModel = viewModel()
    val phoneNumber = otpRegisterViewModel.phoneNumber
    val verificationId = otpRegisterViewModel.verificationId
    val password = otpRegisterViewModel.password
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val context = LocalView.current.context

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .offset(0.dp, 50.dp),
    ) {
        Text(
            text = "Mã OTP",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp)
        )

        Text(
            text = "Hãy nhập mã gửi về số điện thoại",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )

        Text(
            text = "phoneotp",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            val singleInputModifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
            repeat(1) {
                BasicTextField(
                    value = otp1,
                    onValueChange = {
                        otp1 = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = singleInputModifier.then(
                        Modifier
                            .size(150.dp, 60.dp)
                            .drawBehind {
                                drawLine(
                                    color = teal700,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2f
                                )
                            }
                    )
                )
                BasicTextField(
                    value = otp2,
                    onValueChange = {
                        otp2 = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = singleInputModifier.then(
                        Modifier
                            .size(150.dp, 60.dp)
                            .drawBehind {
                                drawLine(
                                    color = teal700,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2f
                                )
                            }
                    )
                )
                BasicTextField(
                    value = otp3,
                    onValueChange = {
                        otp3 = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = singleInputModifier.then(
                        Modifier
                            .size(150.dp, 60.dp)
                            .drawBehind {
                                drawLine(
                                    color = teal700,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2f
                                )
                            }
                    )
                )
                BasicTextField(
                    value = otp4,
                    onValueChange = {
                        otp4 = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = singleInputModifier.then(
                        Modifier
                            .size(150.dp, 60.dp)
                            .drawBehind {
                                drawLine(
                                    color = teal700,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2f
                                )
                            }
                    )
                )
                BasicTextField(
                    value = otp5,
                    onValueChange = {
                        otp5 = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = singleInputModifier.then(
                        Modifier
                            .size(150.dp, 60.dp)
                            .drawBehind {
                                drawLine(
                                    color = teal700,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2f
                                )
                            }
                    )
                )
                BasicTextField(
                    value = otp6,
                    onValueChange = {
                        otp6 = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = singleInputModifier.then(
                        Modifier
                            .size(150.dp, 60.dp)
                            .drawBehind {
                                drawLine(
                                    color = teal700,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2f
                                )
                            }
                    )
                )
            }
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    val code = otp1 + otp2 + otp3 + otp4 + otp5 + otp6
                    if(verificationId != null){
                        isProcessBar = true
                        val phoneAuthCredential =
                            PhoneAuthProvider.getCredential(verificationId, code)
                            FirebaseAuth.getInstance().setLanguageCode("Vi")
                            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        val phoneAPI = phoneNumber
                                        val passwordAPI = password
                                        val response = performRegistration(phoneAPI, passwordAPI)
                                        if (response != null){
                                            val message = response.getMessage()
                                            when(message){
                                                "Register successfully" -> {
                                                    scaffoldState.snackbarHostState.showSnackbar("Register successfully")
                                                    val intent = Intent(context, LoginSmS::class.java)
                                                    Toast.makeText(
                                                        context,
                                                        "Đăng Nhập Thành Công",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    context.startActivity(intent)
                                                }
                                                "User with the provided phone already exists." -> {
                                                    scaffoldState.snackbarHostState.showSnackbar("User with the provided phone already exists. $message")
                                                }
                                                else -> {
                                                    scaffoldState.snackbarHostState.showSnackbar("Register Fail $message")
                                                }
                                            }
                                        } else {
                                            scaffoldState.snackbarHostState.showSnackbar("HTTP Error")
                                        }
                                    }

                                } else {
                                    Toast.makeText(context, "Otp sai", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                    }

                }
            },
            modifier = Modifier
                .width(CustomDimensions.size336dp)
                .height(CustomDimensions.size100dp)
                .padding(
                    top = CustomDimensions.size40dp,
                    start = CustomDimensions.size24dp,
                    end = CustomDimensions.size20dp
                )
        ) {
            Text(
                text = "Đăng nhập",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                color = Color.White
            )
        }

        if (isProcessBar){
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } 

        Text(
            text = "resetOtp",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)

        )
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HostelAppDoanCuoiNamTheme {
        OtpRegisterScreen()
    }
}
suspend fun performRegistration(phoneApi: String, passwordApi: String): Response? {
    return suspendCoroutine { continuation ->
        val apiService = ApiClient.getClient().create(ApiService::class.java)
        val registerRequest = RegisterRequest(phoneApi, passwordApi)

        val call = apiService.register(registerRequest)
        call.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    continuation.resume(registerResponse)
                } else {
                    continuation.resume(null)
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                continuation.resume(null)
            }
        })
    }
}