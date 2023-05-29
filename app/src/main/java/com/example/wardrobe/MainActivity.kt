package com.example.wardrobe


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wardrobe.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setCustomToolbar(R.id.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


//        appBarConfiguration = AppBarConfiguration(navController.graph)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment,R.id.wardrobeFragment,R.id.communityFragment, R.id.codiFragment),
            binding.mainDrawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
//
        binding.mainNavigationView.setupWithNavController(navController)
//        binding.toolbar.setupWithNavController(navController,appBarConfiguration)


//        setCustomToolbar(R.id.main_toolbar)
        // 네비게이션 메뉴를 초기화
//        initNavigationMenu()

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

}
