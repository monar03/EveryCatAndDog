package jp.aquabox.app.everycatanddoggit

import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import jp.aquabox.app.everycatanddoggit.databinding.ActivityAnimalUserInputBinding

class AnimalUserInputActivity : AppCompatActivity() {
    lateinit var binding: ActivityAnimalUserInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animal_user_input)
        binding.controller = this
    }


    fun onFabClick(view: View) {
        startActivity(Intent(this, AnimalUserEditActivity::class.java))
    }

}