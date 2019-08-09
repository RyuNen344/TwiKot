package com.ryunen344.kdroid.di.module

import com.ryunen344.kdroid.accountList.AccountListContract
import com.ryunen344.kdroid.accountList.AccountListFragment
import com.ryunen344.kdroid.accountList.AccountListPresenter
import com.ryunen344.kdroid.addTweetReply.AddTweetReplyContract
import com.ryunen344.kdroid.addTweetReply.AddTweetReplyFragment
import com.ryunen344.kdroid.addTweetReply.AddTweetReplyPresenter
import com.ryunen344.kdroid.di.provider.AppProvider
import com.ryunen344.kdroid.home.HomeActivity
import com.ryunen344.kdroid.home.HomeContract
import com.ryunen344.kdroid.home.HomeFragment
import com.ryunen344.kdroid.home.HomePresenter
import com.ryunen344.kdroid.home.tweet.HomeTweetContract
import com.ryunen344.kdroid.home.tweet.HomeTweetFragment
import com.ryunen344.kdroid.home.tweet.HomeTweetPresenter
import com.ryunen344.kdroid.mediaViewer.MediaViewerActivity
import com.ryunen344.kdroid.mediaViewer.MediaViewerContract
import com.ryunen344.kdroid.mediaViewer.MediaViewerFragment
import com.ryunen344.kdroid.mediaViewer.MediaViewerPresenter
import com.ryunen344.kdroid.profile.ProfileActivity
import com.ryunen344.kdroid.profile.ProfileContract
import com.ryunen344.kdroid.profile.ProfileFragment
import com.ryunen344.kdroid.profile.ProfilePresenter
import com.ryunen344.kdroid.profile.tweet.ProfileTweetContract
import com.ryunen344.kdroid.profile.tweet.ProfileTweetFragment
import com.ryunen344.kdroid.profile.tweet.ProfileTweetPresenter
import com.ryunen344.kdroid.profile.user.ProfileUserContract
import com.ryunen344.kdroid.profile.user.ProfileUserFragment
import com.ryunen344.kdroid.profile.user.ProfileUserPresenter
import com.ryunen344.kdroid.settings.SettingsContract
import com.ryunen344.kdroid.settings.SettingsFragment
import com.ryunen344.kdroid.settings.SettingsPresenter
import com.ryunen344.kdroid.tweetDetail.TweetDetailActivity
import com.ryunen344.kdroid.tweetDetail.TweetDetailContract
import com.ryunen344.kdroid.tweetDetail.TweetDetailFragment
import com.ryunen344.kdroid.tweetDetail.TweetDetailPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val AppModule = module {
    single {AppProvider()}

    factory { AccountListFragment() }
    scope(named<AccountListFragment>()) {
        scoped { AccountListPresenter() as AccountListContract.Presenter }
    }

    factory { HomeFragment() }
    scope(named<HomeFragment>()) {
        scoped { HomePresenter(getProperty(HomeActivity.INTENT_KEY_USER_ID)) as HomeContract.Presenter }
    }

    factory { HomeTweetFragment() }
    scope(named<HomeTweetFragment>()){
        scoped { HomeTweetPresenter(getProperty(HomeTweetFragment.INTENT_KEY_PAGER_POSITION), getProperty(HomeActivity.INTENT_KEY_USER_ID)) as HomeTweetContract.Presenter}
    }

    factory { AddTweetReplyFragment() }
    scope(named<AddTweetReplyFragment>()) {
        scoped { AddTweetReplyPresenter() as AddTweetReplyContract.Presenter }
    }

    factory { TweetDetailFragment() }
    scope(named<TweetDetailFragment>()) {
        scoped { TweetDetailPresenter(getProperty(TweetDetailActivity.INTENT_KEY_TWEET_ID)) as TweetDetailContract.Presenter }
    }

    factory { ProfileFragment() }
    scope(named<ProfileFragment>()) {
        scoped { ProfilePresenter(getProperty(ProfileActivity.INTENT_KEY_USER_ID), getProperty(ProfileActivity.INTENT_KEY_SCREEN_NAME)) as ProfileContract.Presenter }
    }

    factory { ProfileTweetFragment() }
    scope(named<ProfileTweetFragment>()) {
        scoped { ProfileTweetPresenter(getProperty(ProfileTweetFragment.INTENT_KEY_PAGER_POSITION), getProperty(ProfileActivity.INTENT_KEY_USER_ID), getProperty(ProfileActivity.INTENT_KEY_SCREEN_NAME)) as ProfileTweetContract.Presenter }
    }

    factory { ProfileUserFragment() }
    scope(named<ProfileUserFragment>()) {
        scoped { ProfileUserPresenter(getProperty(ProfileUserFragment.INTENT_KEY_PAGER_POSITION), getProperty(ProfileActivity.INTENT_KEY_USER_ID), getProperty(ProfileActivity.INTENT_KEY_SCREEN_NAME)) as ProfileUserContract.Presenter }
    }

    factory { MediaViewerFragment() }
    scope(named<MediaViewerFragment>()) {
        scoped { MediaViewerPresenter(getProperty(MediaViewerActivity.INTENT_KEY_MEDIA_URL)) as MediaViewerContract.Presenter }
    }

    factory { SettingsFragment() }
    scope(named<SettingsFragment>()) {
        scoped { SettingsPresenter() as SettingsContract.Presenter }
    }
}