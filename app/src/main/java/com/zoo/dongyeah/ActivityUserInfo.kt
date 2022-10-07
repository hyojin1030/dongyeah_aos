package com.zoo.dongyeah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import com.zoo.dongyeah.databinding.ActivityUserInfoBinding

class ActivityUserInfo : AppCompatActivity() {
    val TAG = "com.zoo.dongyeah"
    private val firebase = Firebase.firestore
    private lateinit var binding: ActivityUserInfoBinding

    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserEmail()

    }

    private fun setUserEmail() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}")


                userEmail = user.kakaoAccount?.email.toString()
                binding.editEmail.setText(userEmail)

            }
        }
    }

    fun onClickSave(view: View) {
        if (binding.editEmail.text.isEmpty() || binding.editUserName.text.isEmpty() || binding.editPetName.text.isEmpty()
            || binding.editPetAge.text.isEmpty() || binding.editPetGender.text.isEmpty()) {
            Toast.makeText(this, "데이터를 입력해주세요.", Toast.LENGTH_LONG).show()
        }
        else {
            insertData()
        }
    }

    private fun insertData() {
        val user = hashMapOf(
            "userName" to binding.editUserName.text.toString(),
            "petName" to binding.editPetName.text.toString(),
            "petGender" to binding.editPetGender.text.toString(),
            "petAge" to binding.editPetAge.text.toString()
        )

        firebase.collection("UserInfo").document(userEmail).set(user)
            .addOnSuccessListener{ documentRefurence ->
                Log.d(TAG, "success")

                val intent = Intent(this, ActivityMain::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { error -> Log.e(TAG, "Failed", error) }
    }
}