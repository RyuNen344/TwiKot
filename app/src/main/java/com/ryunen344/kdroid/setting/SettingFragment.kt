package com.ryunen344.kdroid.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.ryunen344.kdroid.R.xml.pref_headers
import com.ryunen344.kdroid.util.LogUtil
import org.koin.android.scope.currentScope

class SettingFragment : PreferenceFragmentCompat(), SettingContract.View {

    override val presenter : SettingContract.Presenter by currentScope.inject()

    override fun onCreate(savedInstanceState : Bundle?) {
        LogUtil.d()
        super.onCreate(savedInstanceState)
        presenter.view = this
    }

    override fun onCreatePreferences(savedInstanceState : Bundle?, rootKey : String?) {
        LogUtil.d()
        setPreferencesFromResource(pref_headers, rootKey)
    }

    override fun doSomething() {
        LogUtil.d("something display!!!!!!!!!")
    }


    override fun showError(e : Throwable) {
        LogUtil.e(e)
    }

}