#import "FirebaseStoragePlugin.h"

@implementation FirebaseStoragePlugin {
}

- (instancetype)initWithFlutterView:(FlutterViewController *)flutterView {
  self = [super init];
  if (self) {
    FlutterMethodChannel *channel = [FlutterMethodChannel
        methodChannelWithName:@"firebase_storage"
           binaryMessenger:flutterView];
    [channel setMethodCallHandler:^(FlutterMethodCall *call,
                                    FlutterResultReceiver result) {
      if ([@"getPlatformVersion" isEqualToString:call.method]) {
        result([@"iOS " stringByAppendingString:[[UIDevice currentDevice]
                                                    systemVersion]]);
      }
    }];
  }
  return self;
}

@end
