package com.uli.todo.ui.onBoard

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.uli.todo.R
import com.uli.todo.base.BaseFragment
import com.uli.todo.databinding.FragmentOnBoardBinding
import com.uli.todo.ui.App
import com.uli.todo.ui.onBoard.adapter.OnBoardAdapter

class OnBoardFragment :
    BaseFragment<FragmentOnBoardBinding>(FragmentOnBoardBinding::inflate),
    OnBoardAdapter.FinishCallback {
    private lateinit var adapter: OnBoardAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun setupUI() {
        adapter = OnBoardAdapter(this)
        binding.boardPager.adapter = adapter
        initGoogleSignClient()
    }

    private fun initGoogleSignClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        auth = Firebase.auth
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                account.idToken?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                Log.e("ololo", e.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    controller.navigateUp()
                } else {
                    Log.e("ololo", task.exception.toString())
                }
            }
    }

    private fun singIn() {
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }


    override fun close() {
        App.prefs.saveBoardState()
        singIn()
    }


    companion object {
        private const val RC_SIGN_IN = 9001
    }
}