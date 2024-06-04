/*
Aktywność, w której wyświetlana jest lista użytkowników
 */
package pl.kucharski.autoservice

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText

import androidx.activity.ComponentActivity

import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.kucharski.autoservice.room.UsersViewModel



class ShowUsersListActivity : ComponentActivity() {
    private lateinit var viewModel: UsersViewModel
    private lateinit var recyclerView: RecyclerView




    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_users_list)
        viewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        val adapter = UsersDaoAdapter(viewModel.getAllUsers())
        recyclerView = findViewById(R.id.user_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter  = adapter






    }






}

