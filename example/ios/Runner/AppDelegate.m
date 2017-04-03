#include "AppDelegate.h"
#include "FirebaseStoragePlugin.h"

@implementation AppDelegate {
  FirebaseStoragePlugin *_firebase_storage;
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
  FlutterViewController *flutterController =
      (FlutterViewController *)self.window.rootViewController;
  _firebase_storage = [[FirebaseStoragePlugin alloc] initWithFlutterView:flutterController];
    return YES;
}

@end
