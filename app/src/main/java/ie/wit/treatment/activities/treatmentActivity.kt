package ie.wit.treatment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.wit.treatment.R
import ie.wit.treatment.databinding.ActivityTreatmentBinding
import ie.wit.treatment.main.MainApp
import ie.wit.treatment.models.treatmentModel
import timber.log.Timber.i

class treatmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTreatmentBinding
    var treatment = treatmentModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTreatmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("treatment_edit")) {
            treatment = intent.extras?.getParcelable("treatment_edit")!!
            binding.treatmentId.setText(treatment.id)
            binding.tagNumber.setText(treatment.tagNumber.toString())
            binding.treatmentName.setText(treatment.treatmentName)
            binding.treatmentAmount.setText(treatment.amount.toString())
            binding.withdrawal.setText(treatment.withdarwal.toString())

        }
        
        binding.btnAdd.setOnClickListener() {
            treatment.tagNumber = binding.tagNumber.text.toString().toInt()
            treatment.treatmentName = binding.treatmentName.text.toString()
            treatment.amount = binding.treatmentAmount.text.toString().toInt()
            treatment.withdarwal = binding.withdrawal.text.toString().toInt()
            if (treatment.treatmentName.isNotEmpty()) {
                app.treatments.create(treatment.copy())
                setResult(RESULT_OK)
                finish()

            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        binding.btnDelete.setOnClickListener(){
            app.treatments.delete(treatment.copy())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_treatment, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}