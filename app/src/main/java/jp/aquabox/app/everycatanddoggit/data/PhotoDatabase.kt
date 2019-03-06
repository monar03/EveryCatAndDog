package jp.aquabox.app.everycatanddoggit.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [PhotoItem::class, PetUserItem::class], exportSchema = false, version = 1)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoListDao(): PhotoListDao

    abstract fun petUserDao(): PetUserDao
}