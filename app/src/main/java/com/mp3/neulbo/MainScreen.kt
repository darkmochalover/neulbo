package com.mp3.neulbo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.ImageButton
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mp3.neulbo.databinding.ActivityMainBinding
import kotlinx.coroutines.delay

class MainScreen : AppCompatActivity() {
//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseUser mFirebaseUser;

    private lateinit var add_button: ImageButton
    private lateinit var calendar_button: ImageButton
    private lateinit var message: ImageButton
    private lateinit var points:TextView
    private lateinit var paper_plane: ImageView
    private lateinit var plane: Animation
    private lateinit var plane_2: Animation
    private var testFragment=Message()

    var auth : FirebaseAuth? = null

    var myRef = FirebaseDatabase.getInstance().reference


//    top title
    private lateinit var button_shop: ImageButton
    private lateinit var button_stampBox: ImageButton
    private lateinit var profile: ImageButton


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)

        //현재 로그인한 사용자 가져오기
        val user = Firebase.auth.currentUser

//
//        //User 얻어옴
//        mFirebaseAuth = FirebaseAuth.getInstance()
//        mFirebaseUser = mFirebaseAuth.getCurrentUser()


        //데이터베이스 Root를 가져옴
        /*
        val myRef = database.getReference("message")
        myRef.setValue(binding.etInput.text.toString()) -> 데이터 1개가 계속 수정되는 방식
        myRef.push().setValue(binding.etInput.text.toString()) -> 데이터가 계속 쌓이는 방식
         */
        var database = Firebase.database
        var myRef = database.getReference("User")


        add_button = findViewById(R.id.add)
        calendar_button=findViewById(R.id.calendar)
        paper_plane=findViewById(R.id.paper_plane)
        message=findViewById(R.id.message)
        points=findViewById(R.id.pointText)
        auth = Firebase.auth
        var uid = auth?.currentUser?.uid


        getPointValue(uid.toString()) { currentValue ->

            points.setText(currentValue+"points")
        }


        plane=AnimationUtils.loadAnimation(this,R.anim.save_diary)
        plane_2=AnimationUtils.loadAnimation(this,R.anim.receive)

//        top title
        button_shop = findViewById(R.id.shop)
        button_stampBox = findViewById(R.id.stampBox)
        profile=findViewById(R.id.profile)


        if(intent.hasExtra("login")) {
            message.startAnimation(plane_2)
        }

        paper_plane.visibility= View.INVISIBLE
        if(intent.hasExtra("diaryText")){
            paper_plane.visibility= View.VISIBLE
            paper_plane.startAnimation(plane)
            paper_plane.visibility= View.INVISIBLE

        }

        //일기 추가 버튼
        add_button.setOnClickListener {
            val intent = Intent(this, DiaryActivity::class.java)
            startActivity(intent)
            intent.putExtra("buttonVisible", message.isVisible)
            finish()

        }
        //캘린더 버튼
        calendar_button.setOnClickListener{
            val intent = Intent(this, calendar::class.java)
            startActivity(intent)
            intent.putExtra("buttonVisible", message.isVisible)
            finish()
        }
        //비행기 버튼
        message.setOnClickListener{
            message.isVisible=false
            message.setClickable(false)
            supportFragmentManager.beginTransaction()
                .replace(R.id.message_fragment, testFragment)
                .addToBackStack(null)
                .commit()
        }


        //상점 버튼
        button_shop.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
            finish()
        }
        //우표 박스
        button_stampBox.setOnClickListener {
            val intent = Intent(this, StampBoxActivity::class.java)
            startActivity(intent)
            finish()
        }
        //프로필
        profile.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
            finish()
        }



    }

    private fun getPointValue(userId: String, callback: (String?) -> Unit) {
        val pointRef = myRef.child("user").child(userId).child("point")
        pointRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentValue = dataSnapshot.getValue(Int::class.java)?.toString()
                callback(currentValue)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}