package br.com.arquitetoandroid.appcommerce.viewmodel

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import br.com.arquitetoandroid.appcommerce.repository.HomeBannerRepository

class HomeBannerViewModel(application: Application): AndroidViewModel(application) {

    private val homeBannerRepository = HomeBannerRepository(application)

    fun load(imageView: ImageView) = homeBannerRepository.load(imageView)

}