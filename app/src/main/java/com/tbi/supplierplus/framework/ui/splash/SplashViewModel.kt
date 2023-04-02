package com.tbi.supplierplus.framework.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.repository.SupplierDBRepository
import com.tbi.supplierplus.business.utils.toJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val repo: SupplierDBRepository) :
    ViewModel() {

    var navToRegister = MutableLiveData(false)
    var user = MutableLiveData<User?>(null)

    init {
        viewModelScope.launch { checkIsLogged() }
        viewModelScope.launch { repo.save(User("peter_tbi", "", "", "3", "", "2", "", "")) }
    }

    private suspend fun checkIsLogged() {
        repo.get().collect {
            if (it.isNotEmpty())
                if (it[0].userID.isNotEmpty() && it[0].userID != "0") {
                    user.value = it[0]
                } else navToRegister.value = true
            else navToRegister.value = true
        }
    }

    fun onDoneNavigation() {
        navToRegister.value = false
        user.value = null
    }

}
