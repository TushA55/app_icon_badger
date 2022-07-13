#import "AppIconBadgerPlugin.h"
#if __has_include(<app_icon_badger/app_icon_badger-Swift.h>)
#import <app_icon_badger/app_icon_badger-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "app_icon_badger-Swift.h"
#endif

@implementation AppIconBadgerPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAppIconBadgerPlugin registerWithRegistrar:registrar];
}
@end
