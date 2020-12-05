package com.jinhyun.a10thmenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val TAG = "LoginActivitiy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ownerbutton.setOnClickListener{
            showAlert()
        }

        userbutton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showAlert() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.alert_popup, null)
        var id : EditText = view.findViewById(R.id.idInputText)
        var password : EditText = view.findViewById(R.id.passwordInputText)

        var pref = this.getPreferences(0)
        id.setText(pref.getString("id",""))
        password.setText(pref.getString("password", ""))

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("관리자 로그인")
            .setPositiveButton("로그인"){dialog, which ->
                if(id.text.toString().isNotBlank() && password.text.toString().isNotBlank()){
                    if(id.text.toString() == "ehwlsgus93" && password.text.toString() == "123456"){
                        var editor = this.getPreferences(0).edit()

                        editor.putString("id","ehwlsgus93").apply()
                        editor.putString("password", "123456").apply()

                        val intent = Intent(this, OwnerActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "정보가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("취소", null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }
}