package com.sandy.kabupatenkotariauapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.sandy.kabupatenkotariauapp.databinding.ActivityViewPetaBinding

class ViewPetaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewPetaBinding

    companion object {
        const val EXTRA_URL_PETA = "extra_url_peta"
        private const val TAG = "ViewPetaActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPetaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val urlPeta = intent.getStringExtra(EXTRA_URL_PETA)

        if (urlPeta.isNullOrEmpty()) {

            Toast.makeText(this, "URL Peta tidak tersedia", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "URL Peta kosong atau null")
            finish()
            return
        }


        Log.d(TAG, "Memuat URL Peta: $urlPeta")


        try {
            val requestBuilder = GlideToVectorYou
                .init()
                .with(this)
                .requestBuilder
            requestBuilder
                .load(urlPeta)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.baseline_image_24image)
                        .error(R.drawable.baseline_broken_image_24)
                        .fitCenter()

                )
                .into(binding.imgItemPhoto)



        } catch (e: Exception) {

            Toast.makeText(this, "Gagal memuat peta: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Error memuat peta", e)
        }
    }
}
