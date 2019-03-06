package jp.aquabox.app.everycatanddoggit

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import jp.aquabox.app.everycatanddoggit.data.PetUserItem
import jp.aquabox.app.everycatanddoggit.data.PhotoDatabase
import jp.aquabox.app.everycatanddoggit.databinding.ActivityAnimalUserEditBinding
import jp.aquabox.app.everycatanddoggit.view.AnimalUserViewModel
import java.io.ByteArrayOutputStream
import java.util.*

class AnimalUserEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityAnimalUserEditBinding
    private val REQUEST_CODE_CAMERA: Int = 200

    companion object {
        fun createIntent(context: Context, userItem: PetUserItem): Intent {
            return Intent(context, AnimalUserEditActivity::class.java)
                    .apply {
                        putExtra("data", userItem)
                    }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animal_user_edit)
        binding.viewModel = intent.getSerializableExtra("data")?.let {
            AnimalUserViewModel(intent.getSerializableExtra("data") as PetUserItem)
        } ?: AnimalUserViewModel(PetUserItem())
        binding.action = this
    }

    fun onFabClick() {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_CAMERA)
    }

    lateinit var disposable: Disposable

    fun onSave() {
        disposable = Completable.create { emitter ->
            PhotoDatabase.database(this)
                    .petUserDao()
                    .addPetUser(PetUserItem().apply {
                        id = binding.viewModel?.let {
                            it.petUserItem.id
                        } ?: -1

                        barthday = Date(barthday).time
                        name = binding.petName.text.toString()
                        hospital = binding.petHospital.text.toString()

                        val drawable: BitmapDrawable = binding.toolbarImage.drawable as BitmapDrawable
                        val bos = ByteArrayOutputStream()
                        drawable.bitmap.compress(
                                Bitmap.CompressFormat.JPEG,
                                100,
                                bos
                        )
                        photo = bos.toByteArray()
                    })
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = { this@AnimalUserEditActivity.finish() },
                        onError = {}
                )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (data != null) {
                val image = data.let {
                    it.extras?.get("data") as Bitmap
                }

                binding.toolbarImage.setImageBitmap(image)
            }
        }
    }
}