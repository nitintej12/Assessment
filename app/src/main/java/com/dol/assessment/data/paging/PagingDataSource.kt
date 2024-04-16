package com.dol.assessment.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dol.assessment.data.model.Post
import com.dol.assessment.data.repositories.ApiRepositoryImpl
import com.dol.assessment.utils.NetworkResult
import retrofit2.HttpException
import java.io.IOException


const val POST_STARTING_PAGE_INDEX = 1

class PostPagingSource(private val repo: ApiRepositoryImpl) : PagingSource<Int, Post>() {

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            var response = emptyList<Post>()
            val position = params.key ?: POST_STARTING_PAGE_INDEX
            repo.getPosts(page = position).collect {
                if (it is NetworkResult.Success) {
                    response = it.data!!
                }
            }
            val prevKey = if (position == POST_STARTING_PAGE_INDEX) null else position - 1
            val nextKey = if (response.isEmpty()) null else position + 1
            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}