package com.franklinharper.kickstart

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity: AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}

/**
 * Usage: compositeDisposable += observable.subscribe()
 *
 * Using operator syntax makes it easier to see when someone forgot to dispose of the disposable.
 */
operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}