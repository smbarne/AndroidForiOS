//
//  MBTADataManager.m
//  AndroidForiOS
//
//  Created by Stephen Barnes on 3/21/14.
//  Copyright (c) 2014 Stephen Barnes. All rights reserved.
//

#import "MBTADataManager.h"

@interface MBTADataManager ()

@end

@implementation MBTADataManager

#pragma mark - Shared Instance

+ (MBTADataManager*)shared
{
    static dispatch_once_t onceToken;
    static MBTADataManager *s_sharedManager;
    dispatch_once(&onceToken, ^{
        s_sharedManager = [[MBTADataManager alloc] init];
    });
    
    return s_sharedManager;
}

#pragma mark - Data Import

- (void)importDataForKey:(NSString *)key fromFileNamed:(NSString *)fileNamed withCompletion:(MBTAImportCompletionBlock)completion;
{
    // TODO - load data and set result to
}

@end
