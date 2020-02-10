package com.dalamsyah.accons.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.dalamsyah.accons.R
import com.dalamsyah.accons.adapter.firestore.TransactionAdapter
import com.dalamsyah.accons.databinding.FragmentListHistoryBinding
import com.dalamsyah.accons.lib.DADialog
import com.dalamsyah.accons.model.Transaction
import com.dalamsyah.accons.viewmodel.TransactionViewModel

/**
 * A simple [Fragment] subclass.
 */
class ListHistoryFragment : Fragment(){

    private lateinit var viewModel: TransactionViewModel
    private lateinit var dialog: DADialog.DialoagOne
    private lateinit var binding : FragmentListHistoryBinding

    companion object {
        private const val TAG = "ListHistoryFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_history, container, false);
        viewModel = ViewModelProviders.of(this).get(TransactionViewModel::class.java)
        binding.lifecycleOwner = this
        dialog = context?.let { DADialog.DialoagOne(it) }!!

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
        }

        binding.btnRefresh.setOnClickListener {

            dialog.show()

            viewModel.retrieve( object : TransactionViewModel.Listener{
                override fun loadData(transactions: ArrayList<Transaction>) {

                    var rtn : String = "";

                    for (data in transactions ){
                        rtn += data.email.toString() + " "+ data.nominal.toString() +" \n"
                    }

                    binding.tvList.text = rtn;

                    binding.recyclerView.adapter = TransactionAdapter(transactions);

                    dialog.hideProgress()

                }
            } )

        }

        return binding.root
    }


}
