package com.nvkhang96.tymexgithubusers.base.utils

import co.touchlab.kermit.Logger
import com.nvkhang96.tymexgithubusers.BuildConfig

@Suppress("KotlinConstantConditions")
object LogUtil {
    private const val TAG = "TymeXGitHubUsers"
    private const val ENABLE_VERBOSE = true
    private const val ENABLE_DEBUG = true
    private const val ENABLE_INFO = true
    private const val ENABLE_WARNING = true
    private const val ENABLE_ERROR = true

    fun d(tag: String = TAG, msg: () -> String?) {
        if (!ENABLE_VERBOSE && !ENABLE_DEBUG && BuildConfig.DEBUG) return
        Logger.d(msg().toString(), throwable = null, tag)
    }

    fun i(tag: String = TAG, msg: () -> String?) {
        if (!ENABLE_VERBOSE && !ENABLE_INFO) return
        Logger.i(msg().toString(), throwable = null, tag)
    }

    fun w(
        tag: String = TAG,
        throwable: Throwable? = null,
        msg: () -> String? = { "" },
    ) {
        if (!ENABLE_VERBOSE && !ENABLE_WARNING) return
        Logger.w(msg().toString(), throwable, tag)
    }

    fun e(
        tag: String = TAG,
        throwable: Throwable? = null,
        msg: () -> String? = { "" },
    ) {
        if (!ENABLE_VERBOSE && !ENABLE_ERROR) return
        Logger.e(msg().toString(), throwable, tag)
    }
}