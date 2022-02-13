package ie.wit.treatment.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import ie.wit.treatment.R
import ie.wit.treatment.activities.treatmentActivity
import ie.wit.treatment.adapters.treatmentAdapter
import ie.wit.treatment.databinding.FragmentListBinding
import ie.wit.treatment.main.MainApp
import ie.wit.treatment.models.MyPreferences
import ie.wit.treatment.models.treatmentModel


lateinit var app: MainApp
private var _fragBinding: FragmentListBinding? = null
private val fragBinding get() = _fragBinding!!
private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
class List : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.app_name)
//        val navView : NavigationView = inflater.inflate(R.menu.nav_menu, menu) as NavigationView

        //checkTheme()
//       navView.setNavigationItemSelectedListener{
//            when (it.itemId) {
////                R.id.allmap -> {
////                    val location = Location(52.245696, -7.139102, 15f)
////                    val launcherIntent = Intent( this ,allMapsActivity::class.java).putExtra("location", location)
////                    mapIntentLauncher.launch(launcherIntent)
////                    true
////                }
//                R.id.item_addbtn -> {
//                    val launcherIntent = Intent(activity, AddTreatment::class.java)
//                    refreshIntentLauncher.launch(launcherIntent)
//                    true
//                }
//                R.id.item_change_theme -> {
//                    //chooseThemeDialog()
//                    true
//                }
//                else -> {false}
//            }

//        }

//        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
//        fragBinding.recyclerView.adapter = treatmentAdapter(app.treatments.findAll())



        //registerRefreshCallback()
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_addbtn -> {
                val launcherIntent = Intent(activity, AddTreatment::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            List().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

//    override fun ontreatmentClick(treatment: treatmentModel) {
//        val launcherIntent = Intent(activity, AddTreatment::class.java)
//        launcherIntent.putExtra("treatment_edit", treatment)
//        refreshIntentLauncher.launch(launcherIntent)
//    }


//    private fun registerRefreshCallback() {
//        refreshIntentLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
//            { loadtreatments() }
//    }

//    private fun loadtreatments() {
//        showtreatments(app.treatments.findAll())
//    }

//    fun showtreatments (treatments: kotlin.collections.List<treatmentModel>) {
//        fragBinding.recyclerView.adapter = treatmentAdapter(treatments, this)
//        fragBinding.recyclerView.adapter?.notifyDataSetChanged()
//    }


//    private fun chooseThemeDialog() {
//
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle(getString(R.string.choose_theme_text))
//        val styles = arrayOf("Light", "Dark", "System default")
//        val checkedItem = MyPreferences(this).darkMode
//
//        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->
//
//            when (which) {
//                0 -> {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    MyPreferences(this).darkMode = 0
//                    delegate.applyDayNight()
//                    dialog.dismiss()
//                }
//                1 -> {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    MyPreferences(this).darkMode = 1
//                    delegate.applyDayNight()
//                    dialog.dismiss()
//                }
//                2 -> {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//                    MyPreferences(this).darkMode = 2
//                    delegate.applyDayNight()
//                    dialog.dismiss()
//                }
//
//            }
//        }
//
//        val dialog = builder.create()
//        dialog.show()
//    }
//
//    private fun checkTheme() {
//        when (MyPreferences(this).darkMode) {
//            0 -> {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                delegate.applyDayNight()
//            }
//            1 -> {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                delegate.applyDayNight()
//            }
//            2 -> {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//                delegate.applyDayNight()
//            }
//        }
//    }


}