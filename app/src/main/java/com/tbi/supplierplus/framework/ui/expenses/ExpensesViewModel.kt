package com.tbi.supplierplus.framework.ui.expenses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbi.supplierplus.business.models.Expenses
import com.tbi.supplierplus.business.models.ExpensesType
import com.tbi.supplierplus.business.models.Item
import com.tbi.supplierplus.business.models.User
import com.tbi.supplierplus.business.pojo.expenses.AddExpenses
import com.tbi.supplierplus.business.pojo.expenses.ExpensesSearch
import com.tbi.supplierplus.business.pojo.expenses.Expensese
import com.tbi.supplierplus.business.repository.ExpensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(val repo: ExpensesRepository) : ViewModel() {

    val user = MutableLiveData<User>()
    val selectedItem = MutableLiveData<ExpensesType>()
    val money = MutableLiveData<String>("0")
    val comment = MutableLiveData<String>("")

    // list of data
    val liveData: MutableLiveData<List<Expenses>> by lazy {
        MutableLiveData<List<Expenses>>()
    }

    // for the spinner
    val expensesTypesLiveData: MutableLiveData<List<ExpensesType>> by lazy {
        MutableLiveData<List<ExpensesType>>()
    }

    // the expenses in the list
    val returnExpensesList = MutableLiveData<List<Expensese>>()
    val TotalExpensesObj = MutableLiveData<Expensese?>()

    //ExpensesItemsSearch
    val ExpensesItemsSearchList = MutableLiveData<List<ExpensesSearch>>()


    fun getExpenses(){
        viewModelScope.launch {
            repo. GetExpenses("2").collect{
                returnExpensesList.value = it.data
                TotalExpensesObj.value = it.Item
            }
        }

    }

    fun addExpenses(addExpenses: AddExpenses) {
        viewModelScope.launch {

            repo.addExpenses(addExpenses).collect {

                returnExpensesList.value = it.data
                TotalExpensesObj.value = it.Item
            }
        }

    }

    fun getExpensesSearchType() {
        viewModelScope.launch {
            repo.getExpensesSearchType().collect {

                ExpensesItemsSearchList.value = it.data
            }
        }
    }


    //  fun getExpenses() {
    //      viewModelScope.launch {
    //          repo.getExpenses(user.value!!.userID).collect {
    //              liveData.value = it
    //          }
    //      }
    //  }

    //  fun getExpensesTypes() {

    //      viewModelScope.launch {
    //          repo.getExpensesTypes(user.value!!.userID).collect {
    //              expensesTypesLiveData.value = it
    //          }
    //      }
    //  }


    //  // method for saving the spinner choice in the database
    //  fun addExpenses() =
    //      viewModelScope.launch {
    //          repo.addExpenses(
    //              user.value!!.userID,
    //              money.value!!,
    //              comment.value!!,
    //              selectedItem.value!!.id
    //          ).collect {
    //              liveData.value = it

    //          }
    //      }


    //  fun setUser(user: User) {
    //      this.user.value = user
    //      getExpenses()
    //      getExpensesTypes()
    //  }

}