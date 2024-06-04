package pl.kucharski.autoservice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import pl.kucharski.autoservice.room.UsersViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {

    private lateinit var mUsername: EditText
    private lateinit var mPass: EditText
    private lateinit var btnReg: Button
    private lateinit var mSignIn: TextView
    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        viewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application)
            .create(UsersViewModel::class.java)

        registration()
    }

    private fun registration() {
        mUsername = findViewById(R.id.username_reg)
        mPass = findViewById(R.id.password_reg)
        btnReg = findViewById(R.id.register_button)
        mSignIn = findViewById(R.id.already_have_account)

        btnReg.setOnClickListener{
            if( mUsername.text.isNullOrBlank() || mPass.text.isNullOrBlank() ) {

                Toast.makeText(this,"Required fields must be filled", Toast.LENGTH_SHORT).show()

            }

            else if(!mUsername.text.isNullOrBlank() && !mPass.text.isNullOrBlank()) {



                viewModel.viewModelScope.launch {
                    if (!viewModel.doesUserExists(mUsername.text.toString())) {

                        Toast.makeText(
                            this@RegistrationActivity,
                            "${mUsername.text}, you're logged in!",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.insertUserToDatabase(mUsername.text.toString(), mPass.text.toString())
                        val userSharedPreferences =
                            getSharedPreferences("Prefs", Context.MODE_PRIVATE)
                        val editor = userSharedPreferences.edit()
                        val userID = viewModel.getUserID(mUsername.text.toString())
                        editor.putInt("userID",userID)
                        editor.putString("username", mUsername.text.toString())
                        editor.apply()

                        val intent = Intent(this@RegistrationActivity, HomeActivity::class.java)
                        startActivity(intent)

                    }
                    else {
                        Toast.makeText(
                            this@RegistrationActivity,
                            "Nickname already in use!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        //when you already have an account
        mSignIn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.viewModelScope.cancel()
    }
}