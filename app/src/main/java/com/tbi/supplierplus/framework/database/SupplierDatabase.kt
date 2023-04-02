package com.tbi.supplierplus.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.framework.ui.login.DistrputerLogin

@Database(entities = [User::class,DistrputerLogin::class], version = 4, exportSchema = false)
abstract class SupplierDatabase : RoomDatabase() {
    abstract fun dao(): SupplierDao
    abstract fun dao2(): DistrputerLoginDao
}
