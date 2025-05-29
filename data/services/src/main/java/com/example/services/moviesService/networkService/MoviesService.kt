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

internal class MoviesService(
    private val httpClient: HttpClient,
    private val networkHelper: NetworkHelper
) : IMoviesApiService {
    override suspend fun getPopularMovies(page: Int): MoviesResponse? {
        return networkHelper.processCall {
            httpClient.get(ApiEndpoints.POPULAR_MOVIES) {
                parameter(ApiConstants.PARAM_PAGE, page)
            }
        }
    }

    override suspend fun searchMovies(query: String, page: Int): MoviesResponse? {
        return networkHelper.processCall {
            httpClient.get(ApiEndpoints.SEARCH_MOVIES) {
                parameter(ApiConstants.PARAM_QUERY, query)
                parameter(ApiConstants.PARAM_PAGE, page)
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieResponse? {
        return networkHelper.processCall {
            httpClient.get(ApiEndpoints.MOVIE_DETAILS) {
                url.appendPathSegments(movieId.toString())
            }
        }
    }
} 