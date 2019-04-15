package com.ryunen344.kdroid.data.api

import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import twitter4j.Paging
import twitter4j.Status
import twitter4j.Twitter

class Twitter4jApi {

    fun getTimeLine(twitter: Twitter, paging: Paging): Single<List<Status>> {
        return Single.create(SingleOnSubscribe<List<Status>> { twitter.getHomeTimeline(paging) })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}