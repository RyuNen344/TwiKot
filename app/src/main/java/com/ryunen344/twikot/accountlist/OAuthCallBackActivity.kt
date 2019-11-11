package com.ryunen344.twikot.accountlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ryunen344.twikot.IOState
import com.ryunen344.twikot.R
import com.ryunen344.twikot.errorMessage
import com.ryunen344.twikot.util.LogUtil
import org.koin.android.viewmodel.ext.android.viewModel

class OAuthCallBackActivity : AppCompatActivity() {

    private val oAuthCallBackViewModel : OAuthCallBackViewModel by viewModel()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oauth_callback)

        //Twitterの認証画面から発行されるIntentからUriを取得
        val uri = intent.data
        LogUtil.d(uri)
        uri ?: return
        if (uri.getQueryParameter("denied")?.isNotBlank() != null && uri.getQueryParameter("denied")?.isNotBlank()!!) {
            LogUtil.d("oauth denied")
            finishSave()
            return
        }
        oAuthCallBackViewModel.saveAccessToken(uri)

        oAuthCallBackViewModel.ioState.observe(this, Observer { ioState ->
            when (ioState) {
                is IOState.NOPE -> return@Observer
                is IOState.LOADING -> LogUtil.d("loading")
                is IOState.LOADED -> finishSave()
                is IOState.ERROR -> LogUtil.d(ioState.error.createMessage { errorMessage(it) })
            }
        })
    }

    private fun finishSave() {
        val intent = Intent(this, AccountListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }
}
