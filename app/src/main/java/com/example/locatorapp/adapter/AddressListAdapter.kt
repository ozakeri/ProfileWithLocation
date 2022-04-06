package com.example.locatorapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.locatorapp.R
import com.example.locatorapp.model.ResponseBean
import kotlinx.android.synthetic.main.address_list_items.view.*

class AddressListAdapter() : RecyclerView.Adapter<AddressListAdapter.CustomView>() {

    inner class CustomView(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<ResponseBean>() {
        override fun areItemsTheSame(oldItem: ResponseBean, newItem: ResponseBean): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseBean, newItem: ResponseBean): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        return CustomView(
            LayoutInflater.from(parent.context).inflate(R.layout.address_list_items, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomView, position: Int) {
        val response = differ.currentList[position]
        holder.itemView.apply {
            txt_address.text = response.address
            txt_nameFamily.text = response.first_name  + " " + response.last_name
            txt_mobile.text = response.coordinate_mobile
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}