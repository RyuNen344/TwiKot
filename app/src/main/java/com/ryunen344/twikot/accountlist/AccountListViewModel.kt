package com.ryunen344.twikot.accountlist

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ryunen344.twikot.IOState
import com.ryunen344.twikot.entity.Account
import com.ryunen344.twikot.repository.AccountRepositoryImpl
import com.ryunen344.twikot.repository.OAuthRepositoryImpl
import com.ryunen344.twikot.util.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class AccountListViewModel(
        private val accountRepositoryImpl : AccountRepositoryImpl,
        private val oAuthRepositoryImpl : OAuthRepositoryImpl
) : ViewModel() {

    private var disposable : CompositeDisposable = CompositeDisposable()

    private var _ioState : MutableLiveData<IOState> = MutableLiveData(IOState.NOPE)
    val ioState : LiveData<IOState>
        get() = _ioState

    private var _oAuthRequestUri : MutableLiveData<Uri> = MutableLiveData()
    val oAuthRequestUri : LiveData<Uri>
        get() = _oAuthRequestUri

    private var _items : MutableLiveData<List<Account>> = MutableLiveData()
    val items : LiveData<List<Account>>
        get() = _items

    fun loadAccountList() {
        LogUtil.d()

        when (ioState.value) {
            is IOState.LOADING, IOState.ERROR("something happens!") -> {
                LogUtil.d("state error ${ioState.value.toString()}")
                return
            }
        }

        _ioState.value = IOState.LOADING
        accountRepositoryImpl.findAccountList()
                .delay(800, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            LogUtil.d("account load subscribe")
                            _ioState.value = IOState.LOADED
                            _items.value = it
                        },
                        {
                            _ioState.value = IOState.ERROR(it)
                        }
                ).addTo(disposable)
    }

    fun generateOAuthRequestUri(consumerKey : String, consumerSecretKey : String) {
        LogUtil.d()

        when (ioState.value) {
            is IOState.LOADING, IOState.ERROR("something happens!") -> {
                LogUtil.d("state error ${ioState.value.toString()}")
                return
            }
        }

        _ioState.value = IOState.LOADING
        oAuthRepositoryImpl.initOAuthAuthorization(consumerKey, consumerSecretKey)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            _ioState.value = IOState.LOADED
                            _oAuthRequestUri.value = oAuthRepositoryImpl.loadAuthorizationURL()
                        },
                        {
                            _ioState.value = IOState.ERROR(it)
                        }
                ).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}