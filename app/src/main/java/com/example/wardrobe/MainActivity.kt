package com.example.wardrobe


import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wardrobe.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        setCustomToolbar(R.id.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


//        appBarConfiguration = AppBarConfiguration(navController.graph)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment,R.id.wardrobeFragment,R.id.communityFragment, R.id.codiFragment, R.id.LogoutFragment),
            binding.mainDrawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

//
        binding.mainNavigationView.setupWithNavController(navController)
//        binding.toolbar.setupWithNavController(navController,appBarConfiguration)


//        setCustomToolbar(R.id.main_toolbar)
        // 네비게이션 메뉴를 초기화
//        initNavigationMenu()
        signInAnonymously()

    }

    private fun signInAnonymously() {
        // [START signin_anonymously]
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInAnonymously:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
        // [END signin_anonymously]
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_nav_menu_list,menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun setCustomToolbar(layout: Int){
        val toolbar = findViewById<MaterialToolbar>(layout)
        // 커스텀 툴바를 액션바로 설정
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        // 액션바에서 앱 이름 보이지 않게 제거
        actionBar?.setDisplayShowTitleEnabled(false)
    }


    // 네비게이션 메뉴 초기화
//    private fun initNavigationMenu(){
//        val drawerLayout = binding.mainDrawerLayout
//        val navView = binding.mainNavigationView
//
//        navView.setNavigationItemSelectedListener(this)
//
//        // 네비게이션 아이콘에 클릭 이벤트 연결
//        val navMenu = binding.mainToolbar.hamburgerbar
//        navMenu.setOnClickListener {
//            drawerLayout.openDrawer(GravityCompat.START)
//        }
//
//        // 네비게이션 헤더 메뉴에 클릭 이벤트 연결
//        val headerView = navView.getHeaderView(0)
//        // drawer 닫기 버튼
//        val closeButton = headerView.findViewById<ImageView>(R.id.hamburgerbar)
//        closeButton.setOnClickListener {
//            drawerLayout.closeDrawer(GravityCompat.START)
//        }
//    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.emptyFragment -> navController.navigate(R.id.action_testFragment_to_mainFragment)
//            R.id.testFragment -> navController.navigate(R.id.action_mainFragment_to_testFragment)
//        }
//        return false
//    }
    public override fun onStart() {
        super.onStart()
    // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
}
    private fun updateUI(user: FirebaseUser?) {
    }
}
