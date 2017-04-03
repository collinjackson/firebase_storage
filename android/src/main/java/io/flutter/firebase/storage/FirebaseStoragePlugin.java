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
import io.flutter.plugin.common.FlutterMethodChannel;
import io.flutter.plugin.common.FlutterMethodChannel.MethodCallHandler;
import io.flutter.plugin.common.FlutterMethodChannel.Response;
import io.flutter.plugin.common.MethodCall;

import java.util.List;
import java.io.File;

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
  public void onMethodCall(MethodCall call, final Response response) {
    if (call.method.equals("StorageReference#putFile")) {
      List arguments = (List) call.arguments;
      String filename = (String) arguments.get(0);
      String path = (String) arguments.get(1);
      File file = new File(filename);
      StorageReference ref = FirebaseStorage.getInstance().getReference().child(path);
      UploadTask uploadTask = ref.putFile(Uri.fromFile(file));
      uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot snapshot) {
          response.success(snapshot.getDownloadUrl().toString());
        }
      });
      uploadTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(Exception e) {
          response.error("upload_error", e.getMessage(), e.getStackTrace());
        }
      });
    } else {
      response.notImplemented();
    }
  }
}
