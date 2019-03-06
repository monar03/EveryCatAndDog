package jp.aquabox.app.everycatanddoggit

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.aquabox.app.everycatanddoggit.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context, bitmap: Bitmap?): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra("photo", bitmap)
            }
        }
    }

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewModel = this

        intent?.let {
            it.extras?.get("photo")?.let { photo ->
                binding.imageView.setImageBitmap(photo as Bitmap)
            }
        }
    }


}