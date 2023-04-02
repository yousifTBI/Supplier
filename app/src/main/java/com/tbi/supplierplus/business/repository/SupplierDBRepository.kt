package com.tbi.supplierplus.business.repository

import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.framework.database.SupplierDao

import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

interface SupplierDBRepository {
    fun get(): Flow<List<User>>

    suspend fun save(user: User)
    suspend fun clear()
}

class SupplierDBRepositoryImpl @Inject constructor(private val dao: SupplierDao) :
    SupplierDBRepository {
    override fun get(): Flow<List<User>> = dao.getUsers()

    override suspend fun save(user: User) {
        dao.deleteAll()
        dao.insert(user)
    }

    override suspend fun clear() = dao.deleteAll()
}