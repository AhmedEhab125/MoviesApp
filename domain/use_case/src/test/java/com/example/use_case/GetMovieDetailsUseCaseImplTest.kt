package com.example.use_case

import app.cash.turbine.test
import com.example.models.Movie
import com.example.repositories_interfaces.IMoviesRepository
import com.example.use_case.getMovieDetailsUseCase.GetMovieDetailsUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetMovieDetailsUseCaseImplTest {

    private lateinit var moviesRepository: IMoviesRepository
    private lateinit var useCase: GetMovieDetailsUseCaseImpl

    @Before
    fun setUp() {
        moviesRepository = mockk()
        useCase = GetMovieDetailsUseCaseImpl(moviesRepository)
    }

    @Test
    fun `invoke should return movie details from repository when movie exists`() = runTest {
        // Given
        val movieId = 123
        val expectedMovie = createMockMovie(movieId, "Test Movie")
        every { moviesRepository.getMovieDetails(movieId) } returns flowOf(expectedMovie)

        // When
        val result = useCase(movieId)

        // Then
        verify(exactly = 1) { moviesRepository.getMovieDetails(movieId) }

        result.test {
            val movie = awaitItem()
            assertEquals(expectedMovie.id, movie?.id)
            assertEquals(expectedMovie.title, movie?.title)
            assertEquals(expectedMovie.overview, movie?.overview)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should return null when movie does not exist`() = runTest {
        // Given
        val movieId = 999
        every { moviesRepository.getMovieDetails(movieId) } returns flowOf(null)

        // When
        val result = useCase(movieId)

        // Then
        verify(exactly = 1) { moviesRepository.getMovieDetails(movieId) }

        result.test {
            val movie = awaitItem()
            assertNull(movie)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should pass correct movie id to repository`() = runTest {
        // Given
        val movieId = 456
        val expectedMovie = createMockMovie(movieId, "Another Movie")
        every { moviesRepository.getMovieDetails(movieId) } returns flowOf(expectedMovie)

        // When
        useCase(movieId)

        // Then
        verify(exactly = 1) { moviesRepository.getMovieDetails(movieId) }
    }

    @Test
    fun `invoke should handle different movie ids correctly`() = runTest {
        // Given
        val movieId1 = 111
        val movieId2 = 222
        val movie1 = createMockMovie(movieId1, "Movie 1")
        val movie2 = createMockMovie(movieId2, "Movie 2")

        every { moviesRepository.getMovieDetails(movieId1) } returns flowOf(movie1)
        every { moviesRepository.getMovieDetails(movieId2) } returns flowOf(movie2)

        // When & Then
        useCase(movieId1).test {
            val result1 = awaitItem()
            assertEquals(movieId1, result1?.id)
            assertEquals("Movie 1", result1?.title)
            awaitComplete()
        }

        useCase(movieId2).test {
            val result2 = awaitItem()
            assertEquals(movieId2, result2?.id)
            assertEquals("Movie 2", result2?.title)
            awaitComplete()
        }

        verify(exactly = 1) { moviesRepository.getMovieDetails(movieId1) }
        verify(exactly = 1) { moviesRepository.getMovieDetails(movieId2) }
    }

    @Test
    fun `invoke should delegate to repository without any transformation`() = runTest {
        // Given
        val movieId = 789
        val originalMovie = createMockMovie(movieId, "Original Movie")
        every { moviesRepository.getMovieDetails(movieId) } returns flowOf(originalMovie)

        // When
        val result = useCase(movieId)

        // Then
        result.test {
            val movie = awaitItem()
            // Verify that the use case doesn't modify the data
            assertEquals(originalMovie, movie)
            awaitComplete()
        }
    }

    private fun createMockMovie(id: Int, title: String) = Movie(
        id = id,
        title = title,
        overview = "Test overview for $title",
        posterPath = "/test_poster_$id.jpg",
        backdropPath = "/test_backdrop_$id.jpg",
        releaseDate = "2023-01-01",
        voteAverage = 7.5,
        voteCount = 1000,
        popularity = 100.0,
        adult = false,
        originalLanguage = "en",
        originalTitle = title
    )
} 