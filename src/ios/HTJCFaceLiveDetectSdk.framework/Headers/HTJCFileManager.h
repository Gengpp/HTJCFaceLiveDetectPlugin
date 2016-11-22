//
//  HTJCFileManager.h
//  HTJCFaceLiveDetectSdk
//
//  Created by jiao on 15/8/3.
//  Copyright (c) 2015年 于汇江. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface HTJCFileManager : NSObject

+ (HTJCFileManager *)sharedManagers;



//遍历文件夹
-(NSArray *)traversingFolder:(NSString *)folderPath;

//获取文件路径
-(NSString *)getFilePath:(NSString*)fileName;

//创建文件夹
-(NSString *)creatFolder:(NSString *)folderName;
//创建文件
-(void)creatFile:(NSString*)fileName;
//删除文件
-(void)deleteFile:(NSString*)filePath;

//写入图片数据
-(void)writeData:(NSData *)imageData;

//获取HTML字符串
-(NSString *)getHtmlStringWithFolderName:(NSString *)folderNam;

//提供完整路径,截取文件名称
-(NSString *)getFileName:(NSString *)path;

//清空文件夹
-(void)clearFolder:(NSString *)path;

//获取文件个数
-(NSInteger)getFileCount:(NSString *)folderName;

//压缩指定文件夹到Document目录下,返回zip路径
//-(NSString *)ZipFilesWithName:(NSString *)zipName folderPath:(NSString *)folderPath;

//删除Document海鑫文件夹下所有照片
-(void)deleteDocumentAllPhotos;

- (BOOL)writeSaveImage:(NSData*)imageData toFileName:(NSString*)aname;

-(void)writeData:(NSData *)imageData withTyp:(NSString*)type;

@end
