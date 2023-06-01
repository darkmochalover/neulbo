package com.mp3.neulbo


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.EditText
import android.view.View

import androidx.fragment.app.Fragment

import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import kotlin.random.Random
import java.text.SimpleDateFormat
import java.util.*



import com.mp3.neulbo.databinding.FragmentMessageBinding


class Message : Fragment() {
    lateinit var testBinding: FragmentMessageBinding
    private lateinit var check: ImageButton
    private lateinit var text: TextView

    private lateinit var edit: EditText

    var auth : FirebaseAuth? = null
    var myRef = FirebaseDatabase.getInstance().reference

    var otherUserRef = myRef.child("user")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testBinding = FragmentMessageBinding.inflate(inflater, container, false)
        val view = testBinding.root

        text = view.findViewById(R.id.diary_view)
        auth = Firebase.auth


        var randomKey: String? = ""
        var randomContent: String? = ""

        otherUserRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val keys = mutableListOf<String?>()
                for (snapshot in dataSnapshot.children) {
                    val userId: String? = snapshot.key ?: ""
//                    Log.d("Tag", "User ID: $userId")

                    userId?.let {
                        keys.add(userId)
                    }
                }
                val randomIndex = Random.nextInt(keys.size)
                randomKey = keys[randomIndex]
//                Log.d("Tag", "User ID random: $randomKey")



                val userRef = myRef.child("user").child(randomKey.toString())

                userRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val keys = mutableListOf<String?>()
                        val values = mutableListOf<Any?>()

                        for (snapshot in dataSnapshot.children) {
                            val key: String? = snapshot.key
                            val value: Any? = snapshot.value

                            key?.let {
                                keys.add(it)
                            }

                            value?.let {
                                values.add(it)
                            }
                        }

                        Log.d("Tag", "Key: $keys")
                        keys.removeLast()
                        Log.d("Tag", "Value: $values")
                        values.removeLast()
//                        Log.d("Tag", "Value: $values")

                        if (values.size > 0) {
                            val randomct = Random.nextInt(values.size)
                            val randomValue = values[randomct]
                            randomContent = keys[randomct]
                            Log.d("Tag", "User ID random: $randomValue")
                            Log.d("Tag", "User ID random: $randomContent")
                            val contentValue = randomValue?.toString()?.substringAfter("content=")?.substringBefore("}")
                            Log.d("Tag", "User ID random: $contentValue")
                            text.text = contentValue
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        // 데이터를 읽어오는 도중에 오류가 발생했을 때 처리하는 부분
                    }
                })


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터를 읽어오는 도중에 오류가 발생했을 때 처리하는 부분
            }
        })


        edit = view.findViewById(R.id.commentText)
        check = view.findViewById(R.id.check)
        check.setOnClickListener {
            val input = edit!!.text.toString()

            val currentTime = getCurrentDateTime()
            if (randomKey != null) {
                if (randomContent != null) {
                    writeDiary(randomKey!!, randomContent!!, input, currentTime)
                }
            }

            Log.d("ButtonClick", "Check button clicked")
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().remove(this@Message).commit()
        }
        check.isClickable = true

        return view
    }

    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
    private fun writeDiary(otherId: String, otherContent: String, comment: String, Date:String){
        val diary = Comment(comment, Date)
        Log.d("Tag", "User ID random: 11 $diary")
        otherUserRef.child(otherId).child(otherContent).push().setValue(diary)
    }
}
