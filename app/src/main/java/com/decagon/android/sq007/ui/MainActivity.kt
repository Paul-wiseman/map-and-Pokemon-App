package com.decagon.android.sq007.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.decagon.android.sq007.R
import com.decagon.android.sq007.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        val mapFragment = MapFragment()
//        val pokemonFragment = PokemonFragment()
//        getCurrentFragment(mapFragment)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(setOf(R.id.mapFragment, R.id.pokemonFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)

//        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.b_nav_map -> {
//                    findNavController(R.id.fragment).navigate(R.id.mapFragment)
//                }
//                R.id.b_nav_pokemon -> {
//                    findNavController(R.id.fragment).navigate(R.id.pokemonFragment)
//                }
//            }
//            true
//        }
    }
//    val mapsActivity = startActivity(Intent(this, MapsActivity::class.java))
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        supportFragmentManager.popBackStack()
//    }
//
//    private fun getCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fl_fragment, fragment)
//                .addToBackStack(null)
//                .commit()
//        }
}
