package pl.kucharski.autoservice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OptionsAdapter(private val options: List<String>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<OptionsAdapter.NormalViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(option: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_option, parent, false)
        return NormalViewHolder(view)
    }

    override fun onBindViewHolder(holder: NormalViewHolder, position: Int) {
        val option = options[position]
        holder.bind(option)
        holder.itemView.setOnClickListener {
            listener.onItemClick(option)
        }
    }

    override fun getItemCount(): Int = options.size

    inner class NormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.optionTextView)

        fun bind(option: String) {
            textView.text = option
        }
    }
}
