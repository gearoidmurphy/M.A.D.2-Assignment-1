package ie.wit.treatment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.treatment.databinding.CardTreatmentBinding
import ie.wit.treatment.models.treatmentModel

interface treatmentListener {
    fun ontreatmentClick(treatment: treatmentModel)
}
class treatmentAdapter constructor(private var treatments: List<treatmentModel>,
                                   private val listener: treatmentListener) :
    RecyclerView.Adapter<treatmentAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTreatmentBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val treatment = treatments[holder.adapterPosition]
        holder.bind(treatment,listener)
    }

    override fun getItemCount(): Int = treatments.size

    class MainHolder(private val binding : CardTreatmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(treatment: treatmentModel, listener: treatmentListener) {
            binding.treatmentName.text = treatment.treatmentName
            binding.tagNumber.text = treatment.tagNumber.toString()
            binding.treatmentAmount.text = treatment.amount.toString()
            binding.withdrawal.text = treatment.withdarwal.toString()
            binding.date.text = treatment.date
            Picasso.get().load(treatment.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.ontreatmentClick(treatment) }

        }
    }
}