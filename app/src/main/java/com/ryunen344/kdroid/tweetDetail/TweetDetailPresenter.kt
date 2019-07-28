package com.ryunen344.kdroid.tweetDetail

import android.os.Bundle
import com.ryunen344.kdroid.di.provider.ApiProvider
import com.ryunen344.kdroid.di.provider.AppProvider
import com.ryunen344.kdroid.util.LogUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import twitter4j.Twitter

class TweetDetailPresenter(private val tweetDetailView : TweetDetailContract.View, val appProvider : AppProvider, private val apiProvider : ApiProvider, val bundle : Bundle?) : TweetDetailContract.Presenter {

    private var mTwitter : Twitter = appProvider.provideTwitter()
    private var mTweetId : Long = 0L
    var mCompositeDisposable : CompositeDisposable = CompositeDisposable()

    init {
        bundle?.let { it ->
            mTweetId = it.getLong(TweetDetailActivity.INTENT_KEY_TWEET_ID, 0)
        }
        tweetDetailView.setPresenter(this)
    }

    override fun start() {
        LogUtil.d()
        loadTweetDetail()
    }

    override fun clearDisposable() {
        LogUtil.d()
        mCompositeDisposable.clear()
    }

    override fun loadTweetDetail() {
        LogUtil.d()
        val disposable : Disposable =
                apiProvider.getTweetByTweetId(mTwitter, mTweetId).subscribe(
                        { status ->
                            LogUtil.d(status)
                            tweetDetailView.showTweetDetail(status)

                        }
                        , { e ->
                    tweetDetailView.showError(e)
                }
                )
        mCompositeDisposable.add(disposable)
    }

}