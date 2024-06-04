package pl.kucharski.autoservice


import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.kucharski.autoservice.adapters.HistoryAdapter

import pl.kucharski.autoservice.room.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.kucharski.autoservice.room.TransactionsViewModel


class HistoryActivity : AppCompatActivity() {
    private lateinit var transactionDao: TransactionsDao
    private lateinit var transactionRecyclerView: RecyclerView
    private lateinit var transactionsAdapter: HistoryAdapter
    private lateinit var viewModel: TransactionsViewModel

    private lateinit var userSharedPreferences: SharedPreferences
    private lateinit var userSharedPreferences2: SharedPreferences
    private lateinit var username: String
    private var userID: Int = -1
    private lateinit var currency: String
    private lateinit var dateFormat: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        userSharedPreferences = getSharedPreferences("Prefs", Context.MODE_PRIVATE)
        userSharedPreferences2 = getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        username = userSharedPreferences.getString("username", "") ?: ""
        userID = userSharedPreferences.getInt("userID", -1)
        currency = userSharedPreferences.getString("currency", "PLN") ?: "PLN"
        dateFormat = userSharedPreferences2.getString("dateFormat", "dd/MM/yyyy") ?: "dd/MM/yyyy"

        transactionRecyclerView = findViewById(R.id.lasttransaction_recycler_view)
        transactionRecyclerView.layoutManager = LinearLayoutManager(applicationContext)

        val transactionsDb = MyDatabase.getInstance(context = applicationContext)
        transactionDao = transactionsDb.TransactionsDao()
        viewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]

        lifecycleScope.launch(Dispatchers.IO) {
            val transactions = viewModel.getAllTransactions(userID)
            withContext(Dispatchers.Main) {
                transactionsAdapter = HistoryAdapter(transactions, dateFormat, currency)
                transactionRecyclerView.adapter = transactionsAdapter
            }
        }
    }
}
