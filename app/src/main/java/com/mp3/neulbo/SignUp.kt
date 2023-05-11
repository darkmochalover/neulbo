package com.mp3.neulbo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.emailauthenticationfirebase.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding
    private lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()

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