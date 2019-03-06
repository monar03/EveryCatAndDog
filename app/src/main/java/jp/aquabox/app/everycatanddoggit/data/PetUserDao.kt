package jp.aquabox.app.everycatanddoggit.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface PetUserDao {
    @Query("SELECT * FROM pet_user")
    fun getPerUserAll(): Single<List<PetUserItem>>

    @Query("SELECT * FROM pet_user WHERE id = :id")
    fun getPetUser(id: Long): Single<PetUserItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPetUser(petUser: PetUserItem)
}