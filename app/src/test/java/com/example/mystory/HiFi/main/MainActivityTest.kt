package com.example.mystory.HiFi.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.mystory.DataDummy
import com.example.mystory.HiFi.paging.PagingRepository
import com.example.mystory.MainDispatcherRule
import com.example.mystory.adapter.PagingListAdapter
import com.example.mystory.data.ListItem
import com.example.mystory.data.respository.StoryRepository
import com.example.mystory.data.respository.UserRepository
import com.example.mystory.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Mock
    private lateinit var pagingRepository: PagingRepository


    @Test
    fun `when Get Quote Should Not Null, and Return Data!`() = runTest {
        val dummyStory = DataDummy.generateDummyStoryResponse()
        val data: PagingData<ListItem> = StoryPagingSource.snapshot(dummyStory)

        val expectedStory = MutableLiveData<PagingData<ListItem>>().apply { value = data }
        Mockito.`when`(pagingRepository.getStory(token = String())).thenReturn(expectedStory)

        val mainViewModel = MainViewModel(userRepository, storyRepository, pagingRepository)
        val actualStory = mainViewModel.getStory(token = String()).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = PagingListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(actualStory)

        with(differ.snapshot()) {
            assertNotNull(this)
            assertEquals(dummyStory.size, size)
            assertEquals(dummyStory[0], this[0])
        }
    }


    @Test
    fun `when Get Quote Empty, Should Return No Data!`() = runTest {

        val data: PagingData<ListItem> = PagingData.from(emptyList())
        val expectedStory = MutableLiveData<PagingData<ListItem>>().apply { value = data }
        Mockito.`when`(pagingRepository.getStory(token = String())).thenReturn(expectedStory)

        val mainViewModel = MainViewModel(userRepository, storyRepository, pagingRepository)
        val actualStory = mainViewModel.getStory(token = String()).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = PagingListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(actualStory)
        assertEquals(0, differ.snapshot().size)
    }

}

class StoryPagingSource(private val items: List<ListItem>) : PagingSource<Int, ListItem>() {

    override fun getRefreshKey(state: PagingState<Int, ListItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        return LoadResult.Page(data = items, prevKey = null, nextKey = null)
    }

    companion object {
        fun snapshot(items: List<ListItem>): PagingData<ListItem> {
            return PagingData.from(items)
        }
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}

    override fun onRemoved(position: Int, count: Int) {}

    override fun onMoved(fromPosition: Int, toPosition: Int) {}

    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}