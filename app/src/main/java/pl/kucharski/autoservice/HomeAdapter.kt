package pl.kucharski.autoservice

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import pl.kucharski.autoservice.LoginActivity

class HomeAdapter :  RecyclerView.Adapter<HomeAdapter.ViewHolder>
{

    private  var activity: Activity
    var arrayList = ArrayList<String>()



    constructor (activity:Activity, arrayList : ArrayList<String> ) {
        this.activity = activity
        this.arrayList = arrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //initialize view
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_drawer_prototype, parent, false)

        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //set text on textView
        val  pos : Int = holder.adapterPosition //pos == position
        val item = arrayList[pos]
        holder.textView.text = item
        holder.textView.setOnClickListener() {






            when(item) {
                "Main menu" ->
                    activity.startActivity(Intent(activity,HomeActivity::class.java )
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                "User preferences" ->
                    activity.startActivity(Intent(activity, UserPreferencesActivity::class.java )
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                "Settings"->
                {
                    val intent = Intent(activity, SettingsActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                activity.startActivity(intent)
            }
                "About"->
                {
                    val intent = Intent(activity, OpinionsActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    activity.startActivity(intent)
                }
                "Logout"-> {

                    val loginIntent = Intent(activity, LoginActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    activity.finish()
                    activity.startActivity(loginIntent)
                }
            }


        }
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView: TextView = itemView.findViewById(R.id.text_view_item_drawer)


    }

}