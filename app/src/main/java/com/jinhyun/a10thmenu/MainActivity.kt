package com.jinhyun.a10thmenu

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //현재 날짜 구하기

        val calendar = Calendar.getInstance()

        val yearnow = calendar.get(Calendar.YEAR)
        val monthnow = calendar.get(Calendar.MONTH)
        val daynow = calendar.get(Calendar.DAY_OF_MONTH)

        var changingcalendar = calendar
        changingcalendar.set(yearnow, monthnow, daynow)

        var newmonth = monthnow + 1

        var selectdate = "$yearnow. $newmonth. $daynow."

        dateprintText.text = selectdate

        insertData(selectdate)

        //오늘로 가기 버튼

        todayButton.setOnClickListener{
            newmonth = monthnow + 1
            changingcalendar.set(yearnow, monthnow, daynow)

            selectdate = "$yearnow. $newmonth. $daynow."

            dateprintText.text = selectdate

            insertData(selectdate)
        }

        //날짜 선택 버튼

        calendarButton.setOnClickListener{
            val daypick = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                    view, year, month, day ->

                changingcalendar.set(year, month, day)

                newmonth = month + 1

                selectdate = "$year. $newmonth. $day."

                dateprintText.text = selectdate

                insertData(selectdate)
            }, yearnow, monthnow, daynow)

            daypick.show()
        }

        //하루 전으로 가기 버튼

        minusButton.setOnClickListener {
            changingcalendar.add(Calendar.DATE, -1)

            var yearchanging = changingcalendar.get(Calendar.YEAR)
            var monthchanging = changingcalendar.get(Calendar.MONTH)
            var daychanging = changingcalendar.get(Calendar.DAY_OF_MONTH)

            newmonth = monthchanging + 1

            selectdate = "$yearchanging. $newmonth. $daychanging."

            dateprintText.text = selectdate

            insertData(selectdate)
        }

        //하루 후로 가기 버튼

        plusButton.setOnClickListener {
            changingcalendar.add(Calendar.DATE, 1)

            var yearchanging = changingcalendar.get(Calendar.YEAR)
            var monthchanging = changingcalendar.get(Calendar.MONTH)
            var daychanging = changingcalendar.get(Calendar.DAY_OF_MONTH)

            newmonth = monthchanging + 1

            selectdate = "$yearchanging. $newmonth. $daychanging."
            dateprintText.text = selectdate

            insertData(selectdate)
        }

    }

    //메뉴 데이터 넣기

    private fun insertData(date : String) {
        val menulist = ArrayList<MainData>()

        //아침 -> 점심 -> 저녁 -> 출력

        insertBreakfast(menulist, date)

        Log.d(TAG, "data inserted!")
    }

    private fun insertBreakfast(menulist : ArrayList<MainData>, date : String){

        val breakfastRef = db.collection("Menu").document(date).collection("Mealtype")
            .document("breakfast")
        breakfastRef.get().addOnSuccessListener { document ->
            if (document.data != null) {
                val meal1 = document.getString("1").toString()
                val meal2 = document.getString("2").toString()
                val meal3 = document.getString("3").toString()
                val meal4 = document.getString("4").toString()
                val meal5 = document.getString("5").toString()
                val meal6 = document.getString("6").toString()
                menulist += MainData("아침", meal1, meal2, meal3, meal4, meal5, meal6)
                insertLunch(menulist, date)
            } else {
                Log.d(TAG, "No such document")
                menulist += MainData("아침", "", "", "",
                    "", "", "")
                insertLunch(menulist, date)
            }
        }

    }

    private fun insertLunch(menulist : ArrayList<MainData>, date : String){
        val lunchRef = db.collection("Menu").document(date).collection("Mealtype")
            .document("lunch")
        lunchRef.get().addOnSuccessListener { document ->
            if (document.data != null) {
                val meal1 = document.getString("1").toString()
                val meal2 = document.getString("2").toString()
                val meal3 = document.getString("3").toString()
                val meal4 = document.getString("4").toString()
                val meal5 = document.getString("5").toString()
                val meal6 = document.getString("6").toString()
                menulist += MainData("점심",  meal1, meal2, meal3, meal4, meal5, meal6)
                insertDinner(menulist, date)
            } else {
                Log.d(TAG, "No such document")
                menulist += MainData("점심", "", "", "",
                    "", "", "")
                insertDinner(menulist, date)
            }
        }
    }

    private fun insertDinner(menulist : ArrayList<MainData>, date : String){
        val dinnerRef = db.collection("Menu").document(date)
            .collection("Mealtype")
            .document("dinner")
        dinnerRef.get().addOnSuccessListener { document ->
            if (document.data != null) {
                val meal1 = document.getString("1").toString()
                val meal2 = document.getString("2").toString()
                val meal3 = document.getString("3").toString()
                val meal4 = document.getString("4").toString()
                val meal5 = document.getString("5").toString()
                val meal6 = document.getString("6").toString()
                menulist += MainData("저녁", meal1, meal2, meal3, meal4, meal5, meal6)

                menuRecyclerView.adapter = ItemAdapter(menulist)
                menuRecyclerView.layoutManager = LinearLayoutManager(this)
                menuRecyclerView.setHasFixedSize(true)
            } else {
                Log.d(TAG, "No such document")
                menulist += MainData("저녁", "", "", "",
                    "", "", "")

                menuRecyclerView.adapter = ItemAdapter(menulist)
                menuRecyclerView.layoutManager = LinearLayoutManager(this)
                menuRecyclerView.setHasFixedSize(true)
            }
        }
    }
}
