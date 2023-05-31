package com.mp3.neulbo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mp3.neulbo.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding
    private lateinit var firebaseAuth:FirebaseAuth
    var auth : FirebaseAuth? = null

    var myRef = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()
        auth = Firebase.auth

//        val signUpButton=findViewById<AppCompatButton>(R.id.signUpButton)
        binding.signUpButton.setOnClickListener {

            val email=binding.emailSignupEt.text.toString()
            val password=binding.passwordSignupEt.text.toString()
            val confirmPassword=binding.confirmPasswordSignupEt.text.toString()

            if(email.isNotEmpty()&&password.isNotEmpty()&&confirmPassword.isNotEmpty())
            {
                if(password==confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if (it.isSuccessful){
                            var uid = auth?.currentUser?.uid.toString()
                            myRef.child("user").child(uid).child("point").setValue(0)
                            val intent= Intent(this, SignIn::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()

                        }
                    }
                }
                else
                {
                    Toast.makeText(this,"Password is not matching",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"Empty Fields Are Not Allowed!!",Toast.LENGTH_SHORT).show()
            }



        }
    }
}