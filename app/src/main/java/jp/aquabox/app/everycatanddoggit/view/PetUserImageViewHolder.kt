package jp.aquabox.app.everycatanddoggit.view

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import jp.aquabox.app.everycatanddoggit.AnimalUserEditActivity

class PetUserImageViewHolder(val photo: Drawable?) : ViewModel() {
    fun onClick(context: Context) {
        context.startActivity(Intent(context, AnimalUserEditActivity::class.java))
    }
}