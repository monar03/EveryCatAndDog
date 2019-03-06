package jp.aquabox.app.everycatanddoggit.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.Bitmap
import android.graphics.BitmapFactory

@Entity(tableName = "photo_list")
class PhotoItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "date")
    var date: Long = 0

    @ColumnInfo(name = "photo")
    var photo: ByteArray? = ByteArray(0)

    @ColumnInfo(name = "category")
    var category: String? = ""

    @ColumnInfo(name = "memo")
    var memo: String? = ""

    fun getImage(): Bitmap? {
        return if (photo == null) {
            null
        } else {
            BitmapFactory.decodeByteArray(photo, 0, photo!!.size)
        }
    }
}