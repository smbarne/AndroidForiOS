//
//  UIColor+MBTAColors.m
//  AndroidForiOS
//
//  Created by Stephen Barnes on 3/21/14.
//  Copyright (c) 2014 Stephen Barnes. All rights reserved.
//

#import "UIColor+MBTAColors.h"

@implementation UIColor (MBTAColors)

+ (UIColor *)colorForLineType:(MBTALineType)lineType
{
    UIColor *colorForLineType = nil;
    switch (lineType) {
        case MBTALineTypeBlue:
            colorForLineType = [UIColor blueColor];
            break;
            
        case MBTALineTypeOrange:
            colorForLineType = [UIColor orangeColor];
            break;
            
        case MBTALineTypeRed:
        default:
            colorForLineType = [UIColor redColor];
            break;
    }
    
    return colorForLineType;
}

#pragma mark - Application Colors

+ (UIColor *)rzRed
{
    return [UIColor colorWithRed:0.925f green:0.349f blue:0.301f alpha:1.f];
}

+ (UIColor *)rzRedPressed
{
    return [UIColor colorWithRed:0.925f green:0.145f blue:0.f alpha:1.f];
}

@end
