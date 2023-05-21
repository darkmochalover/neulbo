package com.mp3.neulbo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.mp3.neulbo.databinding.ActivitySignInBinding


class SignIn : AppCompatActivity() {

    private lateinit var binding:ActivitySignInBinding
    private lateinit var firebaseAuth:FirebaseAuth

    private lateinit var googleLoginButton: SignInButton
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

            firebaseAuth=FirebaseAuth.getInstance()
            binding.signUpTxt.setOnClickListener {
            val intent=Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        // 이메일로 로그인
        binding.signInButton.setOnClickListener {
            val emailEt=binding.emailEt.text.toString()
            val passwordEt=binding.passwordEt.text.toString()

            if (emailEt.isNotEmpty()&&passwordEt.isNotEmpty())
            {
                firebaseAuth.signInWithEmailAndPassword(emailEt,passwordEt).addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        val intent=Intent(this, MainScreen::class.java)
                        intent.putExtra("login","successful")
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,"You have not Registered Yet..Please Sign UP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this,"Please Enter All Fields", Toast.LENGTH_SHORT).show()
            }
        }

        // 구글로 로그인
        binding.googleLoginButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent

            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        // 구글 로그인 설정
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 구글 로그인 결과 처리
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "Logged in as ${user.email}", Toast.LENGTH_SHORT).show()

            val intent = Intent(applicationContext, MainScreen::class.java)
            intent.putExtra("UserName", user.email)
//            intent.putExtra("PhotoUrl", user.getPhotoUrl())
            startActivity(intent)
        } else {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show()
        }
    }
}