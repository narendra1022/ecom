package com.example.ecom.Cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecom.data.cartadata
import com.example.ecom.databinding.CartItemBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.viewHolder>() {

    var onItemClick: ((cartadata) -> Unit)? = null

    inner class viewHolder(val binding:CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: cartadata) {

            binding.apply {
                Glide.with(itemView).load(product.images?.get(0)).into(ProductImg)
                ProductName.text = product.name
                ProductNewPrice.text = product.price
//                ProductOldPrice.text = product.offerPercentage.toString()
//                ProductOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<cartadata>() {
        override fun areItemsTheSame(oldItem: cartadata, newItem: cartadata): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: cartadata, newItem: cartadata): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartAdapter.viewHolder {
        return viewHolder(
            CartItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(product)
        }
    }


}
