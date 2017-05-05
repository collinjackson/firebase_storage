#include "AppDelegate.h"
#include "PluginRegistry.h"

@implementation AppDelegate {
  PluginRegistry *plugins;
}

- (BOOL)application:(UIApplication *)application
    didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
  FlutterViewController *flutterController =
      (FlutterViewController *)self.window.rootViewController;
  plugins = [[PluginRegistry alloc] initWithController:flutterController];
  return YES;
}

@end
