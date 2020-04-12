package com.franklinharper.kickstart

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

val IMMEDIATE = object : Scheduler() {

    override fun scheduleDirect(
        @NonNull run: Runnable,
        delay: Long,
        @NonNull unit: TimeUnit
    ): Disposable {
        return super.scheduleDirect(run, 0, unit)
    }

    override fun createWorker(): Worker {
        return ExecutorScheduler.ExecutorWorker(
            Executor { runnable -> runnable.run() },
            false
        )
    }
}

class RxSchedulerTestRule : TestRule {

    override fun apply(base: Statement, d: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler { IMMEDIATE }
                RxJavaPlugins.setInitComputationSchedulerHandler { IMMEDIATE }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { IMMEDIATE }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { IMMEDIATE }
                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }

}


