package ie.wit.treatment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.treatment.R
import ie.wit.treatment.R.id.nav_host_fragment
import ie.wit.treatment.databinding.ActivityMainBinding

private lateinit var drawerLayout: DrawerLayout
private lateinit var mainBinding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}