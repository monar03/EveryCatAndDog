package jp.aquabox.app.everycatanddoggit.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface PhotoListDao {
    @Query("SELECT * FROM photo_list")
    fun getAll(): Single<List<PhotoItem>>

    @Insert
    fun addData(item: PhotoItem)
}