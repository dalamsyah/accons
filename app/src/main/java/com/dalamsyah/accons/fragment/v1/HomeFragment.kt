package com.dalamsyah.accons.fragment.v1


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController

import com.dalamsyah.accons.R
import com.dalamsyah.accons.databinding.FragmentHome2Binding
import com.dalamsyah.accons.lib.DASession

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHome2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home2, container, false);

        binding.ivSetting.setOnClickListener {
            activity?.let { DASession(it).setValue("email", "" ) }
            val action = HomeFragmentDirections.actionHomeFragment2ToLoginFragment2("")
            findNavController(this).navigate(action)
        }

        return binding.root
    }


}
