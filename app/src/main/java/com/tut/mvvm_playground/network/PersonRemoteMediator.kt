package com.tut.mvvm_playground.network

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.tut.mvvm_playground.data.AppDatabase
import com.tut.mvvm_playground.models.Person
import com.tut.mvvm_playground.models.PersonRemoteKeys
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@OptIn(ExperimentalPagingApi::class)
@Singleton
class PersonRemoteMediator @Inject constructor(
    private val db: AppDatabase,
    private val api: PersonApi
) : RemoteMediator<Int, Person>() {
    private val remoteKeyId = 1

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Person>
    ): MediatorResult {
        return try {
            val page = when(loadType) {
                LoadType.REFRESH -> {
                    // Indicates this is the first time this query is being called and no page info
                    // associated with the given query is available in the PersonRemoteKeys table
                    null
                }
                LoadType.PREPEND -> {
                    // Since current implementation only looks at a single direction scroll scenario
                    // no need of prepending network data to current list
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    // Gets the info regarding the next page to be fetched for a given query id
                    // from the PersonRemoteKeys table
                    val remoteKey = db.withTransaction {
                        db.PersonRemoteKeysDao().findById(remoteKeyId)
                    }
                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextKey
                }
            }
            val response = api.getUsers(page?:1)
            if(!response.isSuccessful) throw HttpException(response)

            // Checks if the current fetched response body is the last available page
            val isLastPage = response.body()!!.isEmpty()

            //db.with Transaction indicates that all db transactions inside the block should be
            // treated as one, and if any fails, then all should be roll backed
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.PersonRemoteKeysDao().clearRemoteKeys()
                    db.PersonDao().clearPerson()
                }
                val nextKey = if (isLastPage) null else {
                    if (page == null) 2 //Goes to second page
                    else page+1
                }
                db.PersonRemoteKeysDao().insert(
                    PersonRemoteKeys(remoteKeyId, nextKey)
                )
                db.PersonDao().insert(response.body()!!)
            }
            MediatorResult.Success(endOfPaginationReached = isLastPage)
        } catch (e: IOException) {
            Log.e("PersonRemoteMediator", "load: IOException", e)
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e("PersonRemoteMediator", "load: HttpException", e)
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.e("PersonRemoteMediator", "load: Exception", e)
            MediatorResult.Error(e)
        }
    }
}