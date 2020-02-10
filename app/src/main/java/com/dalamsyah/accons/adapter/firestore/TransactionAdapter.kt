package com.dalamsyah.accons.adapter.firestore

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dalamsyah.accons.R
import com.dalamsyah.accons.model.Transaction
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class TransactionAdapter(private val list: List<Transaction>)
    : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TransactionViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = list[position]
        holder.bind(transaction)
    }

    class TransactionViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_transaction, parent, false)) {

        private var tvNominal: TextView? = null
        private var tvDate: TextView? = null

        init {
            tvNominal = itemView.findViewById(R.id.tvNominal)
            tvDate = itemView.findViewById(R.id.tvDate)
        }

        fun bind(transaction: Transaction) {
            tvNominal?.text = transaction.nominal.toString()
            tvDate?.text = transaction.timestamp.toString()
        }

    }

}


