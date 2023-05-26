package com.mp3.neulbo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.FragmentManager
import com.mp3.neulbo.databinding.FragmentMessageBinding


class Message : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var testBinding : FragmentMessageBinding
    private lateinit var check:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testBinding = FragmentMessageBinding.inflate(inflater, container, false)

        val view = inflater.inflate(R.layout.fragment_message, container, false)
        check=view.findViewById(R.id.check)
        check.setOnClickListener{
            Log.d("ButtonClick", "Check button clicked")
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction().remove(this@Message).commit()
        }
        check.isClickable = true


        return testBinding.root
    }



}