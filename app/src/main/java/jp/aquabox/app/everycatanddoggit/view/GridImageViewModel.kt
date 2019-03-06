package jp.aquabox.app.everycatanddoggit.view;

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import jp.aquabox.app.everycatanddoggit.DetailActivity
import jp.aquabox.app.everycatanddoggit.data.PhotoItem

class GridImageViewModel(val photoItem: PhotoItem) : ViewModel() {
    val drawable: MutableLiveData<Drawable> = MutableLiveData()

    init {
        drawable.value = BitmapDrawable(null, photoItem.getImage())
    }

    fun onClickImage(activity: AppCompatActivity) {
        activity.startActivity(DetailActivity.createIntent(activity, photoItem.getImage()))
    }
}
