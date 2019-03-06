package jp.aquabox.app.everycatanddoggit.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface PetUserDao {
    @Query("SELECT * FROM pet_user")
    fun getPerUserAll(): Single<List<PetUserItem>>

    @Query("SELECT * FROM pet_user WHERE id = :id")
    fun getPetUser(id: Long): PetUserItem

    @Insert
    fun addPetUser(petUser: PetUserItem)
}