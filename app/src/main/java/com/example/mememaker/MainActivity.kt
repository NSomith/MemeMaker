package com.example.mememaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navView: NavigationView
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment?.findNavController()
        val drawerLayout = findViewById<DrawerLayout>(R.id.layout_drawer)
        navView = findViewById(R.id.nav_view)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.memeTemplateFragment, R.id.photoSaveInternalStroage),
            drawerLayout
        )

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.memeTemplateFragment -> {
                    navController?.navigate(R.id.memeTemplateFragment)
                    drawerLayout.close()
                }
                R.id.photoSaveInternalStroage -> {
                    navController?.navigate(R.id.photoSaveInternalStroage)
                    drawerLayout.close()
                }
            }
            true
        }
        navController?.let { setupActionBarWithNavController(it, appBarConfiguration) }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp() //without giving the or it is giving the back button
    }

  
}