package com.mp3.neulbo.shop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.mp3.neulbo.ShopActivity


import com.mp3.neulbo.*
import org.w3c.dom.Text

class BookActivity : AppCompatActivity() {

    private lateinit var goback: ImageButton

    private lateinit var item1: ImageButton
    private lateinit var item2: ImageButton
    private lateinit var item3: ImageButton
    private lateinit var points: TextView
    var auth : FirebaseAuth? = null

    var myRef = FirebaseDatabase.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_book)
        points=findViewById(R.id.pointText)
        auth = Firebase.auth
        val uid = auth?.currentUser?.uid

        getPointValue(uid.toString()) { currentValue ->

            points.setText(currentValue+"points")
        }




        goback=findViewById(R.id.goBack)

        item1 = findViewById(R.id.item1Image)
        item2 = findViewById(R.id.item2Image)
        item3 = findViewById(R.id.item3Image)


        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, ShopActivity::class.java)
            startActivity(intentSend)
            finish()
        }



    }
    fun getPointValue(userId: String, callback: (String?) -> Unit) {
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