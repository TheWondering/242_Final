package com.example.project.ui.subcategory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project.R
import com.example.project.databinding.FragmentSubcategoryBinding
import com.example.project.ui.product.Product
import com.google.firebase.firestore.FirebaseFirestore

class SubcategoryFragment : Fragment() {

    private var _binding: FragmentSubcategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var subcategoryAdapter: SubcategoryProductAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubcategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = arguments?.getString("CATEGORY") ?: ""
        val subcategory = arguments?.getString("SUBCATEGORY") ?: ""

        binding.subcategoryTitle.text = "$category $subcategory"

        setupRecyclerView()
        fetchSubcategoryProducts(category, subcategory)

        // Handle back button click
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        subcategoryAdapter = SubcategoryProductAdapter { productId ->
            // Navigate using Navigation Component
            val action = SubcategoryFragmentDirections
                .actionSubcategoryFragmentToProductDetailFragment(productId)
            findNavController().navigate(action)
        }

        binding.subcategoryRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = subcategoryAdapter
        }
    }

    private fun fetchSubcategoryProducts(category: String, subcategory: String) {
        db.collection("products")
            .whereEqualTo("category", category)
            .whereEqualTo("subcategory", subcategory)
            .get()
            .addOnSuccessListener { documents ->
                val productList = documents.mapNotNull { doc ->
                    doc.toObject(Product::class.java).also { it.id = doc.id }
                }
                subcategoryAdapter.submitList(productList)
            }
            .addOnFailureListener { e ->
                Log.e("SubcategoryFragment", "Error fetching products", e)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
