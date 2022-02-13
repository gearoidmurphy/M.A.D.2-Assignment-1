package ie.wit.treatment.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import ie.wit.treatment.R
import ie.wit.treatment.adapters.treatmentAdapter
import ie.wit.treatment.adapters.treatmentListener
import ie.wit.treatment.databinding.ActivityTreatmentListBinding
import ie.wit.treatment.databinding.CardTreatmentBinding
import ie.wit.treatment.main.MainApp
import ie.wit.treatment.models.Location
import ie.wit.treatment.models.MyPreferences
import ie.wit.treatment.models.treatmentModel


class treatmentListActivity : AppCompatActivity(), treatmentListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityTreatmentListBinding
    private lateinit var cardbinding : CardTreatmentBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    lateinit var toggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreatmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val drawerLayout : DrawerLayout = findViewById(R.id.drawLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        checkTheme()
        navView.setNavigationItemSelectedListener{
            when (it.itemId) {
//                R.id.allmap -> {
//                    val location = Location(52.245696, -7.139102, 15f)
//                    val launcherIntent = Intent( this ,allMapsActivity::class.java).putExtra("location", location)
//                    mapIntentLauncher.launch(launcherIntent)
//                    true
//                }
                R.id.item_addbtn -> {
                    val launcherIntent = Intent(this, treatmentActivity::class.java)
                    refreshIntentLauncher.launch(launcherIntent)
                    true
                }
                R.id.item_change_theme -> {
                    chooseThemeDialog()
                    true
                }
                else -> {false}
            }

        }


        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadtreatments()
//        cardbinding.btnDelete.setOnClickListener(){
//            onbttnDeleteClick(cardbinding.treatmentId.text.toString().toInt())
//        }
        binding.searchBtn.setOnClickListener {
            showtreatments(app.treatments.findByName(binding.searchText.text.toString()))
        }


        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, treatmentActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }

        }

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun ontreatmentClick(treatment: treatmentModel) {
        val launcherIntent = Intent(this, treatmentActivity::class.java)
        launcherIntent.putExtra("treatment_edit", treatment)
        refreshIntentLauncher.launch(launcherIntent)
    }


    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadtreatments() }
    }

    private fun loadtreatments() {
        showtreatments(app.treatments.findAll())
    }

    fun showtreatments (treatments: List<treatmentModel>) {
        binding.recyclerView.adapter = treatmentAdapter(treatments, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }


    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_theme_text))
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = MyPreferences(this).darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this).darkMode = 0
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this).darkMode = 1
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(this).darkMode = 2
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun checkTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }



}
