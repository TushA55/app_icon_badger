import Flutter
import UIKit

public class SwiftAppIconBadgerPlugin: NSObject, FlutterPlugin {
 
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "app_icon_badger", binaryMessenger: registrar.messenger())
    let instance = SwiftAppIconBadgerPlugin()
   
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
      var application = UIApplication.shared
    switch call.method {
        case NotificationConstants.CHANNEL_METHOD_GET_BADGE_COUNT:
            let totalCount = getNotificationCount();
        result(totalCount)
        case NotificationConstants.CHANNEL_METHOD_SET_BADGE_COUNT:
            let nCount:Int = call.arguments as! Int
            let totalCount = setNotificationCount(count: nCount)
            application.applicationIconBadgeNumber = totalCount
        result(totalCount)
        case NotificationConstants.CHANNEL_METHOD_RESET_BADGE:
            let totalCount = setNotificationCount(count: 0)
            application.applicationIconBadgeNumber = totalCount
        result(0)
        case NotificationConstants.CHANNEL_METHOD_INCREMENT_BADGE_COUNT:
            let totalCount : Int = getNotificationCount() + 1;
            application.applicationIconBadgeNumber = setNotificationCount(count: totalCount)
        result(totalCount)
        case NotificationConstants.CHANNEL_METHOD_DECREMENT_BADGE_COUNT:
            let totalCount:Int = getNotificationCount() - 1;
            application.applicationIconBadgeNumber = setNotificationCount(count: totalCount)
        result(totalCount)
        default:
        result(FlutterMethodNotImplemented)
  }

  
}
func getNotificationCount() -> Int{
    var notificationCount : Int = 0
    let preferences = UserDefaults.standard
    let notifyPrefs = "notifyPrefs"
    if preferences.object(forKey: notifyPrefs) == nil {
    } else {
        notificationCount = preferences.integer(forKey: notifyPrefs)
    }
    return notificationCount
}
func setNotificationCount(count:Int)->Int{
    let preferences = UserDefaults.standard
    let currentLevelKey = "notifyPrefs"
    preferences.set(count, forKey: currentLevelKey)
    return count
}
}
