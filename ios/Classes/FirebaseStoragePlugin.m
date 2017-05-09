#import "FirebaseStoragePlugin.h"

#import <Firebase/Firebase.h>

@interface NSError (FlutterError)
@property(readonly, nonatomic) FlutterError *flutterError;
@end

@implementation NSError (FlutterError)
- (FlutterError *)flutterError {
  return [FlutterError
      errorWithCode:[NSString stringWithFormat:@"Error %d", self.code]
            message:self.domain
            details:self.localizedDescription];
}
@end

@implementation FirebaseStoragePlugin {
}

- (instancetype)initWithController:(FlutterViewController *)controller {
  self = [super init];
  if (self) {
    if (![FIRApp defaultApp]) {
      [FIRApp configure];
    }
    FlutterMethodChannel *channel =
        [FlutterMethodChannel methodChannelWithName:@"firebase_storage"
                                    binaryMessenger:controller];
    [channel
        setMethodCallHandler:^(FlutterMethodCall *call, FlutterResult result) {
          if ([@"StorageReference#putFile" isEqualToString:call.method]) {
            NSData *data =
                [NSData dataWithContentsOfFile:call.arguments["filename"]];
            NSString *path = call.arguments["path"];
            FIRStorageReference *fileRef =
                [[FIRStorage storage].reference child:path];
            FIRStorageUploadTask *uploadTask = [fileRef
                   putData:data
                  metadata:nil
                completion:^(FIRStorageMetadata *metadata, NSError *error) {
                  if (error != nil) {
                    result(error.flutterError);
                  } else {
                    // Metadata contains file metadata such as size,
                    // content-type, and download URL.
                    NSURL *downloadURL = metadata.downloadURL;
                    result(downloadURL.absoluteString);
                  }
                }];
          } else {
            result(FlutterMethodNotImplemented);
          }
        }];
  }
  return self;
}

@end
