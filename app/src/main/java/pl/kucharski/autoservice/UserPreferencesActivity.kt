package pl.kucharski.autoservice

import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class UserPreferencesActivity : AppCompatActivity() {


    private lateinit var  btnConfirm: Button
    private lateinit var  btnReset : Button
    private lateinit var  drawerLayout : DrawerLayout
    private lateinit var  btnMenu : ImageView
    private lateinit var  recyclerView : RecyclerView
    private lateinit var selDateFormat: EditText
    private lateinit var selCurrency: EditText
    private lateinit var selBudget: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_preferences)

        btnConfirm = findViewById(R.id.button_confirm)
        btnReset = findViewById(R.id.button_reset)
        selDateFormat = findViewById(R.id.sel_date_format)
        selCurrency = findViewById(R.id.sel_currency_field)
        selBudget = findViewById(R.id.sel_budget_field)

        drawerLayout  = findViewById(R.id.drawer_layout)
        btnMenu = findViewById(R.id.menu_burger)
        recyclerView  = findViewById(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HomeAdapter(this, HomeActivity.arrayList)
        btnMenu.setOnClickListener() {



            drawerLayout.openDrawer(GravityCompat.START)

        }


        btnConfirm.setOnClickListener() {


            if (selBudget.text.isNullOrBlank() || selCurrency.text.isNullOrBlank() || selDateFormat.text.isNullOrBlank()) {
                Toast.makeText(this, "Required fields must be filled", Toast.LENGTH_SHORT).show()
            } else {
                val dateFormat: String = selDateFormat.text.toString()
                try {
                    val sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("currency", selCurrency.text.toString())
                    editor.putString("dateFormat",
                        dateFormat)
                    editor.putFloat("budget", selBudget.text.toString().toFloat())
                    editor.apply()


                    SimpleDateFormat(dateFormat, Locale.getDefault())

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } catch (e: IllegalArgumentException) {
                    Toast.makeText(this, "Invalid date format: $dateFormat", Toast.LENGTH_SHORT).show()
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Invalid budget value", Toast.LENGTH_SHORT).show()
                }
            }



        }

        btnReset.setOnClickListener() {


            val sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("currency", "PLN")
            editor.putString("dateFormat",
                "dd/MM/yyyy")
            editor.putFloat("budget", 0.0f)
            editor.apply()




            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        }

    }

    override fun onPause() {
        super.onPause()
        HomeActivity.closeDrawer(drawerLayout)
    }
}