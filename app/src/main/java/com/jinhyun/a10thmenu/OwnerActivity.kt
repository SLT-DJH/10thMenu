package com.jinhyun.a10thmenu

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_owner.*
import java.util.*

class OwnerActivity : AppCompatActivity() {

    val TAG = "Owerctivitiy"

    val db = FirebaseFirestore.getInstance()
    val menuCollection = db.collection("Menu")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner)

        //현재 날짜 구하기

        val calendar = Calendar.getInstance()

        val yearnow = calendar.get(Calendar.YEAR)
        val monthnow = calendar.get(Calendar.MONTH)
        val daynow = calendar.get(Calendar.DAY_OF_MONTH)

        var changingcalendar = calendar
        changingcalendar.set(yearnow, monthnow, daynow)

        var newmonth = monthnow + 1

        var selectdate = "$yearnow. $newmonth. $daynow."

        dateprintownerText.text = selectdate


        //오늘로 가기 버튼

        todayownerButton.setOnClickListener{
            newmonth = monthnow + 1
            changingcalendar.set(yearnow, monthnow, daynow)

            selectdate = "$yearnow. $newmonth. $daynow."

            dateprintownerText.text = selectdate

        }

        //날짜 선택 버튼

        calendarownerButton.setOnClickListener{
            val daypick = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, month, day ->

                changingcalendar.set(year, month, day)

                newmonth = month + 1

                selectdate = "$year. $newmonth. $day."

                dateprintownerText.text = selectdate

            }, yearnow, monthnow, daynow)

            daypick.show()
        }

        //하루 전으로 가기 버튼

        minusownerButton.setOnClickListener {
            changingcalendar.add(Calendar.DATE, -1)

            var yearchanging = changingcalendar.get(Calendar.YEAR)
            var monthchanging = changingcalendar.get(Calendar.MONTH)
            var daychanging = changingcalendar.get(Calendar.DAY_OF_MONTH)

            newmonth = monthchanging + 1

            selectdate = "$yearchanging. $newmonth. $daychanging."

            dateprintownerText.text = selectdate

        }

        //하루 후로 가기 버튼

        plusownerButton.setOnClickListener {
            changingcalendar.add(Calendar.DATE, 1)

            var yearchanging = changingcalendar.get(Calendar.YEAR)
            var monthchanging = changingcalendar.get(Calendar.MONTH)
            var daychanging = changingcalendar.get(Calendar.DAY_OF_MONTH)

            newmonth = monthchanging + 1

            selectdate = "$yearchanging. $newmonth. $daychanging."
            dateprintownerText.text = selectdate

        }

        //아침메뉴 저장 버튼
        breakfastSave.setOnClickListener {
            val breakfast = hashMapOf(
                "1" to breakfastInput1.text.toString(),
                "2" to breakfastInput2.text.toString(),
                "3" to breakfastInput3.text.toString(),
                "4" to breakfastInput4.text.toString(),
                "5" to breakfastInput5.text.toString(),
                "6" to breakfastInput6.text.toString()
            )

            menuCollection.document(dateprintownerText.text.toString()).collection("Mealtype")
                .document("breakfast").set(breakfast)
                .addOnSuccessListener {
                    Toast.makeText(this, "아침메뉴가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                    val blank = ""
                    breakfastInput1.text.clear()
                    breakfastInput2.text.clear()
                    breakfastInput3.text.clear()
                    breakfastInput4.text.clear()
                    breakfastInput5.text.clear()
                    breakfastInput6.text.clear()

                }
                .addOnFailureListener{ Toast.makeText(this, "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show()}

        }

        //점심메뉴 저장 버튼
        lunchSave.setOnClickListener {
            val lunch = hashMapOf(
                "1" to lunchInput1.text.toString(),
                "2" to lunchInput2.text.toString(),
                "3" to lunchInput3.text.toString(),
                "4" to lunchInput4.text.toString(),
                "5" to lunchInput5.text.toString(),
                "6" to lunchInput6.text.toString()
            )

            menuCollection.document(dateprintownerText.text.toString()).collection("Mealtype")
                .document("lunch").set(lunch)
                .addOnSuccessListener {
                    Toast.makeText(this, "점심메뉴가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                    val blank = ""
                    lunchInput1.text.clear()
                    lunchInput2.text.clear()
                    lunchInput3.text.clear()
                    lunchInput4.text.clear()
                    lunchInput5.text.clear()
                    lunchInput6.text.clear()

                }
                .addOnFailureListener{ Toast.makeText(this, "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show()}

        }

        //저녁메뉴 저장 버튼
        dinnerSave.setOnClickListener{
            val dinner = hashMapOf(
                "1" to dinnerInput1.text.toString(),
                "2" to dinnerInput2.text.toString(),
                "3" to dinnerInput3.text.toString(),
                "4" to dinnerInput4.text.toString(),
                "5" to dinnerInput5.text.toString(),
                "6" to dinnerInput6.text.toString()
            )

            menuCollection.document(dateprintownerText.text.toString()).collection("Mealtype")
                .document("dinner").set(dinner)
                .addOnSuccessListener {
                    Toast.makeText(this, "저녁메뉴가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                    val blank = ""
                    dinnerInput1.text.clear()
                    dinnerInput2.text.clear()
                    dinnerInput3.text.clear()
                    dinnerInput4.text.clear()
                    dinnerInput5.text.clear()
                    dinnerInput6.text.clear()

                }
                .addOnFailureListener{ Toast.makeText(this, "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show()}

        }

    }
}