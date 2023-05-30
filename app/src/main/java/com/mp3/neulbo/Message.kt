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

        text=text.findViewById(R.id.diary_view)

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
