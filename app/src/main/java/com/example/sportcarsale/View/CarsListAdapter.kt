package com.example.sportcarsale.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sportcarsale.Model.Car
import com.example.sportcarsale.R

class CarsListAdapter(private val itemClickListener: (Car) -> Unit) :
    RecyclerView.Adapter<CarsListAdapter.ItemViewHolder>() {
    private var carsList = mutableListOf<Car>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return ItemViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(carsList[position])
    }

    override fun getItemCount(): Int = carsList.size

    fun setCarsList(list: MutableList<Car>) {
        with(carsList) {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val itemview: View, private val itemClickListener: (Car) -> Unit) :
        RecyclerView.ViewHolder(itemview) {
        private val brandModelTextView = itemView.findViewById<TextView>(R.id.brandModelText)
        private val locationTextView = itemView.findViewById<TextView>(R.id.locationText)
        private val carPriceTextView = itemView.findViewById<TextView>(R.id.carPriceText)
        private val sportDescTextView = itemView.findViewById<TextView>(R.id.sportDescriptionText)
        private val usualDescTextView = itemView.findViewById<TextView>(R.id.usualDescriptionText)
        private val photoImageView = itemView.findViewById<ImageView>(R.id.carMainPhoto)
        fun bind(car: Car) {
            brandModelTextView.text = (car.brand + " " + car.model)
            locationTextView.text = car.location
            carPriceTextView.text = (car.price.toString() + "$")
            sportDescTextView.text = (car.sportType.toString() + ", " + car.roadType)
            usualDescTextView.text =
                (car.productYear.toString() + ", " + car.gearType + ", " + car.engineVolume + "l, " + car.engineType)
            Glide.with(itemView.context).load(car.photoLink).into(photoImageView)
            itemView.setOnClickListener { itemClickListener(car) }
        }
    }
}