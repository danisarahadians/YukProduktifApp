package com.codinginflow.YukProduktif.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Qualifier

@Database(entities = [Task::class], version = 1) //anotasi database untuk membuat database yang digunakan untuk mendaftarkan objek dan entitas
abstract class TaskDatabase : RoomDatabase() { //membuat database dngan nama TaskDatabase dan mengambil database database dari ROOM DATABASE

    abstract fun taskDao(): TaskDao //mengambil database dari taskdao
    //class callback
    class Callback @Inject constructor(
        private val database: Provider <TaskDatabase>,
        private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun  onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            //db operations
            val dao = database.get().taskDao()
            //kode untuk membuat task secara langsung untuk ditampilkan di aplikasi
            applicationScope.launch {
                dao.insert(Task("Buy Groceries"))
                dao.insert(Task("Call Mom", important = true))
                dao.insert(Task("Do the Laundry"))
                dao.insert(Task("Prepare for food", completed = true))
                dao.insert(Task("Repair my bike", completed = true))
                dao.insert(Task("Call Ellon Musk"))
            }
        }
    }
}