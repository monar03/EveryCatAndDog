package jp.aquabox.app.everycatanddoggit

import android.annotation.SuppressLint
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import jp.aquabox.app.everycatanddoggit.data.PhotoDatabase
import jp.aquabox.app.everycatanddoggit.data.PhotoItem
import jp.aquabox.app.everycatanddoggit.databinding.ActivityMainBinding
import jp.aquabox.app.everycatanddoggit.databinding.MainGridImageviewBinding
import jp.aquabox.app.everycatanddoggit.databinding.PetImageCircleBinding
import jp.aquabox.app.everycatanddoggit.view.AnimalUserImageViewModel
import jp.aquabox.app.everycatanddoggit.view.GridImageViewModel
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_CAMERA = 200
    lateinit var binding: ActivityMainBinding
    val composites: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.controller = this
        loadView()
    }

    override fun onResume() {
        super.onResume()
        loadView()
    }

    private fun loadView() {
        val db = PhotoDatabase.database(this)
        binding.petList.removeAllViews()
        loadPetUserItem(db)
        loadImageView(db)

    }

    private fun loadImageView(db: PhotoDatabase) = composites.add(
            db.photoListDao()
                    .getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onError =
                            {},
                            onSuccess =
                            {
                                binding.imageGridview.adapter = object : BaseAdapter() {
                                    @SuppressLint("ViewHolder")
                                    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                                        val binding: MainGridImageviewBinding = MainGridImageviewBinding.inflate(layoutInflater)
                                        binding.viewModel = GridImageViewModel(it[position])
                                        binding.action = this@MainActivity
                                        binding.lifecycleOwner = this@MainActivity

                                        return binding.root
                                    }


                                    override fun getItem(position: Int): Bitmap? {
                                        return it[position].getImage()
                                    }

                                    override fun getItemId(position: Int): Long {
                                        return 0
                                    }

                                    override fun getCount(): Int {
                                        return it.size
                                    }

                                }
                            }
                    )
    )

    private fun loadPetUserItem(db: PhotoDatabase) = composites.add(
            db.petUserDao()
                    .getPerUserAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onSuccess = { list ->
                                list.map {
                                    DataBindingUtil
                                            .inflate<PetImageCircleBinding>(layoutInflater, R.layout.pet_image_circle, binding.petList, true)
                                            .apply {
                                                viewModel = AnimalUserImageViewModel(
                                                        it.id,
                                                        BitmapDrawable(null, BitmapFactory.decodeByteArray(it.photo, 0, it.photo!!.size))
                                                )
                                                context = this@MainActivity
                                            }
                                }

                                DataBindingUtil
                                        .inflate<PetImageCircleBinding>(layoutInflater, R.layout.pet_image_circle, binding.petList, true)
                                        .apply {
                                            viewModel = AnimalUserImageViewModel(
                                                    -1,
                                                    getDrawable(R.drawable.ic_add_animal)
                                            )
                                            context = this@MainActivity
                                        }
                            },
                            onError = {}
                    )
    )

    fun fabClick() {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_CAMERA)
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                if (data != null) {
                    val bitmap: Bitmap = data.extras.get("data") as Bitmap
                    composites.add(
                            addData(bitmap)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeBy(
                                            onError = {},
                                            onComplete = { loadView() }
                                    )
                    )
                }
            }
        }
    }

    private fun addData(bitmap: Bitmap): Completable {
        val db = PhotoDatabase.database(this)
        return Completable.create { emitter ->
            db.photoListDao().addData(
                    PhotoItem().apply {
                        val os = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                        photo = os.toByteArray()
                        os.close()
                    })
            emitter.onComplete()
        }
    }
}