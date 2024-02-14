package com.teumteum.data.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.teumteum.domain.TeumTeumDataStore
import com.teumteum.data.datasource.remote.RemoteUserDataSource
import com.teumteum.data.model.request.RequestUserInfo
import com.teumteum.data.model.response.responseToReview
import com.teumteum.domain.entity.Alerts
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.FriendRecommend
import com.teumteum.domain.entity.Review
import com.teumteum.domain.entity.SignUpResult
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.entity.Users
import com.teumteum.domain.entity.toUpdatedUserInfo
import com.teumteum.domain.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataStore: TeumTeumDataStore,
    private val dataSource: RemoteUserDataSource
) : UserRepository {

    private val gsonBuilder: Gson = GsonBuilder().create()

    override fun getUserInfo(): UserInfo? {
        return try {
            gsonBuilder.fromJson(dataStore.userInfo, UserInfo::class.java)
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    override fun saveUserInfo(userInfo: UserInfo) {
        dataStore.userInfo = gsonBuilder.toJson(userInfo)
    }

    override suspend fun getMyInfoFromServer(): Result<UserInfo> {
        return runCatching {
            dataSource.getMyUserInfo()
        }
    }

    override suspend fun postUserInfo(
        user: UserInfo,
        oauthId: String,
        serviceAgreed: Boolean,
        privatePolicyAgreed: Boolean
    ): Result<SignUpResult> {
        return runCatching {
            val requestObject = RequestUserInfo(
                userInfo = user,
                oauthId = oauthId,
                serviceAgreed = serviceAgreed,
                privatePolicyAgreed = privatePolicyAgreed
            ).getRequestUserInfoWithOAuthId()
            dataSource.postUserInfo(requestObject)
        }
    }

    override fun deleteUserInfo() {
        dataStore.userInfo = ""
    }

    override suspend fun getUserFriends(userId: Long): Result<FriendRecommend> {
            return runCatching {
                dataSource.getUserFriend(userId)
            }
    }

    override suspend fun updateUserInfo(
        userInfo: UserInfo
    ): Result<Unit> {
        return runCatching {
            val request = userInfo.toUpdatedUserInfo()
            dataSource.updateUserInfo(request)
        }
    }

    override suspend fun getUser(userId: Long): Result<Friend> {
        return runCatching {
            dataSource.getUser(userId)
        }
    }

    override suspend fun getUsers(id: String): Result<Users> {
        return runCatching {
            dataSource.getUsers(id)
        }
    }

    override suspend fun getFriendInfo(userId: Long): UserInfo? {
        return dataSource.getFriendInfo(userId)
    }

    override suspend fun postFriend(userId: Long): Result<Unit> {
        return runCatching {
            dataSource.postFriend(userId)
        }
    }

    override suspend fun getAlerts(): Result<Alerts> {
        return runCatching {
            dataSource.getAlerts()
        }
    }

    override suspend fun getUserReview(userId: Long): Result<List<Review>> {
        return runCatching {
            val responseReviews = dataSource.getUserReview(userId)
            responseToReview(responseReviews.reviews)
        }
    }
}