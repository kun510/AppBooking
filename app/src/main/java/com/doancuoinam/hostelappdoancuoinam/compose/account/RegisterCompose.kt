package com.doancuoinam.hostelappdoancuoinam.compose.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doancuoinam.hostelappdoancuoinam.R
import com.doancuoinam.hostelappdoancuoinam.compose.account.ui.theme.CustomDimensions
import com.doancuoinam.hostelappdoancuoinam.compose.account.ui.theme.HostelAppDoanCuoiNamTheme
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit



class RegisterCompose : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HostelAppDoanCuoiNamTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    RegisterScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val isTextVisible by remember { mutableStateOf(false) }
    var verificationId by remember { mutableStateOf("") }
    var isRegistering by remember { mutableStateOf(false )}
    val context = LocalView.current.context
    val otpRegisterViewModel: OtpRegisterViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(CustomDimensions.size16dp)
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(CustomDimensions.size150dp)
                .wrapContentHeight()
                .padding(bottom = CustomDimensions.size10dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                stringResource(id = R.string.createAcc),
                color = Color.Black,
                fontSize = CustomDimensions.size16sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = CustomDimensions.size20dp)
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                placeholder = {
                    Text(
                        stringResource(id = R.string.enter_your_number_phone),
                        Modifier.height(CustomDimensions.size18dp)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CustomDimensions.size100dp)
                    .padding(
                        top = CustomDimensions.size40dp,
                        start = CustomDimensions.size20dp,
                        end = CustomDimensions.size20dp
                    )

            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = {
                    Text(
                        stringResource(id = R.string.enter_your_name),
                        Modifier.height(CustomDimensions.size18dp)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CustomDimensions.size100dp)
                    .padding(
                        top = CustomDimensions.size40dp,
                        start = CustomDimensions.size20dp,
                        end = CustomDimensions.size20dp
                    )
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text(
                        stringResource(id = R.string.enter_your_password),
                        Modifier.height(CustomDimensions.size18dp)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CustomDimensions.size100dp)
                    .padding(
                        top = CustomDimensions.size40dp,
                        start = CustomDimensions.size20dp,
                        end = CustomDimensions.size20dp
                    )
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = {
                    Text(
                        stringResource(id = R.string.configpass),
                        Modifier.height(CustomDimensions.size18dp)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CustomDimensions.size100dp)
                    .padding(
                        top = CustomDimensions.size40dp,
                        start = CustomDimensions.size20dp,
                        end = CustomDimensions.size20dp
                    )
            )
            Text(
                text = stringResource(id = R.string.do_you_already_have_an_account),
                textAlign = TextAlign.End,
                color = Color(0xFF009688),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = CustomDimensions.size20dp)
            )
            if (isTextVisible) {
                Text(
                    text = stringResource(id = R.string.LogError),
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(CustomDimensions.size20dp)
                )
            }


            Button(
                onClick = {
                    if (!isRegistering ) {
                        if (context is Activity) {
                            if (password == confirmPassword) {
                                isRegistering  = true
                                otpRegisterViewModel.phoneNumber = phone
                                otpRegisterViewModel.verificationId = verificationId
                                otpRegisterViewModel.password = password
                                val auth = FirebaseAuth.getInstance()
                                val options = PhoneAuthOptions.newBuilder(auth)
                                    .setPhoneNumber(phone)
                                    .setTimeout(60L, TimeUnit.SECONDS)
                                    .setActivity(context)
                                    .setCallbacks(object :
                                        PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                                            isRegistering  = false
                                            Toast.makeText(context,"ok", Toast.LENGTH_SHORT).show()
                                            signInWithPhoneAuthCredential(p0,context)
                                        }

                                        override fun onVerificationFailed(p0: FirebaseException) {
                                            isRegistering  = false
                                            Toast.makeText(context,"no ok", Toast.LENGTH_SHORT).show()
                                        }
                                        override fun onCodeSent(veriId: String, token: PhoneAuthProvider.ForceResendingToken) {
                                            verificationId = veriId
                                            if (confirmPassword == password) {
                                                val intent = Intent(context, OtpRegisterActivity::class.java)
                                                context.startActivity(intent)
                                            } else {
                                                Toast.makeText(context, R.string.VerificationFailed, Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                    }).build()
                                PhoneAuthProvider.verifyPhoneNumber(options)
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
                Text(text = stringResource(id = R.string.btnsignup))
            }

            Spacer(modifier = Modifier.height(CustomDimensions.size10dp))

            if (isRegistering) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(CustomDimensions.size40dp)
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = CustomDimensions.size10dp)
                )
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    HostelAppDoanCuoiNamTheme {
        RegisterScreen()
    }
}

