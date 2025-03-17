package com.example.project.ui.cart

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import java.text.NumberFormat
import java.util.Locale

class cart : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var tvPrice: TextView
    private lateinit var backArrow: ImageView
    private lateinit var btnCheckout: AppCompatButton

    // Sample data for demonstration
    private val cartItems = mutableListOf<CartItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_cart)

        // Initialize views
        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        tvPrice = findViewById(R.id.tvPrice)
        backArrow = findViewById(R.id.backArrow)
        btnCheckout = findViewById(R.id.btnCheckout)

        // Setup RecyclerView
        setupRecyclerView()

        // Load sample data
        loadSampleData()

        // Calculate and display total price
        updateTotalPrice()

        // Set click listeners
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(cartItems,
            onQuantityChanged = { updateTotalPrice() },
            onItemRemoved = { position ->
                cartItems.removeAt(position)
                cartAdapter.notifyItemRemoved(position)
                updateTotalPrice()
            }
        )

        cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@cart)
            adapter = cartAdapter
        }
    }

    private fun loadSampleData() {
        // Sample data
        cartItems.add(CartItem(
            id = 1,
            name = "Fabric Strap Watch",
            color = "Green",
            size = "32 MM",
            price = 100.0,
            quantity = 1,
            imageResId = R.drawable.watch
        ))

        // Add more sample items if needed
        cartItems.add(CartItem(
            id = 2,
            name = "Leather Strap Watch",
            color = "Brown",
            size = "38 MM",
            price = 100.0,
            quantity = 1,
            imageResId = R.drawable.watch
        ))

        cartAdapter.notifyDataSetChanged()
    }

    private fun updateTotalPrice() {
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        val formattedPrice = NumberFormat.getCurrencyInstance(Locale.US).format(totalPrice)
        tvPrice.text = formattedPrice
    }

    private fun setupClickListeners() {
        backArrow.setOnClickListener {
            finish()
        }

        btnCheckout.setOnClickListener {
            // Handle checkout process - you can navigate to checkout screen
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }
    }
}