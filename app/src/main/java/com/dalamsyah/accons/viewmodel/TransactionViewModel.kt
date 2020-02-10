package com.dalamsyah.accons.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dalamsyah.accons.model.Transaction
import com.google.firebase.firestore.FirebaseFirestore


/**
 * Created by Dimas Alamsyah on 1/18/2020
 */
class TransactionViewModel : ViewModel(){

    lateinit var listener : Listener
    interface Listener{
        fun loadData(transactions : ArrayList<Transaction>)
    }
    private lateinit var db : FirebaseFirestore

    private val __transaction = MutableLiveData<Transaction>()
    val transaction: LiveData<Transaction>
        get() = __transaction

    private val __notif = MutableLiveData<String>()
    val notif: LiveData<String>
        get() = __notif

    companion object {
        private const val TAG = "TransactionViewModel"
    }

    init {
        db = FirebaseFirestore.getInstance()
    }

    fun onSave( transaction: Transaction ){
        Log.d(TAG, transaction?.email )

        db.collection("transactions")
            .add(transaction)
            .addOnSuccessListener { documentReference ->

                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                __notif.value = "DocumentSnapshot added with ID: ${documentReference.id}"

            }
            .addOnFailureListener { e ->

                Log.w(TAG, "Error adding document", e)
                __notif.value = "Error adding document $e "

            }

    }
    fun retrieve(listener:Listener) : ArrayList<Transaction>{

        var list : ArrayList<Transaction> = ArrayList<Transaction>()

        val data = db.collection("transactions")
        data.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.toObject(Transaction::class.java)

                    list.add(data)

                    Log.d(TAG, "${document.id} => ${document.data}")
                }

                listener.loadData(list)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        return list
    }

}