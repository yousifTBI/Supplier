package com.tbi.supplierplus.framework.database

import androidx.room.*
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.framework.ui.login.DistrputerLogin
import kotlinx.coroutines.flow.Flow

@Dao
interface DistrputerLoginDao {
    @Query(value = "select * from user_tbi")
    fun getUsers(): Flow<List<DistrputerLogin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insert(user: DistrputerLogin)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(user: DistrputerLogin)

    @Query(value = "DELETE from user_tbi")
    suspend  fun deleteAll()

    @Delete
    suspend fun delete(user: DistrputerLogin)
}