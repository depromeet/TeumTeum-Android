package com.teumteum.domain.repository

import com.teumteum.domain.entity.Group

interface GroupRepository {
    suspend fun getSearchGroup(keyword: String): Result<List<Group>>
}