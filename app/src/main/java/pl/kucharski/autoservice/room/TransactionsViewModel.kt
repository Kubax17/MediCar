package pl.kucharski.autoservice.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.viewModelScope
import pl.kucharski.autoservice.ProductType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TransactionsViewModel(app:Application) : AndroidViewModel(app)


{
    private val repo = TransactionsRepository(app.applicationContext)


    suspend   fun addTransactionToDatabase(userID:Int, title: String, price: Double, productType: ProductType, date: Date){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val transaction = Transaction(userID, title,price,productType, date)
                repo.insertTransaction(transaction)
            }
        }
    }

    suspend fun deleteTransaction(transaction: Transaction){
        repo.deleteTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction){
        repo.updateTransaction(transaction)
    }

    suspend fun getTransactionsByMonth(month: String): Flow<List<Transaction>> {
        return  repo.getTransactionsByMonth(month)
    }

    suspend fun getTransactionsByPeriod(startDate : Date, endDate : Date) : Flow<List<Transaction>> {
        return repo.getTransactionsByPeriod(startDate,endDate)
    }


    suspend fun getAllTransactions(userID : Int): Flow<List<Transaction>>{
        return repo.getAllTransactions(userID)
    }

    fun fetchTransactionPrice(userID: Int) : Flow<List<Double>> {
        return repo.getAllExpenses(userID)
    }

}