package com.yourcompany.firebase_storage_example;

import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import com.yourcompany.firebase_storage.FirebaseStoragePlugin;

public class MainActivity extends FlutterActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseStoragePlugin.register(this);
    }
}
