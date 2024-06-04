package pl.kucharski.autoservice

import android.content.Context
import kotlinx.coroutines.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import pl.kucharski.autoservice.room.UsersDatabase

import android.content.SharedPreferences
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.kucharski.autoservice.room.MyDatabase
import pl.kucharski.autoservice.room.UsersDao
import pl.kucharski.autoservice.room.UsersViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var mUsername: EditText
    private lateinit var mPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var mForgetPassword: TextView
    private lateinit var mSignUpHere : TextView
    private lateinit var mShowUsersList : TextView
    private lateinit var userDao: UsersDao
    private lateinit var viewModel: UsersViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        //inicjalizacja bazy danych

        val db = MyDatabase.getInstance(context = applicationContext)



        mUsername = findViewById(R.id.username_login)
        mPass = findViewById(R.id.password_login)
        btnLogin = findViewById(R.id.btn_login)
        mForgetPassword = findViewById(R.id.forgot_password)
        mSignUpHere = findViewById(R.id.signup_reg)
        mShowUsersList = findViewById(R.id.show_users_list)

        userDao = db.UsersDao()
        viewModel = ViewModelProvider(this)[UsersViewModel::class.java]

        //login button functionality
        btnLogin.setOnClickListener {
            if (mUsername.text.isNullOrBlank() || mPass.text.isNullOrBlank()) {
                Toast.makeText(this, "Required fields must be filled", Toast.LENGTH_SHORT).show()
            } else if(!mUsername.text.isNullOrBlank() &&  !mPass.text.isNullOrBlank()) {

                lifecycleScope.launch {
                    if (viewModel.doesUserExists(mUsername.text.toString()) && viewModel.isPasswordCorrect(mUsername.text.toString(),mPass.text.toString())) {

                        Toast.makeText(this@LoginActivity, "${mUsername.text}, you're logged in!", Toast.LENGTH_SHORT).show()

                        val userSharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE)
                        val editor = userSharedPreferences.edit()
                        val userID = viewModel.getUserID(mUsername.text.toString())
                        editor.putString("username", mUsername.text.toString())
                        editor.putInt("userID", userID)
                        editor.apply()

                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)

                        startActivity(intent)

                    }
                    else if (!viewModel.doesUserExists(mUsername.text.toString())) {

                        Toast.makeText(this@LoginActivity, "Username does not exist!", Toast.LENGTH_SHORT).show()

                    }

                    else if (viewModel.doesUserExists(mUsername.text.toString()) && !viewModel.isPasswordCorrect(mUsername.text.toString(),mPass.text.toString()) ) {
                        Toast.makeText(this@LoginActivity, "Invalid password!", Toast.LENGTH_SHORT).show()
                    }

                }



            }
        }
        //registration
        mSignUpHere.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))

        }
        //when you forgot your password
        mForgetPassword.setOnClickListener {

            startActivity(Intent(this,ResetActivity::class.java))
        }
        // wyświetlenie listy użytkowników
        mShowUsersList.setOnClickListener {
            startActivity(Intent(this,ShowUsersListActivity::class.java))

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.viewModelScope.cancel()
        val usersDb = UsersDatabase.DatabaseBuilder.getInstance(applicationContext)
        usersDb.clearAllTables()
        UsersDatabase.DatabaseBuilder.deleteInstanceOfDatabase()


    }


}

