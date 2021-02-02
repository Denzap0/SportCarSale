package com.example.sportcarsale.View

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportcarsale.Model.data.Car
import com.example.sportcarsale.R
import com.google.firebase.storage.FirebaseStorage
private val storageRef = FirebaseStorage.getInstance().reference

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

    fun setCarsList(list: List<Car>) {
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
            car.photoLink?.let {
                storageRef.child(it).getBytes(1024*1024)
                    .addOnSuccessListener {
                        photoImageView.setImageBitmap(BitmapFactory.decodeByteArray(it,0,it.size))
                    }
            }
            brandModelTextView.text = (car.brand + " " + car.model)
            locationTextView.text = car.location
            carPriceTextView.text = (car.price.toString() + "$")
            sportDescTextView.text = (car.sportType.toString() + ", " + car.roadType)
            usualDescTextView.text =
                (car.productYear.toString() + ", " + car.gearType + ", " + car.engineVolume + "l, " + car.engineType)
//            Glide.with(itemView.context).load(car.photoLink).into(photoImageView)
            itemView.setOnClickListener { itemClickListener(car) }
        }
    }
}