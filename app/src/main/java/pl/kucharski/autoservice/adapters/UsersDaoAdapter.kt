package pl.kucharski.autoservice

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.kucharski.autoservice.room.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class UsersDaoAdapter (private var listOfUsers: Flow<List<User>>): RecyclerView.Adapter<UsersDaoAdapter.MyViewHolder> () {

    private var usersList: List<User> = emptyList()
    init {

        GlobalScope.launch {

            listOfUsers.collect {users ->
                usersList = users
                notifyDataSetChanged()

            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.user_item, parent, false)
        return MyViewHolder(row)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.uidTextView.text = usersList[position].userID.toString()
        holder.usernameTextView.text = usersList[position].username
        holder.passwordTextView.text = usersList[position].password

    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val usernameTextView: TextView = view.findViewById(R.id.text_view_username)
        val passwordTextView: TextView = view.findViewById(R.id.text_view_password)
        val uidTextView: TextView = view.findViewById(R.id.text_view_uid)

    }


    //A to po co w sumie jest? xD

    fun updateList(users: Flow<List<User>>) {
        listOfUsers = users
        notifyDataSetChanged()
    }
}