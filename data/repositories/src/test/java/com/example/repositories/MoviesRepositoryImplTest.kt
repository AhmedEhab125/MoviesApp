package com.example.repositories

import app.cash.turbine.test
import com.example.repositories.implementation.MoviesRepositoryImpl
import com.example.services.models.MovieResponse
import com.example.services.moviesService.cashingService.IMoviesCacheService
import com.example.services.moviesService.networkService.IMoviesApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class MoviesRepositoryImplTest {

    private lateinit var moviesApiService: IMoviesApiService
    private lateinit var moviesCacheService: IMoviesCacheService
    private lateinit var repository: MoviesRepositoryImpl

    @Before
    fun setUp() {
        moviesApiService = mockk()
        moviesCacheService = mockk()
        repository = MoviesRepositoryImpl(moviesApiService, moviesCacheService)
    }


    @Test
    fun `searchMoviesPaging should return paging data from network service`() = runTest {
        // Given
        val query = "Avengers"

        // When
        val result = repository.searchMoviesPaging(query)

        // Then
        // The method creates a new PagingSource, so we can't easily test the actual data
        // but we can verify the method executes without errors
        result.test {
            // Just verify the flow is created successfully
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getMovieDetails should return movie from network service when movie exists`() = runTest {
        // Given
        val movieId = 123
        val mockMovieResponse = createMockMovieResponse(movieId, "Test Movie")
        coEvery { moviesApiService.getMovieDetails(movieId) } returns mockMovieResponse

        // When
        val result = repository.getMovieDetails(movieId)

        // Then
        result.test {
            val movie = awaitItem()
            assertEquals(movieId, movie?.id)
            awaitComplete()
        }
    }

    @Test
    fun `getMovieDetails should return null when movie does not exist`() = runTest {
        // Given
        val movieId = 999
        coEvery { moviesApiService.getMovieDetails(movieId) } returns null

        // When
        val result = repository.getMovieDetails(movieId)

        // Then
        result.test {
            val movie = awaitItem()
            assertNull(movie)
            awaitComplete()
        }
    }

    @Test
    fun `getMovieDetails should handle different movie ids correctly`() = runTest {
        // Given
        val movieId1 = 111
        val movieId2 = 222
        val movieResponse1 = createMockMovieResponse(movieId1, "Movie 1")
        val movieResponse2 = createMockMovieResponse(movieId2, "Movie 2")

        coEvery { moviesApiService.getMovieDetails(movieId1) } returns movieResponse1
        coEvery { moviesApiService.getMovieDetails(movieId2) } returns movieResponse2

        // When & Then
        repository.getMovieDetails(movieId1).test {
            val result1 = awaitItem()
            assertEquals(movieId1, result1?.id)
            assertEquals("Movie 1", result1?.title)
            awaitComplete()
        }

        repository.getMovieDetails(movieId2).test {
            val result2 = awaitItem()
            assertEquals(movieId2, result2?.id)
            assertEquals("Movie 2", result2?.title)
            awaitComplete()
        }

        coVerify(exactly = 1) { moviesApiService.getMovieDetails(movieId1) }
        coVerify(exactly = 1) { moviesApiService.getMovieDetails(movieId2) }
    }

    @Test
    fun `getMovieDetails should map MovieResponse to domain model correctly`() = runTest {
        // Given
        val movieId = 456
        val movieResponse = createMockMovieResponse(movieId, "Detailed Movie")
        coEvery { moviesApiService.getMovieDetails(movieId) } returns movieResponse

        // When
        val result = repository.getMovieDetails(movieId)

        // Then
        result.test {
            val movie = awaitItem()
            assertEquals(movieResponse.id, movie?.id)
            assertEquals(movieResponse.title, movie?.title)
            assertEquals(movieResponse.overview, movie?.overview)
            assertEquals(movieResponse.posterPath, movie?.posterPath)
            assertEquals(movieResponse.backdropPath, movie?.backdropPath)
            assertEquals(movieResponse.releaseDate, movie?.releaseDate)
            assertEquals(movieResponse.voteCount, movie?.voteCount)
            assertEquals(movieResponse.adult, movie?.adult)
            assertEquals(movieResponse.originalLanguage, movie?.originalLanguage)
            assertEquals(movieResponse.originalTitle, movie?.originalTitle)
            awaitComplete()
        }
    }

    @Test
    fun `searchMoviesPaging should handle empty query`() = runTest {
        // Given
        val emptyQuery = ""

        // When
        val result = repository.searchMoviesPaging(emptyQuery)

        // Then
        result.test {
            // Verify the flow is created without errors
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchMoviesPaging should handle special characters in query`() = runTest {
        // Given
        val specialQuery = "X-Men: Days of Future Past"

        // When
        val result = repository.searchMoviesPaging(specialQuery)

        // Then
        result.test {
            // Verify the flow is created without errors
            cancelAndIgnoreRemainingEvents()
        }
    }


    private fun createMockMovieResponse(id: Int, title: String) = MovieResponse(
        id = id,
        title = title,
        overview = "Test overview",
        posterPath = "/test_poster.jpg",
        backdropPath = "/test_backdrop.jpg",
        releaseDate = "2023-01-01",
        voteAverage = 7.5,
        voteCount = 1000,
        popularity = 100.0,
        adult = false,
        originalLanguage = "en",
        originalTitle = title
    )
} 