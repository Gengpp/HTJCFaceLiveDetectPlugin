//
//  HTJCHandle.m
//  HelloCordova
//
//  Created by 彭彭 耿 on 17/11/2016.
//
//

#import "HTJCHandle.h"
#import <HTJCFaceLiveDetectSdk/THIDMCHTJCViewManger.h>

@implementation HTJCHandle
- (void)htjcFaceDetectAction:(CDVInvokedUrlCommand*)command
{
    THIDMCHTJCViewManger *manager = [THIDMCHTJCViewManger sharedManager:self.viewController];
    [manager getLiveDetectCompletion:^(BOOL sueccess, NSData *imageData) {
        
        NSString *resultImgString = [imageData base64EncodedStringWithOptions:NSUTF8StringEncoding];
        
        if (sueccess) {
            CDVPluginResult* result = [CDVPluginResult
                                       resultWithStatus:CDVCommandStatus_OK
                                       messageAsString:resultImgString];
            [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
            [manager dismissTakeCaptureSessionViewController];
        }
    }
                              cancel:^(BOOL sueccess, NSString *error) {
                                  NSLog(@"%s ,,,, error=%@",__func__,error);
                                  
                                  CDVPluginResult* result = [CDVPluginResult
                                                             resultWithStatus:CDVCommandStatus_ERROR
                                                             messageAsString:error];
                                  [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
                              }
                              failed:^(NSString *error) {
                                  NSLog(@"%s ,,,, error=%@",__func__,error);
                                  
                                  CDVPluginResult* result = [CDVPluginResult
                                                             resultWithStatus:CDVCommandStatus_ERROR
                                                             messageAsString:error];
                                  [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
                                  
                              }];
}
@end
