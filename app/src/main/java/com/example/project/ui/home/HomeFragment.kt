package com.example.project.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.project.ui.categories.Category
import com.example.project.R
import com.example.project.databinding.FragmentHomeBinding
import com.example.project.ui.categories.CategoriesAdapter

class HomeFragment : Fragment() {

    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        categoriesRecyclerView = view.findViewById(R.id.rv_categories)

        // Create a list of categories
        val categories = listOf(
            Category("Men"),
            Category("Women"),
            Category("Kids"),
            Category("Electronics")
        )

        // Set the adapter
        categoriesAdapter = CategoriesAdapter(categories) { selectedCategory ->
            // Handle category click (optional)
            // For example, filter products by category
            Toast.makeText(requireContext(), "Selected: ${selectedCategory.name}", Toast.LENGTH_SHORT).show()
        }

        categoriesRecyclerView.adapter = categoriesAdapter
    }
}
