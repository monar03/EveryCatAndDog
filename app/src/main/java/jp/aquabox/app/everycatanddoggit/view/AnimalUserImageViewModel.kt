package jp.aquabox.app.everycatanddoggit.view

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import jp.aquabox.app.everycatanddoggit.AnimalUserActivity
import jp.aquabox.app.everycatanddoggit.AnimalUserEditActivity
import jp.aquabox.app.everycatanddoggit.R
import jp.aquabox.app.everycatanddoggit.data.PetUserItem

class AnimalUserImageViewModel(context: Context, private val item: PetUserItem?) : ViewModel() {
    val photo: Drawable = item?.let {
        BitmapDrawable(null, BitmapFactory.decodeByteArray(it.photo, 0, it.photo!!.size))
    } ?: context.getDrawable(R.drawable.ic_add_animal)!!

    fun onClick(context: Context) {
        item?.let {
            context.startActivity(AnimalUserActivity.createIntent(context, it.id))
        } ?: context.startActivity(Intent(context, AnimalUserEditActivity::class.java))
    }
}