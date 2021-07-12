package com.codinginflow.YukProduktif.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Entity(tableName = "task_table")
//anotasi entity berfungsi untuk menyimpan data dan mempresentasikan tabel dalam database. disini table namenya namyanya tasktable
@Parcelize
//anotasi parcelize dilakukan supaya bisa mengirimkan data berupa objek agar bisa dikirim ke activity lain
data class Task(
    //deklarasikan variable, kalo dikotlin pakainya val
    //tipe variable ada var id, important, completed, created
    //dengan anotasi primarykeynya ID
    val name: String, //bertipe
    val important: Boolean = false,
    val completed: Boolean = false,
    val created: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0 //untuk uniq variable , value auto generatenya true
) : Parcelable //begitu juga dengan parcelable (satukesatuan dgn anotasi parcelize//
    {
    val createdDataFormated: String
        get() = DateFormat.getDateTimeInstance().format(created)
}