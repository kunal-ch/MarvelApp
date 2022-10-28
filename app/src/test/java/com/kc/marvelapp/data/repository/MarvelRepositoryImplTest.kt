package com.kc.marvelapp.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.kc.marvelapp.data.local.CharacterDao
import com.kc.marvelapp.data.local.CharacterDaoFake
import com.kc.marvelapp.data.local.MarvelDatabase
import com.kc.marvelapp.data.remote.MarvelApi
import com.kc.marvelapp.domain.models.*
import com.kc.marvelapp.util.Resource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class MarvelRepositoryImplTest {

    private val comicItemMock = mockk<Comics>()
    private val thumbailMock = mockk<Thumbnail>()

    private val characterListings = (1..100).map {
        ComicCharacter(
            id = it,
            modified = LocalDateTime.now().toString(),
            description = "description$it",
            name = "name$it",
            resourceURI = "resourceURI$it",
            thumbnail = thumbailMock,
            comics = comicItemMock,
        )
    }

    private val mData = Data(
        count = 1,
        limit = 1,
        offset = 1,
        total = 1,
        characters = characterListings
    )

    private val allCharactersResponse = AllCharactersResponse(
        attributionHTML = "attributionHTML",
        attributionText = "attributionText",
        code = 1,
        copyright = "copyright",
        etag = "etag",
        status = "status",
        data = mData
    )

    private lateinit var repository: MarvelRepositoryImpl
    private lateinit var api: MarvelApi
    private lateinit var db: MarvelDatabase
    private lateinit var characterDao: CharacterDao

    @Before
    fun setUp() {
        api = mockk(relaxed = true) {
            coEvery { getAllCharacters() } returns Response.success(allCharactersResponse)
        }
        characterDao = CharacterDaoFake()
        db = mockk(relaxed = true) {
            every { dao } returns characterDao
        }
        repository = MarvelRepositoryImpl(
            api = api,
            database = db,
        )
    }

    @Test
    fun `Test local database cache with fetch from remote set to true`() = runTest {
        val localListings = listOf(
            ComicCharacter(
                id = 0,
                modified = LocalDateTime.now().toString(),
                description = "description",
                name = "name",
                resourceURI = "resourceURI",
                thumbnail = thumbailMock,
                comics = comicItemMock,
            )
        )
        characterDao.insertList(localListings)

        repository.getCharacterListings(
            fetchFromRemote = true,
            query = ""
        ).test {
            val startLoading = awaitItem()
            assertThat((startLoading as Resource.Loading).isLoading).isTrue()

            val listingsFromDb = awaitItem()
            assertThat(listingsFromDb is Resource.Success).isTrue()
            assertThat(listingsFromDb.data).isEqualTo(localListings)

            val remoteListingsFromDb = awaitItem()
            assertThat(remoteListingsFromDb is Resource.Success).isTrue()
            assertThat(remoteListingsFromDb.data).isEqualTo(
                characterDao.searchCharacterListing("")
            )

            val stopLoading = awaitItem()
            assertThat((stopLoading as Resource.Loading).isLoading).isFalse()

            awaitComplete()
        }
    }

    @Test
    fun `Test local database cache with fetch from remote set to false`() = runTest {
        val localListings = listOf(
            ComicCharacter(
                id = 0,
                modified = LocalDateTime.now().toString(),
                description = "description",
                name = "name",
                resourceURI = "resourceURI",
                thumbnail = thumbailMock,
                comics = comicItemMock,
            )
        )
        characterDao.insertList(localListings)

        repository.getCharacterListings(
            fetchFromRemote = false,
            query = ""
        ).test {
            val startLoading = awaitItem()
            assertThat((startLoading as Resource.Loading).isLoading).isTrue()

            val listingsFromDb = awaitItem()
            assertThat(listingsFromDb is Resource.Success).isTrue()
            assertThat(listingsFromDb.data).isEqualTo(localListings)

            val stopLoading = awaitItem()
            assertThat((stopLoading as Resource.Loading).isLoading).isFalse()

            awaitComplete()
        }
    }
}