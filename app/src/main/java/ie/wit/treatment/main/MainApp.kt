package ie.wit.treatment.main

import android.app.Application
import ie.wit.treatment.models.treatmentMemStore
import ie.wit.treatment.models.treatmentModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //val treatments = ArrayList<treatmentModel>()
    val treatments = treatmentMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Treatment started")
//        treatments.add(treatmentModel("One", "About one..."))
//        treatments.add(treatmentModel("Two", "About two..."))
//        treatments.add(treatmentModel("Three", "About three..."))
    }
}