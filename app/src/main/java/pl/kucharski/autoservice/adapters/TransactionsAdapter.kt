package pl.kucharski.autoservice.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import pl.kucharski.autoservice.R
import pl.kucharski.autoservice.room.Transaction
import java.text.SimpleDateFormat
import java.util.*

class TransactionsAdapter(private var listOfTransactions: Flow<List<Transaction>>,
                          private var dateFormat : String?,
                          private var currency : String?
): RecyclerView.Adapter<TransactionsAdapter.MyViewHolder> () {


    private val limit : Int = 3

    private var transactionsList: List<Transaction> = emptyList()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            listOfTransactions.collect { transactions ->
                withContext(Dispatchers.Main) {
                    transactionsList = transactions
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val reversedPosition = transactionsList.size - 1 - position
        holder.titleTextView.text = transactionsList[reversedPosition].title
        val priceString = transactionsList[reversedPosition].price.toString() + "$currency"
        holder.priceTextView.text = priceString
        val dateString = transactionsList[reversedPosition].date
        val format = SimpleDateFormat("$dateFormat", Locale.ENGLISH)
        val formattedDate = format.format(dateString)
        holder.dateTextView.text = formattedDate

        holder.typeTextView.text = transactionsList[reversedPosition].productType.toString()

    }

    override fun getItemCount(): Int {

        return minOf(transactionsList.size, limit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.transaction_item, parent, false)
        return MyViewHolder(row)
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val titleTextView: TextView = view.findViewById(R.id.text_view_tTitle)
        val priceTextView: TextView = view.findViewById(R.id.text_view_tPrice)
        val dateTextView: TextView = view.findViewById(R.id.text_view_tDate)
        val typeTextView: TextView = view.findViewById(R.id.text_view_tType)
    }
}