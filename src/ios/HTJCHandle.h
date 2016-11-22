//
//  HTJCHandle.h
//  HelloCordova
//
//  Created by 彭彭 耿 on 17/11/2016.
//
//

#import <Cordova/CDV.h>

@interface HTJCHandle : CDVPlugin

- (void) htjcFaceDetectAction:(CDVInvokedUrlCommand*)command;
@end
