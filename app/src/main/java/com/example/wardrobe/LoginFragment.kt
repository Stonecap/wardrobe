package com.example.wardrobe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.wardrobe.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment: Fragment(){
    private lateinit var binding: FragmentLoginBinding
    protected lateinit var navController: NavController
    private val firebaseAuth by lazy { Firebase.auth }
    val currentUser = firebaseAuth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var navController = findNavController()
//        if(FirebaseAuth.getInstance().currentUser!=null){
//            var navGraph = navController.graph
//            navGraph.setStartDestination(R.id.homeFragment)
//            navController.graph = navGraph
//        }

        binding.loginButton.setOnClickListener {
            Log.v("Click", "로그인버튼 클릭")
            val idEditText = binding.idEditText.text.toString()
            val passwordEditText = binding.passwordEditText.text.toString()
            navController.navigate(R.id.homeFragment)
            //signIn(idEditText,passwordEditText)
        }

    }
//    public override fun onStart() {
//        super.onStart()
//
//        moveMainPage(firebaseAuth.currentUser)
//    }
//    private fun signIn(email: String, password: String) {
//        firebaseAuth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(
//                        this, "로그인에 성공 하였습니다.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    moveMainPage(firebaseAuth.currentUser)
//                } else {
//                    Toast.makeText(
//                        this, "로그인에 실패 하였습니다.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//    }
//private fun signIn(email: String, password: String) {
//    firebaseAuth.signInWithEmailAndPassword(email, password)
//        .addOnCompleteListener()
//}
    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            navController.navigate(R.id.homeFragment)
        }
    }
}

