package br.com.arquitetoandroid.appcommerce

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

}