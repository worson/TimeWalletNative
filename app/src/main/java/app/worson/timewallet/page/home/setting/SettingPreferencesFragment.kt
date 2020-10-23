package app.worson.timewallet.page.home.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import app.worson.timewallet.R

class SettingPreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}