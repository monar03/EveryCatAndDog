package jp.aquabox.app.everycatanddoggit.view

import android.arch.lifecycle.ViewModel
import android.content.Context
import jp.aquabox.app.everycatanddoggit.AnimalUserEditActivity
import jp.aquabox.app.everycatanddoggit.data.PetUserItem

class AnimalUserViewModel(val petUserItem: PetUserItem) : ViewModel() {
    fun onFabClick(context: Context) {
        context.startActivity(AnimalUserEditActivity.createIntent(context, petUserItem))
    }
}