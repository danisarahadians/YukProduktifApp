package com.codinginflow.YukProduktif.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao // anotasi dao adalah data akses object berfungsi untuk mengakses data aplikasi menggunakan library presistensi room
interface TaskDao {
    fun getTasks(query: String, sortOrder: SortOrder, hideCompleted: Boolean): Flow<List<Task>> =
        when (sortOrder) {
            SortOrder.BY_DATE -> getTasksSortedByDateCreated(query, hideCompleted)
            SortOrder.BY_NAME -> getTasksSortedByName(query, hideCompleted)
        }
    //anotasi query untuk melakukan operasi baca/tulis pad adtabase
    @Query("SELECT * FROM task_table WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, name")
    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, name")
    fun getTasksSortedByDateCreated(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    //anotasi untuk insert data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)
    //suspend adalah cara kotlin menerapkan fungsi yang akan dipanggil dari dalam coroutine
    //anotasi untuk update data
    @Update
    suspend fun update(task: Task)
    //anotasi untuk delete data
    @Delete
    suspend fun delete(task: Task)
}