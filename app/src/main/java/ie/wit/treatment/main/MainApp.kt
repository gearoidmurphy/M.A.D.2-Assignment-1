package ie.wit.treatment.main

import android.app.Application
import ie.wit.treatment.models.treatmentJSONStore
import ie.wit.treatment.models.treatmentModel
import ie.wit.treatment.models.treatmentStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //val treatments = ArrayList<treatmentModel>()
    //val treatments = treatmentMemStore()
    lateinit var treatments: treatmentStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        treatments = treatmentJSONStore(applicationContext)
        i("Treatment started")
//        treatments.add(treatmentModel("One", "About one..."))
//        treatments.add(treatmentModel("Two", "About two..."))
//        treatments.add(treatmentModel("Three", "About three..."))
    }
}