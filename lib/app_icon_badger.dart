import 'package:flutter/services.dart';

import 'constants/constant_keys.dart';

class AppIconBadger {
  static const MethodChannel badgeChannel = MethodChannel(channelFlutterPlugin);

  static Future<bool> get isBadgeSupported async =>
      await badgeChannel.invokeMethod(channelMethodBadgeSupported);

  static Future<int> get badgeCount async =>
      await badgeChannel.invokeMethod(channelMethodGetBadgeCount);

  static Future<int> setBadgeCount(int count) async {
    return await badgeChannel
        .invokeMethod(channelMethodSetBadgeCount, {"count": count});
  }

  static Future<int> resetBadgeCount() async {
    return await badgeChannel.invokeMethod(channelMethodResetBadge);
  }

  static Future<int> incrementBadgeCount() async {
    return await badgeChannel.invokeMethod(channelMethodIncrementBadgeCount);
  }

  static Future<int> decrementBadgeCount() async {
    return await badgeChannel.invokeMethod(channelMethodDecrementBadgeCount);
  }
}
