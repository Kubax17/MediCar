package pl.kucharski.autoservice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OpinionsAdapter(private val opinionsList: List<String>) :
    RecyclerView.Adapter<OpinionsAdapter.OpinionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpinionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_opinion, parent, false)
        return OpinionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OpinionViewHolder, position: Int) {
        val currentOpinion = opinionsList[position]
        holder.opinionTextView.text = currentOpinion
    }

    override fun getItemCount() = opinionsList.size

    class OpinionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val opinionTextView: TextView = itemView.findViewById(R.id.opinionTextView)
    }
}
