package com.yourcompany.firebase_storage;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.FlutterMethodChannel;
import io.flutter.plugin.common.FlutterMethodChannel.MethodCallHandler;
import io.flutter.plugin.common.FlutterMethodChannel.Response;
import io.flutter.plugin.common.MethodCall;

import java.util.HashMap;
import java.util.Map;

/**
 * FirebaseStoragePlugin
 */
public class FirebaseStoragePlugin implements MethodCallHandler {
  private FlutterActivity activity;

  public static void register(FlutterActivity activity) {
    new FirebaseStoragePlugin(activity);
  }

  private FirebaseStoragePlugin(FlutterActivity activity) {
    this.activity = activity;
    new FlutterMethodChannel(activity.getFlutterView(), "firebase_storage").setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(MethodCall call, Response response) {
    if (call.method.equals("getPlatformVersion")) {
      response.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      response.notImplemented();
    }
  }
}
