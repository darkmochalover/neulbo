package com.mp3.neulbo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mp3.neulbo.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {

    private lateinit var binding:ActivitySignInBinding
    private lateinit var firebaseAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

            firebaseAuth=FirebaseAuth.getInstance()
            binding.signUpTxt.setOnClickListener {
            val intent=Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.signInButton.setOnClickListener {
            val emailEt=binding.emailEt.text.toString()
            val passwordEt=binding.passwordEt.text.toString()

            if (emailEt.isNotEmpty()&&passwordEt.isNotEmpty())
            {
                firebaseAuth.signInWithEmailAndPassword(emailEt,passwordEt).addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        val intent=Intent(this, MainScreen::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"You have not Registered Yet..Please Sign UP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
            {
                Toast.makeText(this,"Please Enter All Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}