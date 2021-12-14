package ie.wit.treatment.models

interface treatmentStore {
    fun findAll(): List<treatmentModel>
    fun create(treatment: treatmentModel)
    fun delete(treatment: treatmentModel)
}