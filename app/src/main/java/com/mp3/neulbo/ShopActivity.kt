package com.mp3.neulbo

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

import com.mp3.neulbo.shop.*

class ShopActivity : AppCompatActivity() {

    private lateinit var goback: ImageButton

    private lateinit var button_book: ImageButton
    private lateinit var button_emotion: ImageButton
    private lateinit var button_envelopes: ImageButton
    private lateinit var button_font: ImageButton
    private lateinit var button_origami: ImageButton
    private lateinit var button_background: ImageButton
    private lateinit var button_stationery: ImageButton
    private lateinit var button_wallpaper: ImageButton
    private lateinit var points: TextView
    var auth : FirebaseAuth? = null

    var myRef = FirebaseDatabase.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)


        goback=findViewById(R.id.goBack)

        // item list
        button_book = findViewById(R.id.bookImage)
        button_emotion = findViewById(R.id.emotionImage)
        button_envelopes = findViewById(R.id.envelopesImage)
        button_font = findViewById(R.id.fontImage)
        button_origami = findViewById(R.id.origamiImage)
        button_background = findViewById(R.id.backgroundsImage)
        button_stationery = findViewById(R.id.stationeryImage)
        button_wallpaper = findViewById(R.id.wallpaperImage)
        points=findViewById(R.id.pointText)

        auth = Firebase.auth
        val uid = auth?.currentUser?.uid

        getPointValue(uid.toString()) { currentValue ->

            points.setText(currentValue+"points")
        }


        button_book.setOnClickListener {
            val intentSend = Intent(this, BookActivity::class.java)
            startActivity(intentSend)
            finish()
        }

        button_background.setOnClickListener {
            val intentSend = Intent(this, BackgroundActivity::class.java)
            startActivity(intentSend)
            finish()
        }

        button_origami.setOnClickListener {
            val intentSend = Intent(this, OrgamiActivity::class.java)
            startActivity(intentSend)
            finish()
        }

        button_stationery.setOnClickListener {
            val intentSend = Intent(this, StationeryActivity::class.java)
            startActivity(intentSend)
            finish()
        }

        button_font.setOnClickListener {
            val intentSend = Intent(this, FontActivity::class.java)
            startActivity(intentSend)
            finish()
        }

        button_wallpaper.setOnClickListener {
            val intentSend = Intent(this, WallpaperActivity::class.java)
            startActivity(intentSend)
            finish()
        }

        button_envelopes.setOnClickListener {
            val intentSend = Intent(this, EnvelopeActivity::class.java)
            startActivity(intentSend)
            finish()
        }

        button_emotion.setOnClickListener {
            val intentSend = Intent(this, EmotionActivity::class.java)
            startActivity(intentSend)
            finish()
        }





        //뒤로가기 버튼
        goback.setOnClickListener {
            val intentSend = Intent(this, MainScreen::class.java)
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