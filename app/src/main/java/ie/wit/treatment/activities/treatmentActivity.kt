package ie.wit.treatment.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.treatment.R
import ie.wit.treatment.databinding.ActivityTreatmentBinding
import ie.wit.treatment.helpers.showImagePicker
import ie.wit.treatment.main.MainApp
import ie.wit.treatment.models.Location
import ie.wit.treatment.models.treatmentModel
import timber.log.Timber.i

class treatmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTreatmentBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var treatment = treatmentModel()
    //var location = Location(52.245696, -7.139102, 15f)
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false

        binding = ActivityTreatmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("treatment_edit")) {
            edit = true
            treatment = intent.extras?.getParcelable("treatment_edit")!!
            binding.tagNumber.setText(treatment.tagNumber.toString())
            binding.treatmentName.setText(treatment.treatmentName)
            binding.treatmentAmount.setText(treatment.amount.toString())
            binding.withdrawal.setText(treatment.withdarwal.toString())
            binding.btnAdd.setText(R.string.save_treatment)
            Picasso.get()
                .load(treatment.image)
                .into(binding.treatmentImage)
            if (treatment.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_treatment_image)
            }

        }
        
        binding.btnAdd.setOnClickListener() {
            treatment.tagNumber = binding.tagNumber.text.toString().toInt()
            treatment.treatmentName = binding.treatmentName.text.toString()
            treatment.amount = binding.treatmentAmount.text.toString().toInt()
            treatment.withdarwal = binding.withdrawal.text.toString().toInt()
            if (treatment.treatmentName.isEmpty()) {
                Snackbar.make(it,R.string.enter_treatment_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.treatments.update(treatment.copy())
                } else {
                    app.treatments.create(treatment.copy())
                }
            }
            i("add Button Pressed: $treatment")
            setResult(RESULT_OK)
            finish()
        }
        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.btnDelete.setOnClickListener(){
            app.treatments.delete(treatment.copy())
            i("Delete Button Pressed: $treatment")
            setResult(RESULT_OK)
            finish()
        }

        binding.treatmentLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (treatment.zoom != 0f) {
                location.lat =  treatment.lat
                location.lng = treatment.lng
                location.zoom = treatment.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
        registerImagePickerCallback()
        registerMapCallback()
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            treatment.image = result.data!!.data!!
                            Picasso.get()
                                .load(treatment.image)
                                .into(binding.treatmentImage)
                            binding.chooseImage.setText(R.string.change_treatment_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
         mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
                { result ->
                    when (result.resultCode) {
                        RESULT_OK -> {
                            if (result.data != null) {
                                i("Got Location ${result.data.toString()}")
                                val location = result.data!!.extras?.getParcelable<Location>("location")!!
                                i("Location == $location")
                                treatment.lat = location.lat
                                treatment.lng = location.lng
                                treatment.zoom = location.zoom
                            } // end of if
                        }
                        RESULT_CANCELED -> { } else -> { }
                    }
            }
    }

}