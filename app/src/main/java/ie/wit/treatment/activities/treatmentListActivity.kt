package ie.wit.treatment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.treatment.R
import ie.wit.treatment.adapters.treatmentAdapter
import ie.wit.treatment.adapters.treatmentListener
import ie.wit.treatment.databinding.ActivityTreatmentListBinding
import ie.wit.treatment.databinding.CardTreatmentBinding
import ie.wit.treatment.main.MainApp
import ie.wit.treatment.models.treatmentModel


class treatmentListActivity : AppCompatActivity(), treatmentListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityTreatmentListBinding
    private lateinit var cardbinding : CardTreatmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreatmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = treatmentAdapter(app.treatments.findAll(),this)
//        cardbinding.btnDelete.setOnClickListener(){
//            onbttnDeleteClick(cardbinding.treatmentId.text.toString().toInt())
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, treatmentActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
        
    }

    override fun ontreatmentClick(treatment: treatmentModel) {
        val launcherIntent = Intent(this, treatmentActivity::class.java)
        launcherIntent.putExtra("treatment_edit", treatment)
        startActivityForResult(launcherIntent,0)
    }



}
