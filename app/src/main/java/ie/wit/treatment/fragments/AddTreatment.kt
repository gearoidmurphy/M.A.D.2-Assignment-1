package ie.wit.treatment.fragments


import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.treatment.R
import ie.wit.treatment.activities.MapActivity
import ie.wit.treatment.databinding.FragmentAddTreatmentBinding
import android.content.Intent
import ie.wit.treatment.helpers.showImagePicker
import ie.wit.treatment.main.MainApp
import ie.wit.treatment.models.Location
import ie.wit.treatment.models.treatmentModel
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private var _fragBinding: FragmentAddTreatmentBinding? = null
private val fragBinding get() = _fragBinding!!
private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
private lateinit var fusedLocationClient: FusedLocationProviderClient
var treatment = treatmentModel()
var setLocation = Location()

class AddTreatment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentAddTreatmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.app_name)
        
        fragBinding.treatmentAmount.minValue = 1
        fragBinding.treatmentAmount.maxValue = 1000

        var edit = false
        fragBinding.btnAdd.setOnClickListener() {
            treatment.tagNumber = fragBinding.tagNumber.text.toString().toInt()
            treatment.treatmentName = fragBinding.treatmentName.text.toString()
            treatment.amount = fragBinding.treatmentAmount.value.toString().toInt()
            treatment.withdarwal = fragBinding.withdrawal.progress.toString().toInt()
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formatted = current.format(formatter)
            treatment.date = formatted
            if (treatment.treatmentName.isEmpty()) {
                Snackbar.make(it,R.string.enter_treatment_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.treatments.update(treatment.copy())
                } else {
                    app.treatments.create(treatment.copy())
//                    uploadData()
                }
            }
            Timber.i("add Button Pressed: $treatment")
        }
        fragBinding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        fragBinding.btnDelete.setOnClickListener(){
            app.treatments.delete(treatment.copy())
            Timber.i("Delete Button Pressed: $treatment")
        }

        fragBinding.treatmentLocation.setOnClickListener {
            if (treatment.zoom != 0f) {
                setLocation.lat =  treatment.lat
                setLocation.lng = treatment.lng
                setLocation.zoom = treatment.zoom
            }

        }
        registerImagePickerCallback()
        registerMapCallback()
        return root;
    }


    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            treatment.image = result.data!!.data.toString()!!
                            Picasso.get()
                                .load(Uri.parse(treatment.image))
                                .into(fragBinding.treatmentImage)

                            fragBinding.chooseImage.setText(R.string.change_treatment_image)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            treatment.lat = location.lat
                            treatment.lng = location.lng
                            treatment.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddTreatment().apply {
                arguments = Bundle().apply {}
            }
    }
}