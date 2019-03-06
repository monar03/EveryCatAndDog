package jp.aquabox.app.everycatanddoggit.view

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import jp.aquabox.app.everycatanddoggit.AnimalUserActivity
import jp.aquabox.app.everycatanddoggit.AnimalUserEditActivity

class AnimalUserImageViewModel(val id: Long, val photo: Drawable?) : ViewModel() {
    fun onClick(context: Context) {
        if (id < 0) {
            context.startActivity(Intent(context, AnimalUserEditActivity::class.java))
        } else {
            context.startActivity(AnimalUserActivity.createIntent(context, id))
        }
    }
}