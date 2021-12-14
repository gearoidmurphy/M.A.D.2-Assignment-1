package ie.wit.treatment.models

import timber.log.Timber.i

var lastId = 0

internal fun getId(): Long {
    return lastId++.toLong()
}

class treatmentMemStore : treatmentStore {

    val treatments = ArrayList<treatmentModel>()

    override fun findAll(): List<treatmentModel> {
        return treatments
    }

    override fun create(treatment: treatmentModel) {
        treatment.id = getId().toString()
        treatments.add(treatment)
        logAll()
    }

    override fun delete(treatment: treatmentModel){
        treatments.remove(treatment)
        i("delete")
    }

    fun logAll() {
        treatments.forEach{ i("${it}") }
    }

}