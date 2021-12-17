package ie.wit.treatment.models

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.util.Log.i
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ie.wit.treatment.helpers.exists
import timber.log.Timber

class treatmentFirebaseStore(private val context: Context) : treatmentStore {

    private lateinit var database : DatabaseReference
    var treatments = mutableListOf<treatmentModel>()
    val firestore = Firebase.firestore


//    init {
//        var treatment = treatmentModel(generateRandomId(),"",123,100,10,"",0.0,0.0,0f)
//        readData(treatment)
//    }


    
    override fun findAll(): List<treatmentModel> {
        logAll()
        return treatments
    }

    override fun create(treatment: treatmentModel) {
        treatment.id = generateRandomId()
        treatments.add(treatment)
        uploadData(treatment)
    }

    override fun delete(treatment: treatmentModel) {
        treatments.remove(treatment)
        deleteData(treatment)
    }

    override fun update(treatment: treatmentModel) {
        var foundtreatment: treatmentModel? = treatments.find { p -> p.id == treatment.id }
        if (foundtreatment != null) {
            foundtreatment.tagNumber = treatment.tagNumber
            foundtreatment.treatmentName = treatment.treatmentName
            foundtreatment.withdarwal = treatment.withdarwal
            foundtreatment.amount = treatment.amount
            foundtreatment.image = treatment.image
            foundtreatment.lat = treatment.lat
            foundtreatment.lng = treatment.lng
            foundtreatment.zoom = treatment.zoom
            logAll()
        }
    }

    override fun findByName(name: String): ArrayList<treatmentModel> {
        val foundTreatments = ArrayList<treatmentModel>()
        treatments.forEach() { p -> p.treatmentName; if(p.treatmentName == name){
            foundTreatments.add(p)
        } }
        return foundTreatments
    }

    private fun logAll() {
        treatments.forEach { Timber.i("$it") }
    }

    private fun uploadData(treatment: treatmentModel) {
        firestore.collection("Treatments")//.document("$name")
            .add(treatment)
            .addOnSuccessListener {
                i("string","DocumentSnapshot added")
            }
            .addOnFailureListener { e ->
                i(e.toString(), "Error adding document")
            }
    }

    private fun readData(treatment: treatmentModel,) {

        firestore.collection("Treatments")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot.forEach { document ->
                        Log.d(TAG, "${document.id} => ${document.data}")
                        ///var treatment = treatmentModel(generateRandomId(),document.get("treatmentName").toString(),document.get("amount").toString().toInt(), document.get("withdrawal").toString().toInt(),)
                        treatment.tagNumber = document.get("tagNumber").toString().toInt()
                        treatment.treatmentName = document.get("treatmentName").toString()
                        treatment.amount = document.get("amount").toString().toInt()
                        //treatment.withdarwal = document.get("withdrawal").toString().toInt()
                        treatment.image = document.get("image").toString()
                        treatment.lat = document.get("lat").toString().toDouble()
                        treatment.lng = document.get("lng").toString().toDouble()
                        treatment.zoom = document.get("zoom").toString().toFloat()
                        treatments.add(treatment)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents $exception")
                }


    }

    private fun deleteData(treatment: treatmentModel){
        val rootRef = FirebaseFirestore.getInstance()
        val itemsRef = rootRef.collection("cryptos")
        val query: Query = itemsRef.whereEqualTo("tagNumber", treatment.tagNumber)
        query.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    itemsRef.document(document.id).delete()
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.exception)
            }
        })
    }



}