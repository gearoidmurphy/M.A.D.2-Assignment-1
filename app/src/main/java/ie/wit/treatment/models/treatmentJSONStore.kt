package ie.wit.treatment.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.treatment.helpers.exists
import ie.wit.treatment.helpers.read
import ie.wit.treatment.helpers.write
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE = "treatments.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<treatmentModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class treatmentJSONStore(private val context: Context) : treatmentStore {

    var treatments = mutableListOf<treatmentModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<treatmentModel> {
        logAll()
        return treatments
    }

    override fun create(treatment: treatmentModel) {
        treatment.id = generateRandomId()
        treatments.add(treatment)
        serialize()
    }

    override fun delete(treatment: treatmentModel) {
        treatments.remove(treatment)
        serialize()
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

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(treatments, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        treatments = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        treatments.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}