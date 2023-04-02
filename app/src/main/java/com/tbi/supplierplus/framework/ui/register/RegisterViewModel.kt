package com.tbi.supplierplus.framework.ui.register

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.repository.AuthRepository
import com.tbi.supplierplus.business.repository.SupplierDBRepository
import com.tbi.supplierplus.business.utils.getAndroidID
import com.tbi.supplierplus.business.utils.getSerialID
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dbRepository: SupplierDBRepository,
    @ApplicationContext val context: Context
) :
    ViewModel() {
    var userName = ""
    var msg = MutableLiveData<String?>(null)
    suspend fun register() =
        repository.register(
            getAndroidID(context),
            getSerialID(),
            userName
        )

     fun login() = repository.login(getAndroidID(context), getSerialID())

    init {
        Log.i("ResultAndroid", "${getAndroidID(context)}-${getSerialID()}")
    }

    suspend fun saveUserData(user: User) = dbRepository.save(user)

    fun onShowMsgDone() {
        msg.value = null
    }
}