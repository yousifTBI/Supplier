package com.tbi.supplierplus.framework.database

import androidx.room.*
import com.tbi.supplierplus.business.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplierDao {
    @Query(value = "select * from user_tbl")
    fun getUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend  fun insert(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(user: User)

    @Query(value = "DELETE from user_tbl")
    suspend  fun deleteAll()

    @Delete
    suspend fun delete(user: User)
}