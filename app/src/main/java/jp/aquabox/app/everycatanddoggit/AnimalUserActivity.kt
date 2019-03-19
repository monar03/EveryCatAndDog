package jp.aquabox.app.everycatanddoggit

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import jp.aquabox.app.everycatanddoggit.data.PhotoDatabase
import jp.aquabox.app.everycatanddoggit.databinding.ActivityAnimalUserBinding
import jp.aquabox.app.everycatanddoggit.view.AnimalUserViewModel

class AnimalUserActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context, id: Long): Intent {
            return Intent(context, AnimalUserActivity::class.java)
                    .apply {
                        putExtra("id", id)
                    }
        }
    }

    lateinit var binding: ActivityAnimalUserBinding
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animal_user)
        binding.context = this
    }

    override fun onResume() {
        super.onResume()
        val db: PhotoDatabase = PhotoDatabase.database(this)
        compositeDisposable.add(
                db.petUserDao()
                        .getPetUser(intent.getLongExtra("id", 0))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onSuccess = {
                                    binding.viewModel = AnimalUserViewModel(it)
                                }
                        )
        )
    }
}