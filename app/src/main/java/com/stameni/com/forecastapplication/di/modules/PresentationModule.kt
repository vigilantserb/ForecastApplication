package com.stameni.com.forecastapplication.di.modules

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides

@Module
class PresentationModule(private var mActivity: FragmentActivity) {

    @Provides
    internal fun getFragmentManager(): FragmentManager {
        return mActivity.supportFragmentManager
    }

    @Provides
    internal fun getLayoutInflater(): LayoutInflater {
        return LayoutInflater.from(mActivity)
    }

    @Provides
    internal fun getActivity(): Activity {
        return mActivity
    }

    @Provides
    internal fun getContext(activity: Activity): Context {
        return activity
    }
}