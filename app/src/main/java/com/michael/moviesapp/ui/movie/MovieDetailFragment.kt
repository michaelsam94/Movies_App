package com.michael.moviesapp.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.michael.moviesapp.R
import com.michael.moviesapp.data.local.FavoriteMovie
import com.michael.moviesapp.databinding.FragmentMovieDetailBinding
import com.michael.moviesapp.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()
    private var isFavorite = false
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movie

        binding.movieTitle.text = movie.title
        binding.movieOverview.text = movie.overview
        binding.movieReleaseDate.text = "Release Date: ${movie.release_date}"
        binding.movieRating.text = "Rating: ${movie.vote_average}"
        binding.movieLanguage.text = "Language: ${movie.original_language}"

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error_image)
            .into(binding.moviePoster)

        viewModel.isFavorite(movie.id).observe(viewLifecycleOwner) { favorite ->
            isFavorite = favorite
            binding.favoriteButton.text = if (favorite) "Remove from Favorites" else "Add to Favorites"
        }

        binding.favoriteButton.setOnClickListener {
            val favoriteMovie = FavoriteMovie(
                id = movie.id,
                title = movie.title,
                poster_path = movie.poster_path,
                release_date = movie.release_date,
                vote_average = movie.vote_average,
                overview = movie.overview,
                original_language = movie.original_language
            )

            if (isFavorite) {
                viewModel.removeFavorite(favoriteMovie)
            } else {
                viewModel.addFavorite(favoriteMovie)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
