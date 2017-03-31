import 'dart:async';

import 'package:flutter/services.dart';

class FirebaseStorage {
  static const PlatformMethodChannel _channel =
      const PlatformMethodChannel('firebase_storage');

  static Future<String> get platformVersion =>
      _channel.invokeMethod('getPlatformVersion');
}
