package com.example.use_case

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.example.models.Movie
import com.example.repositories_interfaces.IMoviesRepository
import com.example.use_case.getPopularMoviesPagingUseCase.GetPopularMoviesPagingUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPopularMoviesPagingUseCaseImplTest {

    private lateinit var moviesRepository: IMoviesRepository
    private lateinit var useCase: GetPopularMoviesPagingUseCaseImpl

    @Before
    fun setUp() {
        moviesRepository = mockk()
        useCase = GetPopularMoviesPagingUseCaseImpl(moviesRepository)
    }

    @Test
    fun `invoke should return paging data from repository`() = runTest {
        // Given
        val mockMovies = listOf(
            createMockMovie(1, "Movie 1"),
            createMockMovie(2, "Movie 2"),
            createMockMovie(3, "Movie 3")
        )
        val pagingData = PagingData.from(mockMovies)
        every { moviesRepository.getPopularMoviesPaging() } returns flowOf(pagingData)

        // When
        val result = useCase()

        // Then
        verify(exactly = 1) { moviesRepository.getPopularMoviesPaging() }

        // Verify the content of the paging data
        val snapshot = result.asSnapshot()
        assertEquals(3, snapshot.size)
        assertEquals("Movie 1", snapshot[0].title)
        assertEquals("Movie 2", snapshot[1].title)
        assertEquals("Movie 3", snapshot[2].title)
    }

    @Test
    fun `invoke should return empty paging data when repository returns empty data`() = runTest {
        // Given
        val emptyPagingData = PagingData.from(emptyList<Movie>())
        every { moviesRepository.getPopularMoviesPaging() } returns flowOf(emptyPagingData)

        // When
        val result = useCase()

        // Then
        verify(exactly = 1) { moviesRepository.getPopularMoviesPaging() }

        val snapshot = result.asSnapshot()
        assertEquals(0, snapshot.size)
    }

    @Test
    fun `invoke should delegate to repository without any transformation`() = runTest {
        // Given
        val mockMovies = listOf(createMockMovie(1, "Test Movie"))
        val pagingData = PagingData.from(mockMovies)
        every { moviesRepository.getPopularMoviesPaging() } returns flowOf(pagingData)

        // When
        val result = useCase()

        // Then
        verify(exactly = 1) { moviesRepository.getPopularMoviesPaging() }

        // Verify that the use case doesn't modify the data
        val snapshot = result.asSnapshot()
        assertEquals(1, snapshot.size)
        assertEquals(1, snapshot[0].id)
        assertEquals("Test Movie", snapshot[0].title)
    }

    private fun createMockMovie(id: Int, title: String) = Movie(
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