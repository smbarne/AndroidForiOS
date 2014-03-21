//
//  MBTADataManager.h
//  AndroidForiOS
//
//  Created by Stephen Barnes on 3/21/14.
//  Copyright (c) 2014 Stephen Barnes. All rights reserved.
//

#import <Foundation/Foundation.h>

@class MBTATripList;

typedef void (^MBTAImportCompletionBlock)(MBTATripList*, BOOL succeeded, NSError* error);

@interface MBTADataManager : NSObject

#pragma mark - Shared Instance
+ (MBTADataManager*)shared;

#pragma mark - Data Import
- (void)importDataForKey:(NSString *)key fromFileNamed:(NSString *)fileNamed withCompletion:(MBTAImportCompletionBlock)completion;

@end
