package com.franklinharper.kickstart.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Usage: compositeDisposable += observable.subscribe()
 *
 * Using operator syntax makes it easier to see when someone forgets to add the disposable to
 * the CompositeDisposable.
 */
operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
  add(disposable)
}

