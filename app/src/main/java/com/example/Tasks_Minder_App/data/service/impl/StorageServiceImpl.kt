package com.example.Tasks_Minder_App.data.service.impl

import androidx.compose.ui.util.trace
import com.example.Tasks_Minder_App.data.model.Task
import com.example.Tasks_Minder_App.data.service.AccountService
import com.example.Tasks_Minder_App.data.service.StorageService
import com.example.Tasks_Minder_App.utils.Constants.SAVE_TASK_TRACE
import com.example.Tasks_Minder_App.utils.Constants.TASKS_COLLECTION
import com.example.Tasks_Minder_App.utils.Constants.TASK_DATE_FIELD
import com.example.Tasks_Minder_App.utils.Constants.UPDATE_TASK_TRACE
import com.example.Tasks_Minder_App.utils.Constants.USER_ID_FIELD
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementation of [StorageService] using Firebase Firestore for storing and managing tasks.
 *
 * @param fireStore The [FirebaseFirestore] instance for interacting with Firestore.
 * @param firebaseAuth The [AccountService] instance for accessing the current user.
 */
class StorageServiceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val firebaseAuth: AccountService
) : StorageService {

    /**
     * Retrieves a list of tasks for a specific date for the currently authenticated user.
     *
     * @param selectedDate The date for which to retrieve tasks, formatted as a string ["MM/dd/yyyy"].
     * @return A [Flow] emitting a list of tasks matching the specified date.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSelectedDayTasks(selectedDate: String): Flow<List<Task>> {
        return firebaseAuth.currentUser.flatMapLatest { user ->
            fireStore.collection(TASKS_COLLECTION)
                .whereEqualTo(USER_ID_FIELD, user.userId)
                .whereEqualTo(TASK_DATE_FIELD, selectedDate)
                .dataObjects()
        }
    }

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param taskId The unique identifier of the task.
     * @return The [Task] if found, or `null` if the task does not exist.
     */
    override suspend fun getTask(taskId: String): Task? =
        fireStore.collection(TASKS_COLLECTION).document(taskId).get().await().toObject()

    /**
     * Adds a new task to the database for the currently authenticated user.
     *
     * @param task The task to be added.
     * @return The unique identifier of the newly added task.
     */
    override suspend fun addTask(task: Task): String =
        trace(SAVE_TASK_TRACE) {
            val taskWithUserId = task.copy(userId = firebaseAuth.currentUserId)
            fireStore.collection(TASKS_COLLECTION).add(taskWithUserId).await().id
        }

    /**
     * Updates an existing task in the database.
     *
     * @param task The task with updated details. The task must have a valid `id` field.
     */
    override suspend fun updateTask(task: Task): Unit =
        trace(UPDATE_TASK_TRACE) {
            fireStore.collection(TASKS_COLLECTION).document(task.id).set(task).await()
        }

    /**
     * Deletes a task by its unique identifier from the database.
     *
     * @param taskId The unique identifier of the task to be deleted.
     */
    override suspend fun deleteTask(taskId: String) {
        fireStore.collection(TASKS_COLLECTION).document(taskId).delete().await()
    }

}