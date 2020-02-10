package com.dalamsyah.accons.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.dalamsyah.accons.R
import com.dalamsyah.accons.databinding.FragmentHistoryInputBinding
import com.dalamsyah.accons.lib.DADialog
import com.dalamsyah.accons.lib.DASession
import com.dalamsyah.accons.model.Transaction
import com.dalamsyah.accons.viewmodel.TransactionViewModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import com.google.firestore.v1.DocumentTransform

/**
 * A simple [Fragment] subclass.
 */
class HistoryInputFragment : Fragment() {

    companion object {
        private const val TAG = "HistoryInputFragment"
    }

    private lateinit var viewModel: TransactionViewModel
    private lateinit var dialog: DADialog.DialoagOne


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentHistoryInputBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_input, container, false);

        viewModel = ViewModelProviders.of(this).get(TransactionViewModel::class.java)
        binding.lifecycleOwner = this
        dialog = context?.let { DADialog.DialoagOne(it) }!!

        viewModel.notif.observe(this, Observer { notif ->
            Toast.makeText(context, notif, Toast.LENGTH_SHORT).show()

            dialog.hideProgress()

        })

        binding.btnSubmit.setOnClickListener {

            var email = activity?.let { it1 -> DASession(it1).get("email") }

            var now = FieldValue.serverTimestamp().toString();

            dialog.show();
            viewModel.onSave(
                Transaction(email, binding.etIn.text.toString().toDouble(), "in","noted", "" )
            )

        }



        return binding.root
    }


}
