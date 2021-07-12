package com.codinginflow.YukProduktif.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.codinginflow.YukProduktif.data.PreferencesManager
import com.codinginflow.YukProduktif.data.SortOrder
import com.codinginflow.YukProduktif.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

//di class view model ini untuk menyimpan dan mengelola data yang biasanya berhubungan dengan UI/ Tampilan
// sehingga data tersebut dapat digunakan kembali saat terjadi perubahan konfigurasi
class TasksViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() { //dengan model constructor dengan parameter yang diambil dr private dengan nama variable taskkdao, preferences manager script
    //variable untuk menangkap query
    val searchQuery = MutableStateFlow("")
    //variable untuk preference flow
    val preferencesFlow = preferencesManager.preferencesFlow
    //method untuk combinasi data
    //untuk  mengatur  fungsi  dari filter hide completed, sort orderselecter, dan hide task yang sudah
    private val  taskFlow = combine(
        searchQuery,
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        taskDao.getTasks(query, filterPreferences.sortOrder, filterPreferences.hideCompleted)
    }
    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }
    fun onHideCompleteClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }
    val tasks = taskFlow.asLiveData()
}