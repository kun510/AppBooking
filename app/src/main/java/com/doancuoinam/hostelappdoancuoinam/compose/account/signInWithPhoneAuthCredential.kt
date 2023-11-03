package com.doancuoinam.hostelappdoancuoinam.compose.account

import android.content.Context
import android.widget.Toast
import com.doancuoinam.hostelappdoancuoinam.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential

fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, context: Context) {
    val auth = FirebaseAuth.getInstance()
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, R.string.VerificationSuccessful, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, R.string.VerificationFailed, Toast.LENGTH_SHORT).show()
            }
        }
}
