package com.example.project.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val onQuantityChanged: () -> Unit,
    private val onItemRemoved: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_cart_items, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = cartItems.size

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productImage: ImageView = itemView.findViewById(R.id.imageView2)
        private val productName: TextView = itemView.findViewById(R.id.textView4)
        private val colorLabel: TextView = itemView.findViewById(R.id.textView5)
        private val sizeLabel: TextView = itemView.findViewById(R.id.textView6)
        private val quantityText: TextView = itemView.findViewById(R.id.textView7)
        private val priceText: TextView = itemView.findViewById(R.id.priceEditText)
        private val increaseQuantity: ImageView = itemView.findViewById(R.id.imageView4)
        private val decreaseQuantity: ImageView = itemView.findViewById(R.id.imageView5)
        private val removeItem: ImageView = itemView.findViewById(R.id.imageView6)

        fun bind(item: CartItem, position: Int) {
            productImage.setImageResource(item.imageResId)
            productName.text = item.name
            colorLabel.text = "Color/"
            sizeLabel.text = "Size/${item.size}"
            quantityText.text = item.quantity.toString()

            // Format price
            val formattedPrice = NumberFormat.getCurrencyInstance(Locale.US).format(item.price * item.quantity)
            priceText.text = formattedPrice

            // Set up click listeners
            increaseQuantity.setOnClickListener {
                item.quantity++
                quantityText.text = item.quantity.toString()
                priceText.text = NumberFormat.getCurrencyInstance(Locale.US).format(item.price * item.quantity)
                onQuantityChanged()
            }

            decreaseQuantity.setOnClickListener {
                if (item.quantity > 1) {
                    item.quantity--
                    quantityText.text = item.quantity.toString()
                    priceText.text = NumberFormat.getCurrencyInstance(Locale.US).format(item.price * item.quantity)
                    onQuantityChanged()
                }
            }

            removeItem.setOnClickListener {
                onItemRemoved(position)
            }
        }
    }
}