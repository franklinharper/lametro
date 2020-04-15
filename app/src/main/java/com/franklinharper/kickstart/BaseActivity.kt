package com.franklinharper.kickstart

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity: AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}

/**
 * Usage: compositeDisposable += observable.subscribe()
 *
 * Using operator syntax makes it easier to see when someone forgets to dispose of the disposable.
 */
operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}