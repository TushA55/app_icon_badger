package com.app_icon_badger

import android.os.Build
import android.content.Context
import android.provider.Settings
import me.leolin.shortcutbadger.ShortcutBadger

class BadgeManager(private val context: Context) {
    private val prefs = context.getSharedPreferences("notifyPrefs", Context.MODE_PRIVATE)

    fun getBadgeCount(): Int {
        return prefs.getInt(BADGE_COUNT, 0)
    }

    fun setBadgeCount(count: Int): Int {
        prefs.edit().putInt(BADGE_COUNT, count).apply()
        ShortcutBadger.applyCount(this.context, count)
        return count
    }

    fun resetBadgeCount() {
        setBadgeCount(0)
    }

    fun incrementBadgeCount(): Int {
        val totalAmount: Int = getBadgeCount()
        setBadgeCount(totalAmount + 1)
        return totalAmount + 1
    }

    fun decrementBadgeCount(): Int {
        val totalAmount: Int = getBadgeCount()
        if(totalAmount > 0) {
            setBadgeCount(totalAmount - 1)
        }
        return totalAmount - 1
    }

    private fun isBadgeDeviceAllowed(): Boolean {
        return try {
            Settings.Secure.getInt(context.contentResolver, "notification_badging") == 1
        } catch (e: Settings.SettingNotFoundException) {
            true
        }
    }

    private fun isBadgeNumberingAllowed(): Boolean {
        return try {
            val currentBadgeCount = getBadgeCount()
            ShortcutBadger.applyCountOrThrow(context, currentBadgeCount)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun isBadgeAllowed(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!isBadgeDeviceAllowed())
                return false
            if (isBadgeNumberingAllowed())
                return true
        }
        return true
    }
}