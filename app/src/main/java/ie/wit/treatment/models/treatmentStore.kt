package ie.wit.treatment.models

interface treatmentStore {
    fun findAll(): List<treatmentModel>
    fun create(treatment: treatmentModel)
    fun delete(treatment: treatmentModel)
    fun update(treatment: treatmentModel)
    fun findByName(name: String): ArrayList<treatmentModel>
}