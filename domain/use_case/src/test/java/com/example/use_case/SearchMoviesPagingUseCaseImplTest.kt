package com.example.use_case

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.example.models.Movie
import com.example.repositories_interfaces.IMoviesRepository
import com.example.use_case.searchMoviesPagingUseCase.SearchMoviesPagingUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchMoviesPagingUseCaseImplTest {

    private lateinit var moviesRepository: IMoviesRepository
    private lateinit var useCase: SearchMoviesPagingUseCaseImpl

    @Before
    fun setUp() {
        moviesRepository = mockk()
        useCase = SearchMoviesPagingUseCaseImpl(moviesRepository)
    }

    @Test
    fun `invoke should return search results from repository`() = runTest {
        // Given
        val query = "Avengers"
        val mockMovies = listOf(
            createMockMovie(1, "Avengers: Endgame"),
            createMockMovie(2, "Avengers: Infinity War"),
            createMockMovie(3, "The Avengers")
        )
        val pagingData = PagingData.from(mockMovies)
        every { moviesRepository.searchMoviesPaging(query) } returns flowOf(pagingData)

        // When
        val result = useCase(query)

        // Then
        verify(exactly = 1) { moviesRepository.searchMoviesPaging(query) }

        val snapshot = result.asSnapshot()
        assertEquals(3, snapshot.size)
        assertEquals("Avengers: Endgame", snapshot[0].title)
        assertEquals("Avengers: Infinity War", snapshot[1].title)
        assertEquals("The Avengers", snapshot[2].title)
    }

    @Test
    fun `invoke should return empty results when no movies match query`() = runTest {
        // Given
        val query = "NonExistentMovie"
        val emptyPagingData = PagingData.from(emptyList<Movie>())
        every { moviesRepository.searchMoviesPaging(query) } returns flowOf(emptyPagingData)

        // When
        val result = useCase(query)

        // Then
        verify(exactly = 1) { moviesRepository.searchMoviesPaging(query) }

        val snapshot = result.asSnapshot()
        assertEquals(0, snapshot.size)
    }

    @Test
    fun `invoke should pass exact query to repository`() = runTest {
        // Given
        val query = "Spider-Man"
        val mockMovies = listOf(createMockMovie(1, "Spider-Man: No Way Home"))
        val pagingData = PagingData.from(mockMovies)
        every { moviesRepository.searchMoviesPaging(query) } returns flowOf(pagingData)

        // When
        useCase(query)

        // Then
        verify(exactly = 1) { moviesRepository.searchMoviesPaging(query) }
    }

    @Test
    fun `invoke should handle different search queries correctly`() = runTest {
        // Given
        val query1 = "Batman"
        val query2 = "Superman"
        val batmanMovies = listOf(createMockMovie(1, "The Batman"))
        val supermanMovies = listOf(createMockMovie(2, "Man of Steel"))

        every { moviesRepository.searchMoviesPaging(query1) } returns flowOf(
            PagingData.from(
                batmanMovies
            )
        )
        every { moviesRepository.searchMoviesPaging(query2) } returns flowOf(
            PagingData.from(
                supermanMovies
            )
        )

        // When & Then
        val result1 = useCase(query1)
        val snapshot1 = result1.asSnapshot()
        assertEquals(1, snapshot1.size)
        assertEquals("The Batman", snapshot1[0].title)

        val result2 = useCase(query2)
        val snapshot2 = result2.asSnapshot()
        assertEquals(1, snapshot2.size)
        assertEquals("Man of Steel", snapshot2[0].title)

        verify(exactly = 1) { moviesRepository.searchMoviesPaging(query1) }
        verify(exactly = 1) { moviesRepository.searchMoviesPaging(query2) }
    }

    @Test
    fun `invoke should handle empty query string`() = runTest {
        // Given
        val emptyQuery = ""
        val emptyPagingData = PagingData.from(emptyList<Movie>())
        every { moviesRepository.searchMoviesPaging(emptyQuery) } returns flowOf(emptyPagingData)

        // When
        val result = useCase(emptyQuery)

        // Then
        verify(exactly = 1) { moviesRepository.searchMoviesPaging(emptyQuery) }

        val snapshot = result.asSnapshot()
        assertEquals(0, snapshot.size)
    }

    @Test
    fun `invoke should handle special characters in query`() = runTest {
        // Given
        val specialQuery = "X-Men: Days of Future Past"
        val mockMovies = listOf(createMockMovie(1, "X-Men: Days of Future Past"))
        val pagingData = PagingData.from(mockMovies)
        every { moviesRepository.searchMoviesPaging(specialQuery) } returns flowOf(pagingData)

        // When
        val result = useCase(specialQuery)

        // Then
        verify(exactly = 1) { moviesRepository.searchMoviesPaging(specialQuery) }

        val snapshot = result.asSnapshot()
        assertEquals(1, snapshot.size)
        assertEquals("X-Men: Days of Future Past", snapshot[0].title)
    }

    @Test
    fun `invoke should handle case sensitive queries`() = runTest {
        // Given
        val lowerCaseQuery = "batman"
        val upperCaseQuery = "BATMAN"
        val mockMovies = listOf(createMockMovie(1, "Batman Begins"))
        val pagingData = PagingData.from(mockMovies)

        every { moviesRepository.searchMoviesPaging(lowerCaseQuery) } returns flowOf(pagingData)
        every { moviesRepository.searchMoviesPaging(upperCaseQuery) } returns flowOf(pagingData)

        // When
        val result1 = useCase(lowerCaseQuery)
        val result2 = useCase(upperCaseQuery)

        // Then
        verify(exactly = 1) { moviesRepository.searchMoviesPaging(lowerCaseQuery) }
        verify(exactly = 1) { moviesRepository.searchMoviesPaging(upperCaseQuery) }

        val snapshot1 = result1.asSnapshot()
        val snapshot2 = result2.asSnapshot()
        assertEquals(1, snapshot1.size)
        assertEquals(1, snapshot2.size)
    }

    @Test
    fun `invoke should delegate to repository without any transformation`() = runTest {
        // Given
        val query = "Test Movie"
        val originalMovies = listOf(createMockMovie(1, "Test Movie"))
        val pagingData = PagingData.from(originalMovies)
        every { moviesRepository.searchMoviesPaging(query) } returns flowOf(pagingData)

        // When
        val result = useCase(query)

        // Then
        val snapshot = result.asSnapshot()
        assertEquals(1, snapshot.size)
        // Verify that the use case doesn't modify the data
        assertEquals(originalMovies[0], snapshot[0])
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