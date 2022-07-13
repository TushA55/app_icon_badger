package com.app_icon_badger

import androidx.annotation.NonNull
import io.flutter.Log

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** AppIconBadgerPlugin */
class AppIconBadgerPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel
    private lateinit var badgeManager: BadgeManager

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, CHANNEL_FLUTTER_PLUGIN)
        channel.setMethodCallHandler(this)
        badgeManager = BadgeManager(flutterPluginBinding.applicationContext)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            CHANNEL_METHOD_BADGE_SUPPORTED -> {
                Log.d(CHANNEL_FLUTTER_PLUGIN, "isBadgeAllowed()")
                val isSupported = badgeManager.isBadgeAllowed()
                result.success(isSupported)
            }
            CHANNEL_METHOD_GET_BADGE_COUNT -> {
                Log.d(CHANNEL_FLUTTER_PLUGIN, "getBadgeCount()")

                val totalCount: Int = badgeManager.getBadgeCount()
                result.success(totalCount)
            }
            CHANNEL_METHOD_SET_BADGE_COUNT -> {
                val count  = call.argument<Int>("count")?.toInt()
                val totalCount: Int = badgeManager.setBadgeCount(count ?: 0)
                result.success(totalCount)
            }
            CHANNEL_METHOD_INCREMENT_BADGE_COUNT -> {
                Log.d(CHANNEL_FLUTTER_PLUGIN, "incrementBadgeCount()")

                val totalCount: Int = badgeManager.incrementBadgeCount()
                result.success(totalCount)
            }
            CHANNEL_METHOD_DECREMENT_BADGE_COUNT -> {
                val totalCount: Int = badgeManager.decrementBadgeCount()
                result.success(totalCount)
            }
            CHANNEL_METHOD_RESET_BADGE -> {
                badgeManager.resetBadgeCount()
                result.success(0)
            }
            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
