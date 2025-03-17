package com.example.project.ui.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.project.databinding.FragmentProductDetailBinding
import com.example.project.ui.product.Product
import com.example.project.R
import com.example.project.ui.product.ProductImageAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    companion object {
        private const val ARG_PRODUCT_ID = "PRODUCT_ID"
        fun newInstance(productId: String): ProductDetailFragment {
            val fragment = ProductDetailFragment()
            val args = Bundle().apply {
                putString(ARG_PRODUCT_ID, productId)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = arguments?.getString("PRODUCT_ID") ?: ""

        // Back button
        binding.btnBackProduct.setOnClickListener {
            findNavController().navigateUp()
        }

        // Fetch product details from Firestore
        fetchProductDetails(productId)
    }

    private fun fetchProductDetails(productId: String) {
        db.collection("products").document(productId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val product = document.toObject(Product::class.java)
                    product?.let {
                        it.id = document.id  // Set the Firestore doc ID as the product's ID
                        displayProductDetails(it)
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("ProductDetailFragment", "Error fetching product details", e)
            }
    }

    private fun displayProductDetails(product: Product) {
        // Populate the UI with product details
        binding.tvProductCategory.text = product.subcategory
        binding.tvProductPrice.text = "$${product.price}"
        binding.tvProductDescription.text = product.description

        // Image slider
        setupImageSlider(product.images)

        // Size chips
        setupSizeOptions(product.sizes)

        // "Add to Cart" button
        binding.btnAddToCart.setOnClickListener {
            // TODO: Implement add to cart
            Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupImageSlider(images: List<String>) {
        val imageAdapter = ProductImageAdapter(images)
        binding.productImageViewPager.adapter = imageAdapter

        // Set up the dots indicator
        TabLayoutMediator(binding.imageIndicator, binding.productImageViewPager) { _, _ ->
            // No text needed for dots
        }.attach()
    }

    private fun setupSizeOptions(sizes: List<String>) {
        binding.sizeChipGroup.removeAllViews()

        sizes.forEach { size ->
            val chip = Chip(requireContext()).apply {
                text = size
                isCheckable = true
                isClickable = true
            }
            binding.sizeChipGroup.addView(chip)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
