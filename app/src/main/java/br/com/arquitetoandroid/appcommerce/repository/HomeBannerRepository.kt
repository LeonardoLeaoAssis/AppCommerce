package br.com.arquitetoandroid.appcommerce.repository

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.arquitetoandroid.appcommerce.model.HomeBanner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.storage.ktx.storage

class HomeBannerRepository(application: Application) {

    private val storage = Firebase.storage(Firebase.app)
    private val glide = Glide.with(application)
    private val remoteConfig = Firebase.remoteConfig
    private val configSettins = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 60
    }

    init {
        remoteConfig.setConfigSettingsAsync(configSettins)
    }

    fun load(imageView: ImageView): LiveData<HomeBanner> {
        val liveData = MutableLiveData<HomeBanner>()

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val title = remoteConfig.getString("title")
                val subtitle = remoteConfig.getString("subtitle")
                val image = remoteConfig.getString("image")

                storage.reference.child("home_banner/$image").downloadUrl.addOnSuccessListener {
                    liveData.value = HomeBanner(title, subtitle)

                    glide.load(it)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView)
                }
            }
        }

        return liveData
    }

}