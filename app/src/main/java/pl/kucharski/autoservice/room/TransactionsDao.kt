package pl.kucharski.autoservice

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.kucharski.autoservice.room.Transaction
import java.util.*


@Dao
interface TransactionsDao {


    @Query("SELECT price FROM  transactions_table WHERE tUserID = :userID")
    fun getAllExpenses(userID : Int) : Flow<List<Double>>

    @Update
    suspend fun updateTransaction (transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction (transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions_table  WHERE tUserID = :tUserID ORDER BY transactionID ASC")
    fun getAllTransactions(tUserID: Int) : Flow<List<Transaction>>

    @Query("SELECT * FROM transactions_table WHERE date BETWEEN :startDate AND :endDate")
    fun getTransactionsByPeriod(startDate: Date, endDate: Date): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions_table WHERE strftime('%Y-%m', date) = :month")
    fun getTransactionsByMonth(month: String): Flow<List<Transaction>>


    @Query("SELECT * FROM transactions_table WHERE tUserID = :tUserID AND  date <= :date ORDER BY date DESC ")
    fun getRecentTransactions(tUserID : Int, date: Date): Flow<List<Transaction>>
}