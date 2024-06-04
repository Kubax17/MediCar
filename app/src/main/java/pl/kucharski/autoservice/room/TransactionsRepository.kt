package pl.kucharski.autoservice.room

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import pl.kucharski.autoservice.TransactionsDao
import java.util.*

class TransactionsRepository(context: Context) : TransactionsDao {


    private val dao = MyDatabase.getInstance(context).TransactionsDao()

    override fun getAllExpenses(userID: Int): Flow<List<Double>> {
        return dao.getAllExpenses(userID)
    }


    override  fun getRecentTransactions(tUserID: Int, date: Date):  Flow<List<Transaction>> {
        return dao.getRecentTransactions(tUserID,date)
    }
    override suspend fun insertTransaction(transaction: Transaction) = withContext(Dispatchers.IO) {
        dao.insertTransaction(transaction)
    }


    override suspend fun updateTransaction(transaction: Transaction) = withContext(Dispatchers.IO) {
        dao.updateTransaction(transaction)
    }

    override suspend fun deleteTransaction(transaction: Transaction) = withContext(Dispatchers.IO){
        dao.deleteTransaction(transaction)
    }

    override fun getAllTransactions(tUserID:Int): Flow<List<Transaction>> {
        return  dao.getAllTransactions(tUserID)
    }

    override  fun getTransactionsByMonth(month: String):  Flow<List<Transaction>>{
        return dao.getTransactionsByMonth(month)
    }

    override  fun getTransactionsByPeriod(
        startDate: Date,
        endDate: Date
    ):  Flow<List<Transaction>>{
        return dao.getTransactionsByPeriod(startDate,endDate)
    }
}