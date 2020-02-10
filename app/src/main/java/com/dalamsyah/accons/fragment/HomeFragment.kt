package com.dalamsyah.accons.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

import com.dalamsyah.accons.R
import com.dalamsyah.accons.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // [END config_signin]

        googleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }!!

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // [END initialize_auth]

        var email = HomeFragmentArgs.fromBundle(arguments!!).email
        binding.tvEmail.text = email

        binding.btnSignOut.setOnClickListener {

            auth.signOut()

            // Google sign out
            googleSignInClient.signOut().addOnCompleteListener {
                updateUI(null)
            }

        }

        binding.btnInput.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_homeFragment_to_historyInputFragment)
            //NavHostFragment.findNavController(this).navigate(  HomeFragmentDirections.actionHomeFragmentToHistoryInputFragment())

            //findNavController().navigate( HomeFragmentDirections.actionHomeFragmentToHistoryInputFragment() )
        }

        binding.btnList.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_homeFragment_to_listHistoryFragment)
        }

        return binding.root
    }

    private fun updateUI(user: FirebaseUser?) {
        //hideProgressDialog()
        if (user != null) {

        } else {

            findNavController().navigate( HomeFragmentDirections.actionRestart("") )

        }
    }


}
