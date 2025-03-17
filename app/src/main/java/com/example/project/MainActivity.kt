package com.example.project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.ActivityMainBinding
import com.example.project.ui.product.Product
import com.example.project.ui.product.ProductAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Optionally add products to Firestore.
        addProductsToFirestore()

        // Setup NavHostFragment and Navigation Controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup Bottom Navigation and AppBarConfiguration
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_cart,
                R.id.navigation_categories,
                R.id.navigation_account
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

//    private fun fetchProducts() {
//        db.collection("products").get()
//            .addOnSuccessListener { result ->
//                productList.clear()
//                for (document in result) {
//                    val product = document.toObject(Product::class.java)
//                    productList.add(product)
//                }
//                productAdapter.notifyDataSetChanged()
//            }
//            .addOnFailureListener { e ->
//                println("Error fetching products: ${e.message}")
//            }
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun addProductsToFirestore() {
        val products = mapOf(
            // Men's Clothing
            "p101" to mapOf(
                "name" to "Slim Fit Chino Trousers",
                "price" to 49.99,
                "sizes" to listOf("S", "M", "L", "XL"),
                "category" to "Men's Clothing",
                "subcategory" to "Pants",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),
            "p102" to mapOf(
                "name" to "Casual Denim Jacket",
                "price" to 59.99,
                "sizes" to listOf("M", "L", "XL"),
                "category" to "Men's Clothing",
                "subcategory" to "Jackets",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),
            "p103" to mapOf(
                "name" to "Classic Polo Shirt",
                "price" to 34.99,
                "sizes" to listOf("S", "M", "L", "XL", "XXL"),
                "category" to "Men's Clothing",
                "subcategory" to "Shirts",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),
            "p104" to mapOf(
                "name" to "Formal Suit Blazer",
                "price" to 99.99,
                "sizes" to listOf("M", "L", "XL"),
                "category" to "Men's Clothing",
                "subcategory" to "Suits",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),
            "p105" to mapOf(
                "name" to "Athletic Running Shoes",
                "price" to 79.99,
                "sizes" to listOf("40", "41", "42", "43", "44"),
                "category" to "Men's Clothing",
                "subcategory" to "Shoes",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),

            // Women's Clothing
            "p201" to mapOf(
                "name" to "Floral Summer Dress",
                "price" to 39.99,
                "sizes" to listOf("XS", "S", "M", "L"),
                "category" to "Women's Clothing",
                "subcategory" to "Dresses",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),
            "p202" to mapOf(
                "name" to "High-Waisted Skinny Jeans",
                "price" to 44.99,
                "sizes" to listOf("S", "M", "L"),
                "category" to "Women's Clothing",
                "subcategory" to "Jeans",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),
            "p203" to mapOf(
                "name" to "Leather Handbag",
                "price" to 89.99,
                "sizes" to listOf("One Size"),
                "category" to "Women's Clothing",
                "subcategory" to "Accessories",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),
            "p204" to mapOf(
                "name" to "Winter Wool Coat",
                "price" to 119.99,
                "sizes" to listOf("M", "L", "XL"),
                "category" to "Women's Clothing",
                "subcategory" to "Coats",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),
            "p205" to mapOf(
                "name" to "Running Sneakers",
                "price" to 69.99,
                "sizes" to listOf("36", "37", "38", "39", "40"),
                "category" to "Women's Clothing",
                "subcategory" to "Shoes",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),

            // Kids' Clothing
            "p301" to mapOf(
                "name" to "Boys' Cotton T-Shirt",
                "price" to 19.99,
                "sizes" to listOf("XS", "S", "M", "L"),
                "category" to "Kids' Clothing",
                "subcategory" to "T-Shirts",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),
            "p302" to mapOf(
                "name" to "Girls' Floral Skirt",
                "price" to 24.99,
                "sizes" to listOf("XS", "S", "M"),
                "category" to "Kids' Clothing",
                "subcategory" to "Skirts",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),
            "p303" to mapOf(
                "name" to "Kids' Waterproof Jacket",
                "price" to 49.99,
                "sizes" to listOf("S", "M", "L"),
                "category" to "Kids' Clothing",
                "subcategory" to "Jackets",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),
            "p304" to mapOf(
                "name" to "Unisex Sports Shoes",
                "price" to 39.99,
                "sizes" to listOf("30", "31", "32", "33"),
                "category" to "Kids' Clothing",
                "subcategory" to "Shoes",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            ),
            "p305" to mapOf(
                "name" to "Boys' Cargo Shorts",
                "price" to 29.99,
                "sizes" to listOf("S", "M", "L"),
                "category" to "Kids' Clothing",
                "subcategory" to "Shorts",
                "images" to listOf("https://i.ibb.co/example-image.jpg")
            )
        )

        // Upload each product to Firestore
        for ((id, product) in products) {
            db.collection("products").document(id).set(product)
                .addOnSuccessListener { println("Product $id added successfully") }
                .addOnFailureListener { e -> println("Error adding product $id: ${e.message}") }
        }
    }
}
