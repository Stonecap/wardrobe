package com.example.wardrobe

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wardrobe.adapters.HomeRecyclerViewAdapter
import com.example.wardrobe.adapters.HomeRecyclerViewAdapter2
import com.example.wardrobe.databinding.FragmentHomeBinding
import com.example.wardrobe.viewmodel.HomeViewModel
import com.example.wardrobe.viewmodel.HomeItem
<<<<<<< Updated upstream
import com.google.firebase.auth.FirebaseAuth
=======
>>>>>>> Stashed changes
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlin.text.Typography.dagger

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()
    protected lateinit var navController: NavController


    val user = Firebase.auth.currentUser
    // 회원가입 구현 시 이부분 firebase auth에서 받아올 것
<<<<<<< Updated upstream
    val user = Firebase.auth.currentUser
    val currentUID = user?.uid
=======
    val currentUID = user?.uid

>>>>>>> Stashed changes
    val db = Firebase.firestore

    // Set(코디) Collection Ref
    val setColRef = db.collection("set")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        Log.d("login_checkuid", "login ${currentUID}")

//        if(user!=null){
//            var navGraph = navController.graph
//            navGraph.setStartDestination(R.id.homeFragment)
//            navController.graph = navGraph
////            navController.navigate(R.id.action_homeFragment_to_loginFragment)
//        }
//        else{
//            var navGraph = navController.graph
//            navGraph.setStartDestination(R.id.loginFragment)
//            navController.graph = navGraph
//        }


        val adapter_weather = HomeRecyclerViewAdapter(viewModel, context, this)
        val adapter_community = HomeRecyclerViewAdapter2(viewModel, context, this)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                viewModel.fetchWeatherForecast()
            }
        }
        locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

        val weatherIcon = binding.weatherIcon
        val currentAddress = binding.currentLocation
        val currentTemperature = binding.currentTemperature

        viewModel.weatherData.observe(viewLifecycleOwner) {
            Glide.with(view)
                .load(it.type.icon.id)
                .into(weatherIcon)
            currentAddress.text = it.location
            val unit = view.context.resources.getString(R.string.celsius)
            currentTemperature.text = "${it.temperature.toInt()} ${unit}"

            loadHomeWeatherList(it.feelsLike)
        }


        // 날씨에 따른 추천 부분 옷
        binding.recyclerViewRecommend.adapter = adapter_weather
        binding.recyclerViewRecommend.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 최신 커뮤니티 부분 옷
        binding.recyclerViewCommunity.adapter = adapter_community
        binding.recyclerViewCommunity.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.HomeweatherItemsListData.observe(viewLifecycleOwner) {
            adapter_weather.notifyDataSetChanged()
        }

        viewModel.HomecommunityItemsListData.observe(viewLifecycleOwner) {
            adapter_community.notifyDataSetChanged()
        }

        loadHomeCommunityList()

        //임시
//        viewModel.addHomeWeatherItem(TempHomeItem(R.drawable.test_top))
//        viewModel.addHomeWeatherItem(TempHomeItem(R.drawable.test_bottom))

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()

    }

    private fun loadHomeWeatherList(feelsLike: Float) {
        viewModel.deleteHomeWeatherItem()
        sortClothesForWeather(feelsLike) // api에서 현재 체감 온도 받아와서 전달
    }

    private fun sortClothesForWeather(feelsLike: Float) {
        val seasonString = when {
            feelsLike > 22f -> "summer"
            feelsLike > 12f -> "spring&fall"
            else -> "winter"
        }
        setColRef.whereEqualTo("userID", currentUID).get()
            .addOnSuccessListener {
                for (doc in it) {
                    if (seasonString == doc["season"].toString()) {
                        viewModel.addHomeWeatherItem(HomeItem(doc["imageRef"].toString()))
                    }
                }
            }
    }

    private fun loadHomeCommunityList() {
        viewModel.deleteHomeCommunityItem()
        setColRef.whereNotEqualTo("userID", currentUID).get()
            .addOnSuccessListener {
                for (doc in it) {
                    if (doc["public"] == true) {
                        viewModel.addHomeCommunityItem(HomeItem(doc["imageRef"].toString()))
                    }
                }
            }
    }
}
