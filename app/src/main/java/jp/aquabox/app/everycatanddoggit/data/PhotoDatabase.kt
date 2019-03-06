package jp.aquabox.app.everycatanddoggit.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [PhotoItem::class, PetUserItem::class], exportSchema = false, version = 1)
abstract class PhotoDatabase : RoomDatabase() {
    companion object {
        fun database(context: Context) = Room.databaseBuilder(context.applicationContext, PhotoDatabase::class.java, "PhotoDB").build()
    }

    abstract fun photoListDao(): PhotoListDao

    abstract fun petUserDao(): PetUserDao
}