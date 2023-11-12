package com.example.challenges

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.challenges.ui.ViewModelFactories
import com.example.challenges.ui.home.HomeViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<HomeViewModel> { ViewModelFactories.getInstance(this) }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        viewModel.getSession().observe(this) { token ->
            if (token == null || token == "") {
                navController.popBackStack()
                navController.navigate(R.id.fragment_login)
            } else {
                navController.navigate(R.id.fragment_register)
            }

        }
        //        Crash
        // Creates a button that mimics a crash when pressed
        val btnCrash = Button(this)
        btnCrash.text = "Huraaa ngecrash"
        btnCrash.setOnClickListener {
            throw RuntimeException("Huraaa ngecrash") // Force a crash
        }

        addContentView(btnCrash, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))
    }
}


//
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_keranjang, R.id.navigation_akun
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//    }
//}