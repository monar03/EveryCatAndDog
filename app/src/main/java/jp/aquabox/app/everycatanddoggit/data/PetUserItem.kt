package jp.aquabox.app.everycatanddoggit.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "pet_user")
class PetUserItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "sex")
    var sex: Long = 0

    @ColumnInfo(name = "barthday")
    var barthday: Long = 0

    @ColumnInfo(name = "hospital")
    var hospital: String? = ""

    @ColumnInfo(name = "photo")
    var photo: ByteArray? = ByteArray(0)

    enum class SEX {
        MAN,
        FEMALE,
        UNKNOWN;

        companion object {
            fun convert(i: Int): SEX {
                when (i) {
                    1 -> return SEX.MAN
                    2 -> return SEX.FEMALE
                }

                return SEX.UNKNOWN
            }
        }
    }

    fun getSex(): SEX {
        return SEX.convert(sex.toInt())
    }
}