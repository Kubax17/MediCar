package pl.kucharski.autoservice

import android.annotation.SuppressLint


import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context


import android.widget.TextView

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.Gravity
import android.view.ViewGroup
import android.widget.*

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.kucharski.autoservice.ProductType

import pl.kucharski.autoservice.adapters.TransactionsAdapter

import pl.kucharski.autoservice.room.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class HomeActivity : AppCompatActivity(){


    private lateinit var  drawerLayout : DrawerLayout
    private lateinit var  btnMenu : ImageView
    private lateinit var  recyclerView : RecyclerView
    private lateinit var  transactionDao : TransactionsDao
    private lateinit var  viewModel: TransactionsViewModel
    private lateinit var transactionRecyclerView : RecyclerView
    private lateinit var transactionsAdapter: TransactionsAdapter
    private lateinit var totalExpenseVal : TextView
    private lateinit var totalBalanceVal : TextView
    private lateinit var budgetVal : TextView

    private lateinit var adapter : HomeAdapter




    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val userSharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE)
        val userSharedPreferences2 = getSharedPreferences("Preferences", Context.MODE_PRIVATE)


        val username = userSharedPreferences.getString("username", "")
        val userID = userSharedPreferences.getInt("userID", -1)
        val welcomeUser = findViewById<TextView>(R.id.welcome_user)

        val budget = userSharedPreferences2.getFloat("budget", 0.0f)
        val currency = userSharedPreferences.getString("currency","PLN")
        val dateFormat = userSharedPreferences2.getString("dateFormat", "dd/MM/yyyy")


        welcomeUser.text = "Welcome, $username!"

        budgetVal = findViewById(R.id.total_budget)
        totalExpenseVal = findViewById(R.id.total_expense)
        totalBalanceVal = findViewById(R.id.balance)
        drawerLayout = findViewById(R.id.drawer_layout)
        btnMenu = findViewById(R.id.menu_burger)
        recyclerView = findViewById(R.id.recycler_view)

        //baza danych transakcji
        val transactionsDb = MyDatabase.getInstance(context = applicationContext)
        transactionDao = transactionsDb.TransactionsDao()
        viewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]
        transactionRecyclerView = findViewById(R.id.lasttransaction_recycler_view)
        transactionRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        //  transactionRecyclerView.addItemDecoration(LastThreeTransactionsDecoration())


        updateFinancialData(userID, budget, currency)
        //tutaj kod drawera
        arrayList.clear()

        arrayList.add("Main menu")
        arrayList.add("User preferences")
        arrayList.add("Settings")
        arrayList.add("About")
        arrayList.add("Logout")

        adapter = HomeAdapter(this, arrayList)


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        //BurgerButton listener;
        btnMenu.setOnClickListener()
        {

            drawerLayout.openDrawer(GravityCompat.START)

        }

        //bottom menu listener
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_menu_home)

        bottomNav.setOnItemSelectedListener {




                item ->
            when (item.itemId) {
                R.id.add_action -> {
                    // Handle add action
                    showTransactionDetailsDialog(userID, budget, currency, dateFormat)

                    true
                }
                R.id.transaction_history -> {
                    // Handle transaction history
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.payments -> {
                    val intent = Intent(this, PaymentActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.calendar -> {
                    val intent = Intent(this, CalendarActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }

        }



        lifecycleScope.launch(Dispatchers.Main) {
            showLastTransactions(userID,dateFormat,currency)

        }


    }

    override fun onPause() {
        super.onPause()
        closeDrawer(drawerLayout)
    }

    companion object {
        val arrayList = ArrayList<String>()

        fun closeDrawer(drawerLayout: DrawerLayout) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

    }

    @SuppressLint("SimpleDateFormat")
    private fun showTransactionDetailsDialog(userID: Int, budget:Float, currency:String?, dateFormat: String?) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_add_action)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.CENTER)

        //zapewnienie aby itemy wybierane z listy były typu ProductType
        //----------------------------------
        val productTypes: List<ProductType> = ProductType.values().toList()

        //simple_spinner_item oraz simple_spinner_dropdown_item to wbudowane layouty powiązane ze spinnerem
        val productTypeSpinner = dialog.findViewById<Spinner>(R.id.producttype_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, productTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        productTypeSpinner.adapter = adapter
        //----------------------------------



        var selectedDate  = Date()
        val dateButton = dialog.findViewById<ImageView>(R.id.dateButton)

        dateButton.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val year = currentDate.get(Calendar.YEAR)
            val month = currentDate.get(Calendar.MONTH)
            val dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, selectedYear)
                calendar.set(Calendar.MONTH, selectedMonth)
                calendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)

                selectedDate = calendar.time







            }, year, month, dayOfMonth)
            datePickerDialog.show()
        }

        val addExpenseButton = dialog.findViewById<Button>(R.id.btn_add_expense)
        addExpenseButton.setOnClickListener {
            val titleEditText = dialog.findViewById<EditText>(R.id.titleEditText)
            val priceEditText = dialog.findViewById<EditText>(R.id.priceEditText)
            val productTypeSpinner = dialog.findViewById<Spinner>(R.id.producttype_spinner)

            val title = titleEditText.text.toString()
            val priceText = priceEditText.text.toString()

            if (priceText.isNotEmpty()) {

                //   val normalizedPriceText = priceText.replace(",", ".")
                try {

                    val price = priceText.toFloat()
                    val roundOff = (price * 100.0).roundToInt() / 100.0
                    val productType = productTypeSpinner.selectedItem as ProductType




                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        viewModel.addTransactionToDatabase(
                            userID,
                            title,
                            roundOff,
                            productType,
                            selectedDate
                        )


                    }

                    dialog.dismiss()
                    lifecycleScope.launch(Dispatchers.Main) {
                        showLastTransactions(userID, dateFormat, currency)

                    }
                    updateFinancialData(userID, budget, currency )
                    Toast.makeText(this, "Expense added!", Toast.LENGTH_SHORT).show()
                    // Update the UI with the latest transactions


                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show()
                }




            } else {
                Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()

    }


    private suspend fun showLastTransactions(userID: Int, dateFormat: String?, currency: String?) {
        val transactions = viewModel.getAllTransactions(userID)

        transactionsAdapter = TransactionsAdapter(transactions, dateFormat, currency)
        transactionRecyclerView.adapter = transactionsAdapter
        transactionRecyclerView.requestLayout()


    }




    private fun updateFinancialData(userID: Int, budget: Float, currency : String?) {
        var sum = 0.0

        val givenBudget = (budget * 100.0).roundToInt() / 100.0
        val expenseList = viewModel.fetchTransactionPrice(userID)
        lifecycleScope.launch(Dispatchers.Default) {
            expenseList.collect { list ->
                for (price in list) {
                    sum += price
                }
            }

        }

        lifecycleScope.launch(Dispatchers.Main) {
            delay(3000L)

            //czy to można zrobić lepiej korzystając z Flow lub Deferred albo Async?

            val sumRoundOff = (sum * 100.0).roundToInt() / 100.0
            val budgetRoundOff = (givenBudget * 100.0).roundToInt() / 100.0




            val totalBalance = budgetRoundOff - sumRoundOff
            val finalTotalBalance = (totalBalance * 100.0).roundToInt() / 100.0
            val expText = "$sumRoundOff $currency"
            val balText = "$finalTotalBalance $currency"
            val budgetText = "$budgetRoundOff $currency"
            budgetVal.text = budgetText
            totalExpenseVal.text = expText
            totalBalanceVal.text = balText
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.viewModelScope.cancel()

    }

}