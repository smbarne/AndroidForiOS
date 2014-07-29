//
//  UIColor+MBTAColors.h
//  AndroidForiOS
//
//  Created by Stephen Barnes on 3/21/14.
//  Copyright (c) 2014 Stephen Barnes. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MBTATripList.h"

@interface UIColor (MBTAColors)

+ (UIColor *)colorForLineType:(MBTALineType)lineType;

#pragma mark - Application Colors
+ (UIColor *)rzRed;
+ (UIColor *)rzRedPressed;

@end
