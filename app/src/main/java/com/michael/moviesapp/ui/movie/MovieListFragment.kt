package com.michael.moviesapp.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.michael.moviesapp.R
import com.michael.moviesapp.adapters.MovieAdapter
import com.michael.moviesapp.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.RecyclerView
import com.michael.moviesapp.BuildConfig
import com.michael.moviesapp.data.ErrorType
import com.michael.moviesapp.ui.viewmodel.MovieViewModel

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieAdapter
    private lateinit var progressDialog: AlertDialog

    val apiKey = BuildConfig.TMDB_API_KEY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter { movie ->
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movie)
            findNavController().navigate(action)
        }

        setupProgressDialog()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorType ->
            errorType?.let {
                val message = when (it) {
                    is ErrorType.NoInternet -> "No internet connection"
                    is ErrorType.ServerError -> "Server error, try again later"
                    is ErrorType.Unknown -> "Error: ${it.message}"
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) progressDialog.show() else progressDialog.dismiss()
        }

        setupPagination()


        viewModel.fetchMovies(apiKey) // Load first page
    }

    private fun setupProgressDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(R.layout.dialog_progress)
        builder.setCancelable(false)
        progressDialog = builder.create()
    }

    private fun setupPagination() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                // Load next page when user scrolls to the bottom
                if (lastVisibleItem >= totalItemCount - 5) {
                    viewModel.fetchMovies(apiKey)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
