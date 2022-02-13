package ie.wit.treatment.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class treatmentModel(var id: Long = 0,
                          var treatmentName: String = "",
                          var tagNumber: Int = 0,
                          var amount: Int = 0,
                          var withdarwal: Int = 0,
                          var date: String = "",
                          var image: String = "",
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable