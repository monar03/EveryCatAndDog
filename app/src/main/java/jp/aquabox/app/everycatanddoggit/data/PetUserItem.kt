package jp.aquabox.app.everycatanddoggit.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.text.format.DateFormat
import java.io.Serializable

@Entity(tableName = "pet_user")
class PetUserItem : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = -1

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

    enum class SEX(val s: String) {
        MAN("おとこのこ"),
        FEMALE("おとこのこ"),
        UNKNOWN("おとこのこ");

        fun getText(): String {
            return s
        }

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

    fun getSexText(): String {
        return SEX.convert(sex.toInt()).getText()
    }

    fun getBarthDayText(): String {
        return DateFormat.format("yyyy/MM/dd", barthday).toString()
    }
}