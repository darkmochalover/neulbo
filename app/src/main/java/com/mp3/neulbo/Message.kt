package com.mp3.neulbo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mp3.neulbo.databinding.FragmentMessageBinding

class Message : Fragment() {
    lateinit var testBinding: FragmentMessageBinding
    private lateinit var check: ImageButton
    private lateinit var text: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testBinding = FragmentMessageBinding.inflate(inflater, container, false)
        val view = testBinding.root

        text = view.findViewById(R.id.diary_view)
        auth = Firebase.auth


        val otherUserRef = myRef.child("user")

        otherUserRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val keys = mutableListOf<String?>()
                for (snapshot in dataSnapshot.children) {
                    val userId: String? = snapshot.key ?: ""
                    Log.d("Tag", "User ID: $userId")

                    userId?.let {
                        keys.add(userId)
                    }
                }
                val randomIndex = Random.nextInt(keys.size)
                val randomKey = keys[randomIndex]
                Log.d("Tag", "User ID random: $randomKey")



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

                        Log.d("Tag", "Value: $values")
                        values.removeLast()
                        Log.d("Tag", "Value: $values")

                        val randomContent = Random.nextInt(values.size)
                        val randomValue = values[randomContent]
                        Log.d("Tag", "User ID random: $randomValue")
                        val contentValue = randomValue?.toString()?.substringAfter("content=")?.substringBefore("}")
                        Log.d("Tag", "User ID random: $contentValue")
                        text.text = contentValue
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


        check = view.findViewById(R.id.check)
        check.setOnClickListener {
            Log.d("ButtonClick", "Check button clicked")
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().remove(this@Message).commit()
        }
        check.isClickable = true

        return view
    }
}
