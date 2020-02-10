package com.dalamsyah.accons.fragment.v1


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment

import com.dalamsyah.accons.R
import com.dalamsyah.accons.databinding.FragmentLogin2Binding
import com.dalamsyah.accons.fragment.LoginFragment
import com.dalamsyah.accons.fragment.LoginFragmentDirections
import com.dalamsyah.accons.lib.DASession
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val TAG = "LoginFragment"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentLogin2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login2, container, false);

        /*if(activity?.let { DASession(it).get("email").equals("") }!!){
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }!!
            auth = FirebaseAuth.getInstance()
        }else{
            DASession(activity!!).get("email")?.let { gotoNext(it) };
        }*/

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }!!
        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            signWithEmail(binding.etUsername.text.toString(), binding.etPassword.text.toString())
        }

        if(activity?.let {
                !DASession(it).get("email").equals("")
            }!!){

            val action2 = DASession(activity!!).get("email")?.let {
                com.dalamsyah.accons.fragment.v1.LoginFragmentDirections.actionLoginFragment2ToHomeFragment2(
                    it
                )
            }
            NavHostFragment.findNavController(this).navigate(action2!!)

        }

        return binding.root
    }

    private fun signWithEmail(email: String, password: String){
        Log.d(TAG, "signIn:$email")

        /*auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    updateUI(null)
                }

            }*/

        activity?.let { DASession(it).setValue("email", "dimas" ) }

        if(activity?.let {
                !DASession(it).get("email").equals("")
            }!!){

            val action2 = DASession(activity!!).get("email")?.let {
                com.dalamsyah.accons.fragment.v1.LoginFragmentDirections.actionLoginFragment2ToHomeFragment2(
                    it
                )
            }
            NavHostFragment.findNavController(this).navigate(action2!!)

        }

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //val currentUser = auth.currentUser
        //updateUI(currentUser)


    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    //Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
                //hideProgressDialog()
                // [END_EXCLUDE]
            }

    }

    private fun gotoNext(user: String){
        val action2 = com.dalamsyah.accons.fragment.v1.LoginFragmentDirections.actionLoginFragment2ToHomeFragment2(user)

        NavHostFragment.findNavController(this).navigate(action2)

        activity?.let { DASession(it).setValue("email", user ) }
    }

    private fun updateUI(user: FirebaseUser?) {
        //hideProgressDialog()
        if (user != null) {

            gotoNext(user.email.toString());

        } else {

        }
    }


}
