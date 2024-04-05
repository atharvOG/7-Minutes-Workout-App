package com.example.a7minutesworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel1>): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemExerciseStatusBinding ): RecyclerView.ViewHolder(binding.root)  {
        val tvItem = binding.tvName

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
return ViewHolder(ItemExerciseStatusBinding.inflate(
    LayoutInflater.from(parent.context), parent, false))

    }

    override fun getItemCount(): Int {

        return items.size
   }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel1 = items[position]
        holder.tvItem.text = model.getId().toString()

        when {
            model.getIsSelected() -> {
                // Given Below we are setting up the background of the recyclerView i.e.
                // the list of the exercise the background of the current exrcise will change out of the current exercise list
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                R.drawable.item_circular_thin_color_accent_border)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
                // above inside the getDrawable() as we cant direvtky pass this as a contect because this doesn't exist coz we're not in the context of a class so we get it from the itemView, the itemView of the recyclerView View holder

            }
            model.getisCompleted() -> {
                // Given Below we are setting up the background of the recyclerView i.e.
                // the list of the exercise the background of the current exrcise will change out of the current exercise list
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_color_accent_border)
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
                // above inside the getDrawable() as we cant direvtky pass this as a contect because this doesn't exist coz we're not in the context of a class so we get it from the itemView, the itemView of the recyclerView View holder


            }
            else ->{
                // Given Below we are setting up the background of the recyclerView i.e.
                // the list of the exercise the background of the current exrcise will change out of the current exercise list
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                    R.drawable.item_circular_gray_background)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
                // above inside the getDrawable() as we cant direvtky pass this as a contect because this doesn't exist coz we're not in the context of a class so we get it from the itemView, the itemView of the recyclerView View holder


            }
        }

    }
}