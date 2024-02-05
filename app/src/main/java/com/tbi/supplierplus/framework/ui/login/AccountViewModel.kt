package com.tbi.supplierplus.framework.ui.login

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.*
import com.tbi.supplierplus.business.pojo.RegistrationModel
import dagger.hilt.android.internal.Contexts.getApplication

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect


@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: LoginRepositoryImpl,
    application: Application
) :
    AndroidViewModel(application)  {





    var loginInfoCombVLiveData = MutableLiveData<State<Task3<DistrputerLogin>>>()

    fun loginInfoCombVM(androidID: String,context: Context) {
        viewModelScope.launch {

            repository.loginInfoComb(androidID,context).collect {

                loginInfoCombVLiveData.value = it
            }
        }
    }

  var registrationLiveData = MutableLiveData<State<Task3<RegistrationModel>>>()

    fun RegistrationInfo(registrationModel: RegistrationModel,context: Context) {
        viewModelScope.launch {

            repository.RegistrationInfo(registrationModel,context).collect {

                registrationLiveData.value = it
            }

        }
    }


//    var branchInfoCombVLiveData = MutableLiveData<State<Task2<BranchModel>>>()
//
//    fun branchInfoCombVM(ComID: String) {
//        viewModelScope.launch {
//
//            repository.branchInfoComb(ComID).collect {
//
//                branchInfoCombVLiveData.value = it
//            }
//
//        }
//    }


//   fun GetBranchViewModel(ComID: String){
//      Log.d("GetBranchViewModel",ComID)
//      viewModelScope.launch {
//
//         repository.GetBranchAPIRepo2(ComID).collect{
//
//
//             BranchLiveData.value=it
//              Log.d("GetBranchLiveData", it!!.Branches[0]!!.Name)
//
//
//         }
//
//         repository.GetBranchAPIRepo(ComID).collect{
//
//             Log.d("GetBranchLiveData1", it.toData()!!.Message.toString()+"ddddd")
//             GetBranchLiveData.value=it
//           it.toData()!!.Message?.let { it1 -> Log.d("GetBranchLiveData", it1) }
//           Log.d("GetBranchLiveData", it.toData()!!.Message.toString()+"ddddd")
//         }
//      }
//  }




    private val _isInternetAvailable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean> = _isInternetAvailable

    fun checkInternetConnection() {
        viewModelScope.launch(Dispatchers.IO) {
            val connectivityManager =
                getApplication<Application>().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            val isConnected = networkInfo != null && networkInfo.isConnected
            _isInternetAvailable.postValue(isConnected)
        }
    }


}