package ie.wit.treatment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class treatmentModel(var id: String = "",
                          var treatmentName: String = "",
                          var tagNumber: Int = 0,
                          var amount: Int = 0,
                          var withdarwal: Int = 0,
                          var date: String = "") : Parcelable
