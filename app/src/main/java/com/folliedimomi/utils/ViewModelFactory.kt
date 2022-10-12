package com.folliedimomi.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    //private val tasksRepository: TasksRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            /* when {
                 isAssignableFrom(StatisticsViewModel::class.java) ->
                     StatisticsViewModel(tasksRepository)
                 isAssignableFrom(TaskDetailViewModel::class.java) ->
                     TaskDetailViewModel(tasksRepository)
                 isAssignableFrom(AddEditTaskViewModel::class.java) ->
                     AddEditTaskViewModel(tasksRepository)
                 isAssignableFrom(TasksViewModel::class.java) ->
                     TasksViewModel(tasksRepository)
                 else ->
                     throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
             }*/
        } as T
}