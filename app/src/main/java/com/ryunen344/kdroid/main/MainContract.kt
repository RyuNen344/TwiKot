package com.ryunen344.kdroid.main

import com.ryunen344.kdroid.BasePresenter
import com.ryunen344.kdroid.BaseView
import twitter4j.Status

interface MainContract {

    interface View : BaseView<Presenter> {
        fun showTweetList(mainList : List<Status>)
        fun addNewTweet()
        fun showSuccessfullyTweet()
        fun showFailTweet()
    }

    interface Presenter : BasePresenter {
        fun loadTweetList()
        fun openTweetDetail()
        fun result(requestCode : Int, resultCode : Int)
    }

    interface MainItemListner {
        fun onAccountClick()
    }
}