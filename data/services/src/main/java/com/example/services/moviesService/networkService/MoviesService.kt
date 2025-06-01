package com.example.services.moviesService.networkService

import com.example.network.NetworkHelper
import com.example.services.ApiConstants
import com.example.services.ApiEndpoints
import com.example.services.models.MovieResponse
import com.example.services.models.MoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments

/**
 * Service class that handles all movie-related network operations.
 * Implements [IMoviesApiService] to provide movie data from the remote API.
 *
 * Features:
 * - Fetches popular movies with pagination
 * - Performs movie searches
 * - Retrieves detailed movie information
 * - Uses Ktor client for network operations
 * - Includes error handling and network state management
 *
 * @property httpClient Ktor HTTP client for making network requests
 * @property networkHelper Helper class for handling network operations and errors
 */
internal class MoviesService(
    private val httpClient: HttpClient,
    private val networkHelper: NetworkHelper
) : IMoviesApiService {

    /**
     * Fetches a paginated list of popular movies from the API.
     *
     * @param page The page number to fetch (1-based indexing)
     * @return [MoviesResponse] containing the list of movies and pagination info, or null if the request fails
     */
    override suspend fun getPopularMovies(page: Int): MoviesResponse? {
        return networkHelper.processCall {
            httpClient.get(ApiEndpoints.POPULAR_MOVIES) {
                parameter(ApiConstants.PARAM_PAGE, page)
            }
        }
    }

    /**
     * Searches for movies based on a query string.
     *
     * @param query The search query string
     * @param page The page number for paginated results (1-based indexing)
     * @return [MoviesResponse] containing the search results and pagination info, or null if the request fails
     */
    override suspend fun searchMovies(query: String, page: Int): MoviesResponse? {
        return networkHelper.processCall {
            httpClient.get(ApiEndpoints.SEARCH_MOVIES) {
                parameter(ApiConstants.PARAM_QUERY, query)
                parameter(ApiConstants.PARAM_PAGE, page)
            }
        }
    }

    /**
     * Fetches detailed information for a specific movie.
     *
     * @param movieId The unique identifier of the movie
     * @return [MovieResponse] containing detailed movie information, or null if the request fails
     */
    override suspend fun getMovieDetails(movieId: Int): MovieResponse? {
        return networkHelper.processCall {
            httpClient.get(ApiEndpoints.MOVIE_DETAILS) {
                url.appendPathSegments(movieId.toString())
            }
        }
    }
} 