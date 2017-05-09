// Copyright 2017 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.firebase.storage;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;

import java.util.Map;
import java.io.File;

/**
 * FirebaseStoragePlugin
 */
public class FirebaseStoragePlugin implements MethodCallHandler {
  private FlutterActivity activity;
  private FirebaseStorage firebaseStorage;

  public static FirebaseStoragePlugin register(FlutterActivity activity) {
    return new FirebaseStoragePlugin(activity);
  }

  private FirebaseStoragePlugin(FlutterActivity activity) {
    this.activity = activity;
    FirebaseApp.initializeApp(activity);
    this.firebaseStorage = FirebaseStorage.getInstance();
    new MethodChannel(activity.getFlutterView(), "firebase_storage").setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(MethodCall call, final Result result) {
    if (call.method.equals("StorageReference#putFile")) {
      Map<String, String> arguments = (Map<String, String>) call.arguments;
      String filename = arguments.get("filename");
      String path = arguments.get("path");
      File file = new File(filename);
      StorageReference ref = firebaseStorage.getReference().child(path);
      UploadTask uploadTask = ref.putFile(Uri.fromFile(file));
      uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot snapshot) {
          result.success(snapshot.getDownloadUrl().toString());
        }
      });
      uploadTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(Exception e) {
          result.error("upload_error", e.getMessage(), e.getStackTrace());
        }
      });
    } else {
      result.notImplemented();
    }
  }
}
